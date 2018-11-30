
package com.manhui.gsl.jbqgsl.dao.app.memberapproval;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberHistoryInfo;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.result.CommerceResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
/**
* @Title: CommerceMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.commerce
* @Description: TODO(商会入会审批数据交互层)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppMemberApprovalMapper {
	/**
	 * 根据登录账号核对商会信息
	 */
	CommerceResult checkAccound(@Param("user_phone")String user_phone);
	/**
	 * 根据会员ID获取到个人会员基本详情信息
	 */
	Map<String, Object> getMemberPersonBaseDetailInfo(Map<String, Object> conditionMap);
	/**
	 * 根据会员ID获取到团体会员基本详情信息
	 */
	Map<String, Object> getMemberGroupBaseDetailInfo(Map<String, Object> conditionMap);
	/**
	 * 根据会员ID获取到企业会员基本详情信息
	 */
	Map<String, Object> getMemberCompanyBaseDetailInfo(Map<String, Object> conditionMap);
	/**
	 * 会员商会中间表--修改
	 */
	int updateMemberjoinManager(Map<String, Object> dataMap);
	/**
	 * 人员基本信息表--修改
	 */
	int updateMemberRyjbxxPerson(Map<String, Object> dataMap);
	/**
	 * 证件信息表--修改(个人团体 企业共用)
	 */
	int updateMemberRyzjManager(Map<String, Object> dataMap);
	/**
	 * 荣誉表--修改(个人团体 企业共用)
	 */
	int updateMemberRyManager(Map<String, Object> dataMap);
	/**
	 * 向会员审批表中新增一条记录
	 */
	void saveMemberApprove(Map<String, Object> dataMap);
	/**
	 * 企业基本信息
	 */
	int updateMemberQyjbxx(Map<String, Object> dataMap);
	/**
	 * 企业法人
	 */
	int updateMemberQyfrxx(Map<String, Object> dataMap);
	/**
	 * 企业介绍
	 */
	void updateMemberQyJs(Map<String, Object> dataMap);
	/**
	 * 根据审批人id查询审批记录
	 */
	List<MemberHistoryInfo> queryApprovalHistory(Map<String, Object> dataMap);
	/**
	 * 根据会员id获取到商会基本信息
	 */
	CommerceResult queryCommerce(@Param("member_id")String member_id);
	/**
	 * 根据会员名字以及审批人ID模糊查询被审批会员列表
	 */
	List<MemberHistoryInfo> queryMemberList(Map<String, Object> conditionMap);
	/**
	 * 会议签到--根据会员ID获取会员 职务以及会员姓名 电话
	 */
	Map<String, Object> queryMemberPerson(@Param("member_id")String member_id);

}
