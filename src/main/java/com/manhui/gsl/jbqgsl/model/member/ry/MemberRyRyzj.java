package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 *个人入会，人员证件表
 * table:member_ry_ryzj
 */
@Data
public class MemberRyRyzj {
    private String id;              //ID
    private String rybm;              //人员 ID
    private String ryzjlx;              //人员证件类型（参考：“证件类型”）
    private String ryzjbm;              //人员证件编码
    private String zjqfrq;              //证件签发日期
    private String zjyxq;              //证件有效期
    private String zjyxqsrq;              //证件有效起始日期
    private String zhyxjsrq;              //证件有效结束日期
    private String zjqfdd;              //证件签发地点
    private String se;              //省（参考：“行政区划”）
    private String si;              //市（参考：“行政区划”）
    private String xa;              //县（参考：“行政区划”）
    private String zjzt;              //证件状态（0：无效，1：有效，默认：1）
    private String ryid;            //人员表id
    private String fileUrl;         //证件照片地址
    private String pan;             //证件正反面(1=正面，2=反面)

    private String state;               //状态
}
