package com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import lombok.Data;

import java.util.List;

/**
 * 人员入会基本信息保存
 */
@Data
public class RyxxBaseResult {

    //人员基本信息表
    private String xm;              //姓名
    private String xb;              //性别
    private String csrq;              //出生日期
    private String jg;              //籍贯（参考“行政区划”）
    private String mz;              //民族（参考“民族”）
    private String hyzk;              //婚姻状况（参考“婚姻状况”）
    private String zy;              //职业（参考“职业分类与代码”）
    private String zc;              //职称（参考“职称代码”）
    private String ryzjlx;              //人员证件类型（参考：“证件类型”）
    private String ryzjbm;              //人员证件编码
    private String peopleType;          //人员类型
    //党派身份表
    private String dpsf;              //党派身份（参考“政治面貌”）

    //学历学位信息表
    private String xlmc;              //学历名称
    private String xxmc;              //学校（单位）名称

    //通讯信息表
    private String yddh;              //移动电话
    private String gddh;              //固定电话（座机）
    private String dzyx;              //电子邮箱
    private String gsmc;               //公司名称
    private String gszw;                //公司职务

    //职务信息表
    private String rdzw;            //人大职务
    private String zxzw;            //政协职务
    private String ttxhmc;          //参与的团体协会名称
    private String ttxhzw;             //参加的团体协会职务


    private List<MemberRyRyzj> ryzjList;//人员证件集合数据

    private List<MemberRyFgrszyry> ryryList;//人员荣誉集合数据
    //
    private String joinId;
    private String ryid;

    private String approve_fail_info;//审批未通过类型
}
