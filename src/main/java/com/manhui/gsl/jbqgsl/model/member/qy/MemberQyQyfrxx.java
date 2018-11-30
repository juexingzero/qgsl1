package com.manhui.gsl.jbqgsl.model.member.qy;

import lombok.Data;

/**
 * 企业会员，企业法人信息表
 * table :member_qy_qyfrxx
 */
@Data
public class MemberQyQyfrxx {
    //法人信息主键
    private String frxxzj;
    //联系电话
    private String lxdh;
    //学历（参考“学历代码”）
    private String xl;
    //企业基本信息表主键
    private String jbxxzj;
    //工商联职务
    private String gslzw;
    //姓名
    private String xm;
    //出生年月
    private String csny;
    //政治面貌(党派身份)
    private String zzmm;
    //社会职务
    private String shzw;
    //所有权经营权是否合一
    private String syqjyqsfhy;
    //性别（参考“人的性别代码”）
    private String xb;
    //公司职务
    private String gszw;
    //股权情况
    private String gqqk;
    //民族（参考“民族代码”）
    private String mz;
    //企业基本信息表id
    private String qyid;

    //籍贯(出生地)
    private String jg;
    //身份证
    private String identity;
    //职称
    private String zc;
    //座机
    private String zj;

    private String state;               //状态
}
