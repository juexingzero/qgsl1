package com.manhui.gsl.jbqgsl.service.app.membermanager.st;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.StxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjbxx;

import java.util.List;
import java.util.Map;

/**
 * 社团基本信息表
 */
public interface AppMemberJgStjbxxService {
    /**
     * 保存社团基本信息
     * @param entity
     * @return
     */
    JsonResult addLeagueBase(StxxBaseResult entity);

    /**
     * 社团基本信息修改
     * @param entity
     * @return
     */
    JsonResult updateLeagueBase(StxxBaseResult entity);

    MemberJgStjbxx queryById(String id);

    /**
     * 查询 入会信息
     * @param joinId
     * @return
     */
    Map<String,Object> queryLeaguelMemberData(String joinId);

    /**
     * 社团入会，继续入会
     * @param addMemberParamMap
     * @return
     */
    JsonResult leagueContinueAddMember(Map<String,Object> addMemberParamMap);
}
