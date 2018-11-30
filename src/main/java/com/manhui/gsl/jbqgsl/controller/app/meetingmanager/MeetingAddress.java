package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @Title: MeetingListResult.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meetingmanager
* @Description: TODO(会议详情--会议导航)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class MeetingAddress {
	private String meeting_id; //会议id
	private String meeting_address; //会议地址
	private String meeting_navigation; //会议导航地址
	private String meeting_ll; //会议导航经纬度（经度，纬度，用中文逗号隔开）
	
}
