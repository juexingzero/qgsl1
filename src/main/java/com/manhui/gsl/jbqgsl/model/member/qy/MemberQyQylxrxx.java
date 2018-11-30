package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业联系人信息表
 * table :member_qy_qylxrxx
 */
@Data
public class MemberQyQylxrxx {
    //联系人信息主键
    private String lxrxxzj;
    //企业基本信息表主键
    private String jbxxzj;
    //填表人姓名
    private String tbrxm;
    //填表人联系方式
    private String tbrlxfs;
    //秘书姓名
    private String msxm;
    //秘书联系方式
    private String mslxfs;
    //联系人姓名
    private String lxrxm;
    //联系人联系方式
    private String lxrlxfs;
    //海外负责人姓名
    private String hwfzrxm;
    //海外负责人职务
    private String hwfzrzw;
    //企业通讯地址
    private String qytxdz;
    //法人代表联系方式
    private String frdblxfs;
    //海外负责人联系方式
    private String hwfzrlxfs;
    //企业邮编
    private String qyyb;
    //董事长姓名
    private String dszxm;
    //董事长联系方式
    private String dszlxfs;
    //企业邮箱
    private String qyyx;
    //办公电话
    private String bgdh;
    //手机号码
    private String sjhm;
    //企业基本信息表id
    private String qyid;
}
