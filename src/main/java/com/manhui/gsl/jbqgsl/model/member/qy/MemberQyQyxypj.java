package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业信用评级表
 * table :member_qy_qyxypj
 */
@Data
public class MemberQyQyxypj {
    //信用评级主键
    private String xypjzj;
    //企业基本信息表主键
    private String jbxxzj;
    //企业获得信用评级年份
    private String qyhdxypjnf;
    //工商企业信用
    private String gsqyxy;
    //纳税信用等级
    private String nsxydj;
    //监管评价等级
    private String jgpjdj;
    //银行资信定级
    private String yhzxdj;
    //企业信用级别
    private String qyxyjb;
    //劳动保障说明
    private String ldbzsm;
    //审查信用定级
    private String scxydj;
    //企业基本信息表id
    private String qyid;
}
