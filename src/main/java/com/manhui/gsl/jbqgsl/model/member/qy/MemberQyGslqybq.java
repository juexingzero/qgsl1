package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，工商联企业标签表
 * table :member_qy_gslqybq
 */
@Data
public class MemberQyGslqybq {
    //企业标签主键
    private String qybbqzj;
    //企业基本信息表主键
    private String jbxxzj;
    //企业标签
    private String qybq;
    //企业规模（参考“企业规模”）
    private String qygm;

    //企业基本信息表id
    private String qyid;
}
