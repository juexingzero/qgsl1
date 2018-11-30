package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @Title: MeetingListResult.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meetingmanager
* @Description: TODO(会议详情--会议资料)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class MeetingData {
	private String meeting_id; //会议id
	private String meeting_data; //会议资料
	private String[] data; //切割后的会议资料
	
}
