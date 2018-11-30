package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 *个人入会，学位学历记录表
 * table:member_ry_xlyxw
 */
@Data
public class MemberRyXlyxw {
    private String id;              //ID
    private String rybm;              //人员 ID
    private String xldm;              //学历代码（参考“学历代码”）
    private String xlmc;              //学历名称
    private String byrq;              //毕（肄）业日期
    private String xxmc;              //学校（单位）名称
    private String sxzydm;              //所学专业代码
    private String xxwcqk;              //学习完成情况
    private String xwmc;              //学位名称
    private String xwdm;              //学位代码（参考“学位”）
    private String xwsyrq;              //学位授予日期
    private String xwsydw;              //学位授予单位
    private String ryid;            //人员表id
}
