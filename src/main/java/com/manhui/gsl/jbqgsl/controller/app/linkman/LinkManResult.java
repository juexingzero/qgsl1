package com.manhui.gsl.jbqgsl.controller.app.linkman;

import lombok.Data;

@Data
public class LinkManResult {
    private String  user_id;    //用户ID
    private String  user_name;  //用户名称
    private String  mobile_no;  //手机号码
    private String  work_phone; //办公电话
    private String  home_phone; //家庭电话
    private String  email;      //邮件地址
    private String  head_img;   //用户头像
    private String  user_sex;   //用户头像
    private Integer order_no;   //排序号
    private String position_id;   //岗位id
    private String position_name;   //岗位名称
    private String dept_name;   //部门名称
    private String dept_id;   //部门id
}
