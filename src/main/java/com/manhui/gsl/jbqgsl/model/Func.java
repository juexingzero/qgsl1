package com.manhui.gsl.jbqgsl.model;

import lombok.Data;
import java.util.Date;

@Data
public class Func {
    private String  func_id;     //功能ID
    private String  menu_id;     //菜单ID
    private String  menu_name;   //菜单名称
    private String  func_name;   //功能名称
    private String  func_desc;   //功能描述
    private String  order_no;    //排序号
    private String  auth_flag;   //授权标识(A：匿名访问，N：登录访问，Y：授权访问)
    private Integer func_level;  //功能等级
    private String  creator_id;  //创建人ID
    private Date    create_time; //创建时间
    private String  update_bools;//是否修改(修改：true，添加：false)
    private Integer pageNo;      //开始页数
    private Integer pageSize;    //查询总数
}
