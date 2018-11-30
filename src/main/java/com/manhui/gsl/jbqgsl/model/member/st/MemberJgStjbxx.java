package com.manhui.gsl.jbqgsl.model.member.st;

import lombok.Data;

/**
 *社团入会，社团基本信息表
 * table:member_jg_stjbxx
 */
@Data
public class MemberJgStjbxx {
    private String id;         //ID
    private String stbm;         //社团编码
    private String szsbm;         //所在省编码
    private String szsbm02;         //所在市编码
    private String szxbm;         //所在县编码
    private String stmc;         //社团名称
    private String ywzgbm;         //业务主管部门
    private String clsj;         //成立时间
    private String pzdjjg;         //批准登记机关
    private String stfrzshm;         //社团法人证书号码
    private String stfrjgdmzhm;         //社团法人机构代码证号码
    private String stlxr;         //社团联系人 ID
    private String stfrdb;         //社团法人代表 ID
    private String dh;         //电话
    private String dz;         //地址
    private String qywz;         //企业网址
    private String ttjj;         //团体简介
    private String bz;         //备注

    private String stywmc;      //社团英文名称
    private String zjlx;        //证件类型
    private String zjhm;        //证件号码
    private Integer cysl;        //成员数量
    private String mscgzryxm;       //秘书处工作人员姓名
    private String mscgzryzw;       //秘书处工作人员职务
    private String mscgzrydh;       //秘书处工作人员电话
    private Integer zip_code;        //邮编
    private String fax;         //传真
}
