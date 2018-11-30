
/**
* @Title: MemberInfo.java
* @Package com.manhui.gsl.jbqgsl.controller.app.memberapproval
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年11月6日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.controller.app.memberapproval;

import lombok.Data;

/**
* @ClassName: MemberBaseInfo
* @Description: TODO(个人 团体 企业会员 审批历史页面展示)
* @author LiuBin
* @date 2018年11月6日
*/
@Data
public class MemberHistoryInfo {
	private String joinId;//会员商会中间表
	private String approve_id;//会员审批记录表id
	private String approver_id;//审批人ID
	private String approver_name;//审批人姓名
	private String member_id;//会员ID
	private String member_name;//会员名称
	private String contact_name;//联系人名称
	private String contact_telephone;//联系人电话
	private String member_type;//会员类型
	private String create_time;//申请时间
	private String approval_time;//审批时间
	private String approve_status;//审批状态
	private String approve_fail_info;//未通过部分
	private String approve_fail_reason;//未通过原因
	
}
