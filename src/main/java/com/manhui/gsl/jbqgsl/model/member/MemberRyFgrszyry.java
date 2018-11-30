package com.manhui.gsl.jbqgsl.model.member;

import lombok.Data;

import java.util.List;

/**
 * 非公人士主要荣誉表
 * table :member_ry_fgrszyry
 */
@Data
public class MemberRyFgrszyry {
    //ID
    private String id;
    //人员 ID
    private String rybm;
    //荣誉名称
    private String rymc;
    //主要荣誉类别（参考“荣誉类别”）
    private String zyrylb;
    //荣誉级别（参考“荣誉级别”）
    private String ryjb;
    //届
    private String je;
    //获取时间
    private String hqsj;
    //荣誉证书编号
    private String ryzsbh;
    //颁发单位
    private String bfdw;
    //获得原因
    private String hdyy;
    //其他主要荣誉
    private String qtzyry;
    //管理表id
    private String paramid;

    private String state;               //状态
}
