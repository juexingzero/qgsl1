package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业介绍表
 * table :member_qy_qyjs
 */
@Data
public class MemberQyQyjs {
    //企业介绍主键
    private String qyjszj;
    //企业基本信息表主键
    private String jbxxzj;
    //企业发展历程
    private String qyfzlc;
    //企业简介
    private String qyjj;
    //企业文化
    private String qywh;
    //企业行业地位影响力
    private String qyhydwyxl;
    //企业突出特点
    private String qytctd;
    //企业网址
    private String qywz;
    //企业品牌
    private String qypp;
    //经营范围
    private String jyfw;

    //电话
    private String phone;
    //传真
    private String fax;
    //邮编
    private String zip_code;
    //职工数
    private Integer staff_number;
    //党员数
    private Integer party_number;
    //大专文化以上人数
    private Integer college_number;
    //是否有党团，工会
    private String is_society;
    //企业基本信息表id
    private String qyid;

    private String state;               //状态
}
