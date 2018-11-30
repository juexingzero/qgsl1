package com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import lombok.Data;

import java.util.List;

/**
 * 社团入会基本信息保存
 */
@Data
public class StxxBaseResult {
    private String stmc;         //社团名称
    private String stywmc;      //社团英文名称
    private String zjlx;        //证件类型
    private String zjhm;        //证件号码
    private String pzdjjg;         //批准登记机关
    private String ywzgbm;         //业务主管部门
    private String clsj;         //成立时间
    private Integer cysl;        //成员数量
    private String mscgzryxm;       //秘书处工作人员姓名
    private String mscgzryzw;       //秘书处工作人员职务
    private String mscgzrydh;       //秘书处工作人员电话
    private String dh;         //电话
    private String dz;         //地址
    private String qywz;         //企业网址
    private Integer zip_code;        //邮编
    private String fax;         //传真



    //
    private String stid;
    private String joinId;

    private String approve_fail_info;//审批不通过类型
}
