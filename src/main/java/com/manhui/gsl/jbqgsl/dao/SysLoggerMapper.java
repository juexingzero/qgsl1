package com.manhui.gsl.jbqgsl.dao;

import com.manhui.gsl.jbqgsl.model.SysLoggerModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLoggerMapper {
    void save(SysLoggerModel model);
}
