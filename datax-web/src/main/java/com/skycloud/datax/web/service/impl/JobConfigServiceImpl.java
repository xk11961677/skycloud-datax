package com.skycloud.datax.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.skycloud.datax.web.model.entity.JobConfig;
import com.skycloud.datax.web.dao.JobConfigMapper;
import com.skycloud.datax.web.service.IJobConfigService;
import org.springframework.stereotype.Service;

@Service
public class JobConfigServiceImpl extends ServiceImpl<JobConfigMapper, JobConfig> implements IJobConfigService {

}
