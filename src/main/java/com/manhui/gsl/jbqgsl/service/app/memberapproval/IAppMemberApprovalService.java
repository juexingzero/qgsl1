
package com.manhui.gsl.jbqgsl.service.app.memberapproval;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberHistoryInfo;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.result.CommerceResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(审批)
* @author LiuBin
* @date 2018年11月5日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppMemberApprovalService {
	/**
	 * 根据登陆的手机账号,核对商会信息
	 */
	CommerceResult checkAccound(String user_phone);
	/**
	 * 根据商会id获取到待审批基本信息(个人,企业,团体)
	 */
	List<MemberBaseInfo> getMemberList(String id);
	/**
	 * 根据会员ID获取到申请会员的所有数据--个人,企业,团体会员基本信息
	 */
	Map<String, Object> getMemberBaseInfoDetail(Map<String, Object> conditionMap);
	/**
	 * APP端--审批
	 */
	void updateApprovelMember(Map<String, Object> dataMap);
	/**
	 * 根据审批人id查询审批记录
	 */
	List<MemberHistoryInfo> queryApprovalHistory(Map<String, Object> dataMap);
	/**
	 * 根据会员名字以及审批人ID模糊查询被审批会员列表
	 */
	List<MemberHistoryInfo> queryMemberList(Map<String, Object> conditionMap);



}
