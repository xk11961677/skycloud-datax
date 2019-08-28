package com.skycloud.datax.web.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.common.log.DataxResult;
import com.alibaba.datax.common.log.EtlJobLogger;
import com.alibaba.datax.common.log.LogResult;
import com.alibaba.datax.common.spi.ErrorCode;
import com.alibaba.datax.core.Engine;
import com.alibaba.datax.core.util.ExceptionTracker;
import com.alibaba.datax.core.util.FrameworkErrorCode;
import com.alibaba.datax.core.util.container.CoreConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skycloud.datax.web.model.dto.RunJobDto;
import com.skycloud.datax.web.model.entity.JobConfig;
import com.skycloud.datax.web.model.entity.JobLog;
import com.skycloud.datax.web.service.IDataxJobService;
import com.skycloud.datax.web.service.IJobConfigService;
import com.skycloud.datax.web.service.IJobLogService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author:
 **/
@Slf4j
@Service
public class IDataxJobServiceImpl implements IDataxJobService {
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("datax-job-%d").build();

    private ExecutorService jobPool = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 日志文件保存目录
     */
    @Value("${app.etlLogDir}")
    private String etlLogDir;

    @Autowired
    private IJobLogService jobLogService;

    @Autowired
    private IJobConfigService jobConfigService;


    @Override
    public String startJobByJsonStr(String jobJson, String param) {
        jobPool.submit(() -> {
            start(jobJson, param);
        });
        return "success";
    }


    @Override
    public String startJobLog(RunJobDto runJobDto) {
        JSONObject json = log(runJobDto);
        //启动任务
        return startJobByJsonStr(JSON.toJSONString(json), runJobDto.getParam());
    }


    @Override
    public String startJobById(RunJobDto runJobDto) {
        JobConfig jobConfig = jobConfigService.getById(runJobDto.getJobConfigId());
        if (jobConfig == null) {
            return "执行失败: 未找到jobConfig";
        }
        runJobDto.setJobJson(jobConfig.getConfigJson());
        if (StringUtils.isEmpty(runJobDto.getParam()) && !StringUtils.isEmpty(jobConfig.getConfigJsonParam())) {
            runJobDto.setParam(jobConfig.getConfigJsonParam());
        }
        return startJobLog(runJobDto);
    }

    @Override
    public DataxResult startAndResultJobById(RunJobDto runJobDto) {
        JobConfig jobConfig = jobConfigService.getById(runJobDto.getJobConfigId());
        if (jobConfig == null) {
            DataxResult result = new DataxResult();
            result.setExceptionMessage("执行失败: 未找到jobConfig :{} " + JSON.toJSONString(runJobDto));
            return result;
        }
        runJobDto.setJobJson(jobConfig.getConfigJson());
        if (StringUtils.isEmpty(runJobDto.getParam()) && !StringUtils.isEmpty(jobConfig.getConfigJsonParam())) {
            runJobDto.setParam(jobConfig.getConfigJsonParam());
        }
        JSONObject json = log(runJobDto);
        return start(JSON.toJSONString(json), runJobDto.getParam());
    }

    @Override
    public LogResult viewJogLog(Long id, int fromLineNum) {
        QueryWrapper<JobLog> queryWrapper = new QueryWrapper<>();
        //根据id获取最新的日志文件路径
        queryWrapper.lambda().eq(JobLog::getJobId, id).orderByDesc(JobLog::getCreateDate);
        List<JobLog> list = jobLogService.list(queryWrapper);
        //取最新的一条记录
        if (list.isEmpty()) {
            return new LogResult(1, 1, "没有找到对应的日志文件！", true);
        } else {
            //取出路径，读取文件
            return EtlJobLogger.readLog(list.get(0).getLogFilePath(), fromLineNum);
        }
    }


    /**
     * 运行
     *
     * @param jobJson
     * @param param
     * @return
     */
    private DataxResult start(String jobJson, String param) {
        final String tmpFilePath = "jobTmp-" + System.currentTimeMillis() + ".conf";
        // 根据json写入到临时本地文件
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(tmpFilePath, "UTF-8");
            writer.println(jobJson);

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        DataxResult result;
        try {
            // 使用临时本地文件启动datax作业
            result = Engine.entry(tmpFilePath, param);
            //  删除临时文件
            FileUtil.del(new File(tmpFilePath));
        } catch (Throwable e) {
            String trace = ExceptionTracker.trace(e);
            log.error("\n\n经DataX智能分析,该任务最可能的错误原因是:\n" + trace);
            result = new DataxResult();
            result.setExceptionMessage(trace);
            if (e instanceof DataXException) {
                DataXException tempException = (DataXException) e;
                ErrorCode errorCode = tempException.getErrorCode();
                if (errorCode instanceof FrameworkErrorCode) {
                    FrameworkErrorCode tempErrorCode = (FrameworkErrorCode) errorCode;
                }
            }

        }
        return result;
    }


    /**
     * 记录日志
     *
     * @param runJobDto
     * @return
     */
    private JSONObject log(RunJobDto runJobDto) {
        //取出 jobJson，并转为json对象
        JSONObject json = JSONObject.parseObject(runJobDto.getJobJson());
        //根据jobId和当前时间戳生成日志文件名
        String logFileName = runJobDto.getJobConfigId().toString().concat("_").concat(StrUtil.toString(System.currentTimeMillis()).concat(".log"));
        String logFilePath = etlLogDir.concat(logFileName);
        //记录日志
        JobLog jobLog = new JobLog();
        jobLog.setJobId(runJobDto.getJobConfigId());
        jobLog.setLogFilePath(logFilePath);
        jobLogService.save(jobLog);
        //将路径放进去
        json.put(CoreConstant.LOG_FILE_PATH, logFilePath);
        return json;
    }
}
