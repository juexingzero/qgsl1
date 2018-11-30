package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业业务情况表
 * table :member_qy_qyywqk
 */
@Data
public class MemberQyQyywqk {
    //业务情况主键
    private String ywqkzj;
    //主营业务
    private String zyyw;
    //企业基本信息表主键
    private String jbxxzj;
    //主要产品与服务
    private String zycpyfw;
    //经营模式
    private String jyms;
    //主营业务占营收总额比例
    private String zyywzyszebl;
    //企业基本信息表id
    private String qyid;
}
