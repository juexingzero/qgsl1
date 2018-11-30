package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: MeetingSignFlow.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(会议签到流水表)
 * @author WangSheng
 * @date 2018年11月5日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class MeetingSignFlow {
	
	private String sign_id;//签到ID
	
	private String meeting_id;//会议ID
	
	private String member_id;//会员ID
	
	private String company_title;//企业职务
	
	private String company_name;//公司名称
	
	private String sign_name;//签到人姓名
	
	private String sign_phone;//签到人手机
	
	private String sing_time;//签到时间

}
