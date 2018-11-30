package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

@Data
public class Institution {
    private String institution_id;            	//机构ID
    private String institution_name;        	//机构名称
    private String institution_type;       		//机构类型(1：区级部门，2：街镇，3：企业，默认1)
    private String institution_describe;     	//机构描述
    private String industry_id;          		//关联行业ID
    private String industry_name;				//展示用关联行业名称
    private String institution_linkman_name;    //机构联系人姓名
    private String institution_linkman_phone;  	//机构联系人电话
    private String institution_linkman_email; 	//机构联系人邮箱
    private String del_flag;                  	//删除标识(0：未删除，1：已删除，默认0)
    private String create_time;               	//创建时间
    private String update_time;               	//修改时间
    private String institution_main_id;			//设定主管部门ID(企业)
    private String street_main_id;                //设定主管部门ID(街镇)
    private String institution_main_name;		//设定主管部门名称(显示用)
    
    
    //不是数据库中的
    private String headImg;               	// 头像
    private String forthwith_id;               	// 即时评价id
    private String is_answer;               	// 即时评价栏目-我的历史评价 是否回复
    private String answer_content;               	// 即时评价栏目-我的历史评价 回复内容
    private String html_describe;     // 机构简介 --页面格式展示
}
