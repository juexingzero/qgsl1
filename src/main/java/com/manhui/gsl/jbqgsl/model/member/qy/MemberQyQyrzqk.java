package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业认证情况表
 * table :member_qy_qyrzqk
 */
@Data
public class MemberQyQyrzqk {
    //认证情况主键
    private String rzqkzj;
    //环境标志认证产品种类名称
    private String hjbzrzcpzlmc;
    //环境标志认证产品
    private String hjbzrzcp;
    //荣誉级别
    private String ryjb;
    //企业基本信息表主键
    private String jbxxzj;
    //双软认证等级
    private String srrzdj;
    //高新技术企业认证机构(部门)
    private String gxjsqyrzjg;
    //高新技术企业所在区域
    private String gxjsqyszqy;
    //获得时间
    private String hdsj;
    //双软认证日期
    private String srrzrq;
    //外贸自营认证部门
    private String wmzyrzbm;
    //外贸自营认证批次
    private String wmzyrzpc;
    //高新技术企业认证批次
    private String gxjsqyrzpc;
    //是否获得外贸自营进出口权
    private String sfhdwmzyjckq;
    //是否被认证为高新技术企业
    private String sfbrzwgxjsqy;
    //环境标志证书编号
    private String hjbzzsbh;
    //环境标志批准时间
    private String hjbzpzsj;
    //高新技术企业所在地区
    private String gxjsqyszdq;
    //所获名誉名称
    private String shmymc;
    //颁发机构
    private String bfjg;
    //是否通过质量管理认证
    private String sftgzlglrz;
    //质量认证部门
    private String zlglrzmb;
    //企业基本信息表id
    private String qyid;
}
