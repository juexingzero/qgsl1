package com.manhui.gsl.jbqgsl.service.app.membermanager;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.StxxBaseResult;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;

import java.util.List;
import java.util.Map;

/**
 * 会员 入会管理
 */
public interface AppMemberJoinManagerService {

	/**
	 * 查询集合 数据，不分页
	 * @param entity
	 * @return
	 */
	List<MemberJoinManager> queryAllList(MemberJoinManager entity);

	void save(MemberJoinManager joinManager);

	/**
	 * \获得手机端 个人入会 入会申请填写资料
	 * @return
	 */
    Map<String,Object> getAppApplyData(Map<String,Object> paramMap);

	int updateById(MemberJoinManager joinManager);

	/**
	 * 根据joinObjIdList 查询数据
	 * @param entity joinObjIdList=商会id集合，state=数据状态（参照系统参数,不传查询所有）
	 * @return
	 */
	List<MemberJoinManager> queryByjoinObjIdList(MemberJoinManager entity);
	/**
	 * 根据商会ID以及状态,获取到入会会员的基本信息
	 */
	List<MemberBaseInfo> queryMemberBaseInfo(MemberJoinManager joinManager);

	/**
	 * 判断是否能 提交入会 申请
	 * @param dataMap
	 * @return
	 */
    JsonResult sendApplyJudge(Map<String,Object> dataMap);

	/**
	 * 验证修改 memberjoinManager表数据状态
	 * @param joinId
	 * @return
	 */
	JsonResult verifyJoinManagerState(String joinId,List<MemberJoinManager> joinManagerList);

	/**
	 * 新增个人入会 基本信息
	 * @param entity
	 * @param joinManager
	 * @return
	 */
    JsonResult addUserMember(RyxxBaseResult entity, MemberJoinManager joinManager);

	/**
	 * 修改个人入会 基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
    JsonResult updateUserMember(MemberJoinManager joinManager, RyxxBaseResult entity);

	/**
	 * 新增企业入会基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	JsonResult addCompanyMember(MemberJoinManager joinManager, QyxxBaseResult entity);

	/**
	 * 企业入会，基本信息修改
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	JsonResult updateCompanyMember(MemberJoinManager joinManager, QyxxBaseResult entity);

	/**
	 * 新增团体入会，基本新
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	JsonResult addLeagueMember(MemberJoinManager joinManager, StxxBaseResult entity);

	/**
	 * 修改社团 入会基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
    JsonResult updateLeagueMember(MemberJoinManager joinManager, StxxBaseResult entity);

	/**
	 * 查询商会人员信息
	 * @return
	 */
	PageInfo<Map<String,Object>> queryMemberStaffList(ParamModel param);

	/**
	 * 继续加入商会
	 * @param joinManager
	 * @return
	 */
	JsonResult continueAddMember(MemberJoinManager joinManager,String joinId);
}
