package com.skycloud.datax.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.skycloud.datax.web.service.DataxPluginService;
import com.skycloud.datax.web.dao.DataxPluginDao;
import com.skycloud.datax.web.model.entity.DataxPlugin;
import org.springframework.stereotype.Service;

/**
 * datax插件信息表服务实现类
 *
 * @author
 */
@Service("dataxPluginService")
public class DataxPluginServiceImpl extends ServiceImpl<DataxPluginDao, DataxPlugin> implements DataxPluginService {

}