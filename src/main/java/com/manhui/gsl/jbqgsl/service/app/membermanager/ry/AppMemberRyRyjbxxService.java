package com.manhui.gsl.jbqgsl.service.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyjbxx;

import java.util.Map;

/**
 * 会员 个人入会，人员基本信息管理
 */
public interface AppMemberRyRyjbxxService {

	void save(MemberRyRyjbxx entity);

	/**
	 * 查询 个人入会，入会信息
	 * @param joinId
	 * @return
	 */
	Map<String,Object> queryIndividualMemberData(String joinId);

	MemberRyRyjbxx queryById(String id);

	int updateById(MemberRyRyjbxx entity);

	/**
	 * 保存人员 入会基本信息
	 * @param entity
	 */
    void addIndividualMemberData(RyxxBaseResult entity);

	/**
	 * 修改人员 入会基本信息
	 * @param entity
	 */
	JsonResult updateIndividualMemberData(RyxxBaseResult entity);

	/**
	 * 个人入会，继续入会
	 * @param addMemberParamMap
	 */
	JsonResult individualContinueAddMember(Map<String,Object> addMemberParamMap);
}
