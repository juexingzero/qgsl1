package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * 系统保存日志
 */
@Data
public class SysLoggerModel {
    //
    private Integer id;
    //创建时间
    private String createTime;
    //日志类型，1=等日志
    private String type;
    //功能操作人id
    private String userId;
    //用户类型，1=app注册用户，2=系统管理员用户
    private String userType;
    //
    private String userName;
    //日志内容
    private String logger_content;
    //操作对象id
    private String operating_obj_id;
    //操作描述
    private String operating_describe;
}
