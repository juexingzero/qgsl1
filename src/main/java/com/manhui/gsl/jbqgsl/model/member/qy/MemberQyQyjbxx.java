package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业基本信息表
 * table :member_qy_qyjbxx
 */
@Data
public class MemberQyQyjbxx {
    //基本信息主键
    private String jbxxzj;
    //企业名称
    private String qymc;
    //企业简称
    private String qyjc;
    //企业代码类型（参考 “企业（商会）代码类型”）
    private String qydmlx;
    //法定代表人
    private String fddbr;
    //行业分类（参考 “国民经济行业分类”）
    private String hyfl;
    //行业代码
    private String hydm;
    //行业性质
    private String hyxz;
    //企业类型（参考“企业类型”）
    private String qylx;
    //企业注册地址
    private String qyzcdz;
    //企业所在行政省
    private String qyszxzs;
    //企业所在行政市
    private String qyszxzs1;
    //企业所在行政区县
    private String qyszxzqx;
    //企业所在行政乡镇
    private String qyszxzxz;
    //注册资金
    private Double zczj;
    //成立日期
    private String clrq;
    //经营期限
    private String jyqx;
    //经营状态
    private String jyzt;
    //国税号
    private String gsh;
    //地税号
    private String dsh;
    //领导人名字
    private String ldrmz;
    //公司注册牌照号
    private String gszcpzh;
    //主要商业
    private String zysy;
    //质量标准
    private String zlbz;
    //其他质量标准
    private String qtzlbz;
    //整体价值
    private String ztjz;
    //统一社会信用代码
    private String tyshxydm;
    //原始表主键
    private String company_code;
    //原始表人员 ID
    private String person_id;
    //新人员编码
    private String person_new;
    //数据来源
    private String source;

    private String state;               //状态
}
