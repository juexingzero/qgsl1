package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业帮扶基本情况表
 * table :member_qy_qybfjbqk
 */
@Data
public class MemberQyQybfjbqk {
    //帮扶主键
    private String bfzj;
    //贫困村名称
    private String pkcmc;
    //村总人口数量
    private String czrksl;
    //企业基本信息表主键--用于表关联
    private String jbxxzj;
    //帮扶建档立卡贫苦总人口数
    private String bfjdlkpkzrks;
    //帮扶建档立卡贫困总户数
    private String bfjdlkpk_zhs;
    //村所属行政区划
    private String cssxzqh;
    //开始时间
    private String kssj;
    //企业基本信息表id
    private String qyid;
}
