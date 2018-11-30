package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业公益帮扶情况表
 * table :member_qy_qygybfqk
 */
@Data
public class MemberQyQygybfqk {
    //公益帮扶主键
    private String gybfzj;
    //公益帮扶项目名称
    private String gybfxmmc;
    //公益帮扶捐物折合总额
    private Double gybfjwzhze;
    //企业基本信息表主键--用于表关联
    private String jbxxzj;
    //公益帮扶项目惠及村名
    private String gybfxmhjcm;
    //公益帮扶用途分类（ 参考“公益帮扶用途分类”）
    private String gybfytfl;
    //公益帮扶建档立卡人口数
    private String gybfjdlk_rks;
    //公益帮扶受捐款物合计
    private Double gybfsjkwhj;
    //公益帮扶收集捐赠物合计
    private Double gybfsjjz_whj;
    //公益帮扶建档立卡户数
    private String gybfjdlkhs;
    //公益帮扶项目受益总人数
    private String gybfxmsy_zrs;
    //公益帮扶接收单位
    private String gybfjsdw;
    //企业基本信息表id
    private String qyid;
}
