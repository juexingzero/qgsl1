package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @Title: MeetingListResult.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meetingmanager
* @Description: TODO(会议以及回执表)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class ManagerAndRecept {
	private String meeting_id; //会议id
	private String member_id; //会员id
	private String meeting_starttime; //会议开始时间
	private String participate_state; //是否参与（0：否，1：是，默认：0）
	
}
