package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @Title: MeetingListResult.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meetingmanager
* @Description: TODO(我的会议--数据展示列表)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class MeetingListResult {
	private String meeting_id; //会议id
	private String member_id; //会员ID
	private String meeting_state; //会员ID
	private String meeting_theme; //会议主题
	private String meeting_address;//会议地址
	private String meeting_ll;//会议导航经纬度（经度，纬度，用中文逗号隔开）
	private String meeting_navigation;//会议导航
	private String allow_select;//允许选择数
	private String meeting_starttime;//会议开始时间（格式：yyyy-MM-dd HH:mm）
	private String meeting_endtime;//会议结束时间（格式：yyyy-MM-dd HH:mm）
	//--签到表字段
	private String receipt_id;//回执ID
	private String participate_state;//是否参加0:否 1:是
	private String is_leave;//是否请假 0:否 1:是
	//--会议人员表字段 
	private String sign_id;//签到id
	private String vote_options;//投票选项
}
