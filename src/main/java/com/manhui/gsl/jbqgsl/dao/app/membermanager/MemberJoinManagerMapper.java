package com.manhui.gsl.jbqgsl.dao.app.membermanager;

import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.model.PersonMember;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ParamModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会员入会管理表
 */
@Mapper
public interface MemberJoinManagerMapper {

	void save(MemberJoinManager entity);

	int updateById(MemberJoinManager entity);

	/**
	 * 查询集合 数据，不分页
	 * @param entity
	 * @return
	 */
    List<MemberJoinManager> queryAllList(MemberJoinManager entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	List<MemberJoinManager> queryByjoinObjIdList(MemberJoinManager entity);
	/**
	 * 根据商会ID以及状态,获取到入会会员的基本信息(会员名称,入会时间 会员类型)
	 */
	List<MemberBaseInfo> queryMemberBaseInfo(MemberJoinManager joinManager);

	/**
	 * 删除审核未通过数据
	 * @param entity
	 */
    void delByCreateUserIdAndState(MemberJoinManager entity);
    /**
     * 通过会员id获取到会员 的详情
     */
	MemberJoinManager getMemberInfoByMemberId(@Param("member_id")String member_id);

	/**
	 * 分页查询数据
	 * @param param
	 * @return
	 */
    List<Map<String,Object>> queryMemberStaffListPage(ParamModel param);
    /**
     * 数据上报--通过user_id获取到memberId
     */
	Map<String,Object>  queryMemberId(@Param("user_id")String user_id);
}
