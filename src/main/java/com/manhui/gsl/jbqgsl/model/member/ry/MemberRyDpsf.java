package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 *个人入会，党派身份管理表
 * table:member_ry_dpsf
 */
@Data
public class MemberRyDpsf {
    private String id;              //ID
    private String rybm;              //人员 ID
    private String dpsf;              //党派身份（参考“政治面貌”）
    private String jrdprq;              //加入党派日期
    private String dppj;              //党派拼接
    private String ryid;            //人员表id
}
