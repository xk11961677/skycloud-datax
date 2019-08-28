package com.skycloud.datax.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.skycloud.datax.web.service.IJobLogService;
import com.skycloud.datax.web.dao.JobLogMapper;
import com.skycloud.datax.web.model.entity.JobLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 抽取日志记录表表服务实现类
 *
 * @author
 */
@Service
@Transactional(readOnly = true)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

}