
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
* @Description: TODO(个人 团体 企业会员 基本信息实体类)
* @author LiuBin
* @date 2018年11月6日
*/
@Data
public class MemberBaseInfo {
	private String joinId;//入会中间表标志
	private String member_id;//会员ID
	private String member_name;//会员名称
	private String member_type;//会员类型
	private String apply_time;//申请时间
	private String contact_name;//联系人名字
	private String contact_phone;//联系人电话
	
}
