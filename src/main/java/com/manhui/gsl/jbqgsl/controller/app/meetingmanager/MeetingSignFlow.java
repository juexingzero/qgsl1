package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @Title: MeetingListResult.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meetingmanager
* @Description: TODO(会议签到实体类)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class MeetingSignFlow {
	private String sign_id; //签到ID
	private String meeting_id; //会议id
	private String member_id; //会员ID
	private String company_title; //公司职务
	private String sign_name; //签到人
	private String sign_phone; //签到热电话
	private String sing_time; //签到时间
	
}
