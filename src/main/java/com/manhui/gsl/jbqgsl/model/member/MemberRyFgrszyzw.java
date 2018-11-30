package com.manhui.gsl.jbqgsl.model.member;

import lombok.Data;

/**
 * 非公人事主要职务表
 * table :member_ry_fgrszyzw
 */
@Data
public class MemberRyFgrszyzw {
    //ID
    private String id;
    //人员 ID
    private String rybm;
    //行政区划名称（参考“行政区划”）
    private String xzqhmc;
    //行政区划编码（参考“行政区划”）
    private String xzqhbm;
    //公司名称
    private String gsmc;
    //公司编码
    private String gsbm;
    //内部机构 ID
    private String nbjgbm;
    //内部机构名称
    private String nbjgmc;
    //其它社会单位名称
    private String qtshdwmc;
    //届
    private String je;
    //职务（参考“职务”）
    private String zw;
    //职级（参考“职称”）
    private String zj;
    //所属职务类别
    private String sszwlb;
    //职务所在省份
    private String zwszsf;
    //职务所在城市
    private String zwszcs;
    //部门
    private String bm;
    //职务所在区县
    private String zwszqx;
    //是否自建组织机构（0：否，1：是，默认0）
    private String sfzjzzjg;
    //任职起始日期
    private String rzqsrq;
    //任职结束日期
    private String rzjsrq;
    //是否在职（0：否，1：是，默认0）
    private String sfzz;
    //是否兼职（0：否，1：是，默认0）
    private String sfjz;
    //分管工作
    private String fggz;
    //推荐单位
    private String tjdw;
    //推荐人选
    private String tjrx;

    private String rdzw;            //人大职务
    private String zxzw;            //政协职务
    private String ttxhmc;          //参与的团体协会名称
    private String ttxhzw;             //参加的团体协会职务
    private String shzw;            //社会职务

    private String paramid;         //  关联父表id
}
