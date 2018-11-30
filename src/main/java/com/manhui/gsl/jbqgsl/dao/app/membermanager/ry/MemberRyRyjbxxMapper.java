package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyjbxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 入会 人员基本信息数据子类表
 */
@Mapper
public interface MemberRyRyjbxxMapper {

	void save(MemberRyRyjbxx entity);

	int updateById(MemberRyRyjbxx entity);

	/**
	 * 查询人员 个人入会申请，基本信息
	 * @param joinId
	 * @return
	 */
    Map<String,Object> getAppIndividualApplyDataById(String joinId);

	MemberRyRyjbxx queryById(String id);
}
