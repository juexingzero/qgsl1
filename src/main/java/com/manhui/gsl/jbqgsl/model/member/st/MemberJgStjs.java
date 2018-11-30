package com.manhui.gsl.jbqgsl.model.member.st;

import lombok.Data;

/**
 *社团入会，社团介绍信息表
 * table:member_jg_stjs
 */
@Data
public class MemberJgStjs {
    private String id;         //
    private String stjj;         //社团简介
    private String stzzjg;         //社团组织结构
    private String state;         //状态
    private String stid;         //社团基本信息表id

    //不与数据库同步id

    private String joinId;//
}
