package com.manhui.gsl.jbqgsl.service.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;

import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业基本信息表
 */
public interface AppMemberQyQyjbxxService {
    /**
     * 保存企业会员 入会基本信息
     * @param entity
     */
    void addCompanyMemberData(QyxxBaseResult entity);

    /**
     * 修改企业会员 入会基本信息
     * @param entity
     */
    void updateCompanyMemberData(QyxxBaseResult entity);

    /**
     *
     * @param jbxxzj
     * @return
     */
    MemberQyQyjbxx queryById(String jbxxzj);

    /**
     * 查询企业入会 数据
     * @param joinId
     * @return
     */
    Map<String,Object> queryCompanyMemberData(String joinId);

    /**
     * 企业入会 继续加入会
     * @param addMemberParamMap
     */
    JsonResult companyContinueAddMember(Map<String,Object> addMemberParamMap);
}
