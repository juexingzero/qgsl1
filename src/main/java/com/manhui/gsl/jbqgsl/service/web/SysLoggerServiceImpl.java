package com.manhui.gsl.jbqgsl.service.web;

import com.manhui.gsl.jbqgsl.dao.SysLoggerMapper;
import com.manhui.gsl.jbqgsl.model.SysLoggerModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志
 */
@Service("sysLoggerService")
public class SysLoggerServiceImpl {

    @Autowired
    private SysLoggerMapper sysLoggerMapper;

    public void save(SysLoggerModel model) {
        model.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        sysLoggerMapper.save(model);
    }
}
