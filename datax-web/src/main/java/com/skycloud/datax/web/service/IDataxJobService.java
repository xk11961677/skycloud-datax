package com.skycloud.datax.web.service;

import com.alibaba.datax.common.log.DataxResult;
import com.alibaba.datax.common.log.LogResult;
import com.skycloud.datax.web.model.dto.RunJobDto;

/**
 * @author
 **/
public interface IDataxJobService {
    /**
     * 根据json字符串用线程池启动一个datax作业
     *
     * @param jobJson
     * @param param 变量参数
     * @return
     */
    String startJobByJsonStr(String jobJson,String param);

    /**
     * 启动
     *
     * @param runJobDto
     * @return
     */
    String startJobLog(RunJobDto runJobDto);


    /**
     * 启动
     *
     * @param runJobDto
     * @return
     */
    String startJobById(RunJobDto runJobDto);

    /**
     *
     * @param runJobDto
     * @return
     */
    DataxResult startAndResultJobById(RunJobDto runJobDto);

    /**
     * 日志
     *
     * @param id
     * @param fromLineNum
     * @return
     */
    LogResult viewJogLog(Long id, int fromLineNum);
}
