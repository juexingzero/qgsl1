package com.manhui.gsl.jbqgsl.service.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;

import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业介绍表
 */
public interface AppMemberQyQyjsService {
    /**
     *
     * @param entity
     */
    void save(MemberQyQyjs entity);

    /**
     * 根据企业基本信息表id 查询数据
     * @param qyid
     * @return
     */
    List<Map<String,Object>> queryByQyid(String qyid);

    /**\
     *
     * @param entity
     */
    void updateByid(MemberQyQyjs entity);

    /**
     * 新增企业 介绍信息
     * @param entity
     * @return
     */
    JsonResult addCompanyIntroduceData(QyxxBaseResult entity);

    /**
     * 查询企业介绍（只应对企业入会的，企业介绍内容）
     * 只查询，企业发展历程，企业简介，企业文化
     *
     * @param qyid 根据企业基本信息表id 查询，并且企业简介内容不能为空
     * @return
     */
    Map<String,Object> queryCompanyApplyJsData(String qyid);

}
