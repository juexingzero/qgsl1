package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingReceipt;

/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(会务活动数据交互层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingReceptMapper {
	
	/**
	 * 会议详情--会议回执
	 */
	int meetingResponse(Map<String, Object> conditionMap);
	/**
	 * 会议详情--会议回执详情
	 */
	MeetingReceipt queryMeetingReceptDetail(Map<String, Object> conditionMap);
	/**
	 * 会议回执--保存
	 */
	int saveMeetingRecept(Map<String, Object> conditionMap);
	/**
	 * 会议回执--请假
	 */
	int meetingReceptLeave(Map<String, Object> conditionMap);

}
