package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 *个人入会，内容人员办公表,关联系统内容人员
 * table:member_ry_nbrybg
 */
@Data
public class MemberRyNbrybg {
    private String id;              //ID
    private String rybm;              //人员 ID
    private String bgdz;              //办公地址
    private String bgdh;              //办公电话
    private String msdh;              //秘书电话
    private String nbjgbm;              //内部机构编码
    private String nbjgmc;              //内部机构名称
    private String drsj;              //调入时间
    private String ryid;            //人员表id
}
