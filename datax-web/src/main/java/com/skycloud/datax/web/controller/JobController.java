package com.skycloud.datax.web.controller;

import com.alibaba.datax.common.log.DataxResult;
import com.alibaba.datax.common.log.LogResult;
import com.baomidou.mybatisplus.extension.api.R;
import com.skycloud.datax.web.model.dto.RunJobDto;
import com.skycloud.datax.web.service.IDataxJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:
 **/
@RestController
@RequestMapping("api")
@Api(tags = "datax作业接口")
public class JobController {

    @Autowired
    IDataxJobService iDataxJobService;


    /**
     * 通过接口传入json配置启动一个datax作业
     *
     * @param jobJson
     * @return
     */
    @ApiOperation("通过传入json配置启动一个datax作业")
    @PostMapping("/runJob")
    public R<String> runJob(@RequestBody String jobJson, @RequestBody String param) {
        String result = iDataxJobService.startJobByJsonStr(jobJson, param);
        return R.ok(result);
    }

    /**
     * 通过接口传入 runJobDto 实体启动一个datax作业，并记录日志
     *
     * @param runJobDto
     * @return
     */
    @ApiOperation("通过传入 runJobDto 实体启动一个datax作业，并记录日志")
    @PostMapping("/runJobLog")
    public R<String> runJobLog(@RequestBody RunJobDto runJobDto) {
        String result = iDataxJobService.startJobLog(runJobDto);
        return R.ok(result);
    }

    /**
     * 通过接口传入 runJobDto 实体启动一个datax作业，并记录日志,同步执行并返回执行结果
     *
     * @param runJobDto
     * @return
     */
    @ApiOperation("(同步操作)通过传入 runJobDto中jobConfigId 实体启动一个datax作业，并记录日志")
    @PostMapping("/runJobAndResultByConfigId")
    @ResponseBody
    public R<DataxResult> runJobAndResultByConfigId(@RequestBody RunJobDto runJobDto) {
        DataxResult result = iDataxJobService.startAndResultJobById(runJobDto);
        if (result == null || !StringUtils.isEmpty(result.getExceptionMessage())) {
            return R.failed(result.getExceptionMessage());
        }
        return R.ok(result);
    }


    /**
     * 通过接口传入 runJobDto 实体启动一个datax作业，并记录日志
     *
     * @param runJobDto
     * @return
     */
    @ApiOperation("(异步操作)通过传入 runJobDto中jobConfigId 实体启动一个datax作业，并记录日志")
    @PostMapping("/runJobByConfigId")
    public R<String> runJobByConfigId(@RequestBody RunJobDto runJobDto) {
        String result = iDataxJobService.startJobById(runJobDto);
        return R.ok(result);
    }

    /**
     * 根据任务id查询日志
     *
     * @param id
     * @return
     */
    @ApiOperation("查看任务抽取日志,id为任务id，fromLineNum为读取的行数")
    @GetMapping("/viewJobLog")
    public R<LogResult> viewJogLog(Long id, int fromLineNum) {
        return R.ok(iDataxJobService.viewJogLog(id, fromLineNum));
    }
}
