package com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import lombok.Data;

import java.util.List;

/**
 * 企业法人信息数据
 */
@Data
public class QyfrxxResult {
    /******企业法人信息表*******/
    //姓名
    private String xm;
    //出生年月
    private String csny;
    //性别（参考“人的性别代码”）
    private String xb;
    //籍贯
    private String jg;
    //身份证
    private String identity;
    //民族（参考“民族代码”）
    private String mz;
    //政治面貌(党派身份)
    private String zzmm;
    //学历（参考“学历代码”）
    private String xl;
    //公司职务
    private String gszw;
    //职称
    private String zc;
    //联系电话
    private String lxdh;
    //座机
    private String zj;
    //股权情况
    private String gqqk;
    //所有权经营权是否合一
    private String syqjyqsfhy;

    //职务信息表,MemberRyFgrszyzw
    private String rdzw;            //人大职务
    private String zxzw;            //政协职务
    private String ttxhmc;          //参与的团体协会名称
    private String ttxhzw;             //参加的团体协会职务
    private String shzw;            //社会职务


    private String qyid;
    private String frxxzj;//主键
    private String joinId;
}
