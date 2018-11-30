package com.manhui.gsl.jbqgsl.model;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Menu {
    private String     menu_id;     //菜单ID
    private String     menu_name;   //菜单名称
    private String     p_menu_id;   //父级菜单ID：顶级菜单的父级菜单id为-1
    private String     p_menu_name; //父级菜单名称
    private String     menu_url;    //菜单URL
    private String     menu_pic_css;//菜单图片样式
    private String     order_no;    //排序号
    private String     creator_id;  //创建人ID
    private Date       create_time; //创建时间
    private String     update_bools;//是否修改(修改：true，添加：false)
    private String     user_id;     //用户编码
    private String     id;          //menu编码
    private String     iconCls;     //menu图片
    private String     text;        //menu内容
    private String     url;         //url地址
    private List<Menu> children;    //下级菜单
    private List<Func> functions;   //菜单功能权限
    private Integer    child_count; //下级菜单数量
}
