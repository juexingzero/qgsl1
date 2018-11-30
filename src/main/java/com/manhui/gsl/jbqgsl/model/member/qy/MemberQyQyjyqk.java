package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业经营情况表
 * table :member_qy_qyjyqk
 */
@Data
public class MemberQyQyjyqk {
    //经营情况主键
    private String jyqkzj;
    //年份
    private String nf;
    //企业产值年份
    private String qycznf;
    //利润年份
    private String lrnf;
    //企业基本信息表主键
    private String jbxxzj;
    //总资产年份
    private String zzcnf;
    //税后净利润年份
    private String shjlrnf;
    //固定资产
    private Double gdzc;
    //纳税年份
    private Double nsnf;
    //税后净利润
    private Double shjlr;
    //出口总额
    private Double ckze;
    //固定资产年份
    private String gdzcnf;
    //资产负债率年份
    private String zcfzlnf;
    //总资产
    private Double zzc;
    //资产负债率
    private Double zcfzl;
    //营业收入年份
    private String yysrnf;
    //营业收入总额
    private Double yysrze;
    //利润总额
    private Double lrze;
    //出口年份
    private String cknf;
    //净资产
    private Double jzc;
    //净资产年份
    private String jzcnf;
    //纳税总额
    private Double nsze;
    //企业产值
    private Double qycz;
    //企业基本信息表id
    private String qyid;
}
