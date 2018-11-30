package com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import lombok.Data;

import java.util.List;

/**
 * 企业入会基本信息保存
 */
@Data
public class QyxxBaseResult {
    /***基本信息表**/
    //企业名称
    private String qymc;
    //统一社会信用代码
    private String tyshxydm;
    //行业分类（参考 “国民经济行业分类”）
    private String hyfl;
    //企业类型（参考“企业类型”）
    private String qylx;
    //企业注册地址
    private String qyzcdz;
    //注册资金
    private Double zczj;
    //成立日期
    private String clrq;

    /***企业介绍表**/
    //企业网址
    private String qywz;
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
    //企业品牌
    private String qypp;
    //经营范围
    private String jyfw;

    /***以下企业介绍数据，单独保存*****/
    //企业发展历程
    private String qyfzlc;
    //企业简介
    private String qyjj;
    //企业文化
    private String qywh;



    /***企业联系人信息表**/
    //联系人姓名srrzrq
    private String lxrxm;
    //联系人联系方式
    private String lxrlxfs;

    /***企业认证信息表**/
    //是否被认证为高新技术企业
    private String sfbrzwgxjsqy;
    //高新技术企业认证机构（部门）
    private String gxjsqyrzjg;
    //是否获得外贸自营进出口权
    private String sfhdwmzyjckq;
    //外贸自营认证部门
    private String wmzyrzbm;
    //是否通过质量管理认证
    private String sftgzlglrz;
    //质量认证部门
    private String zlglrzmb;

    private List<MemberRyRyzj> ryzjList;//人员证件集合数据
    private List<MemberRyFgrszyry> ryryList;//人员荣誉集合数据


    private String qyid;
    private String joinId;

    private String approve_fail_info;//审批未通过类别
}
