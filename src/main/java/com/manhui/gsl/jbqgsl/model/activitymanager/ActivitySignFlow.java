package com.manhui.gsl.jbqgsl.model.activitymanager;

import lombok.Data;

/**
 * @Title: ActivitySignFlow.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(活动签到流水表)
 * @author WangSheng
 * @date 2018年11月7日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class ActivitySignFlow {
	
	private String sign_id;//签到ID
	
	private String activity_id;//活动ID
	
	private String member_id;//会员ID
	
	private String group_name;//团体名称/名称
	
	private String sign_title;//职位
	
	private String sign_name;//签到人姓名
	
	private String sign_phone;//签到人手机
	
	private String sign_time;//签到时间

}
