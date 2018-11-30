package com.manhui.gsl.jbqgsl.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private String  user_id;        //用户ID
    private String  user_name;      //用户名称
    private String  user_no;        //编号
    private String  login_name;     //登录名
    private String  password;       //登录密码
    private String  email;          //邮件地址
    private String  work_phone;     //办公电话
    private String  mobile_no;      //手机号码
    private String  home_phone;     //家庭电话
    private String  keyword;        //关键字
    private Integer order_no;       //排序号
    private String  memo;           //备注说明
    private String  user_type;      //用户类型(0：普通用户，1：管理员)
    private String  account_status; //账号状态(0：禁用 ，1：正常)
    private String  user_status;    //用户状态(1：正常 2，已离职 ，：已删除)
    private Date    last_login_time;//上次登录时间
    private String  creator_id;     //创建人ID
    private Date    create_time;    //创建时间
    private Date    alter_time;     //修改时间
    private String   head_img;     //用户头像
    private String   user_sex;     //用户性别 0: 女性 1:男性
    //不在sys_user中
    private String  position_id;    //岗位编码
    private String  position_name;  //职位
    private String  dept_name;      //部门
    private String  where;          //条件
    private String  sql;            //where
    private Integer is_primary;     //是否主要岗位(0：否，1：是（同一员工只有一个主岗位）)
    private Integer pageNo;         //开始页数
    private Integer pageSize;       //查询总数
}
