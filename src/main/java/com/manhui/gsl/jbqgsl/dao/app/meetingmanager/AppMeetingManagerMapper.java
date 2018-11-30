package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.ManagerAndRecept;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingAddress;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingContent;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingData;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingListResult;
import com.manhui.gsl.jbqgsl.model.MeetingManager;

/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(会务活动--会议管理数据交互层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingManagerMapper {
	/**
	 * 
	  * 查看我的会议(待开会议,已开会议,)
	 *
	 */
	List<MeetingListResult> queryMeetingList(Map<String, Object> conditionMap);
	/**
	 * 会议详情--会议内容
	 */
	MeetingContent getMeetingContent(String meeting_id);
	/**
	 * 会议详情--会议资料
	 */
	MeetingData getMeetingData(@Param("meeting_id")String meeting_id);
	/**
	 * 会议详情--会议回执
	 */
	int meetingResponse(Map<String, Object> conditionMap);
	/**
	 * 会议详情--会议导航
	 */
	MeetingAddress getMeetingAddress(@Param("meeting_id")String meeting_id);
	/**
	 * 查询会议开始时间 --结束时间
	 */
	MeetingManager queryMeetingTime(@Param("meeting_id")String meeting_id);
	/**
	 * 会议详情--会议纪要
	 */
	MeetingContent getMeetingMinutes(@Param("meeting_id")String meeting_id);
	/**
	 * 根据meeting_id获取会议回执时间
	 */
	String getMeetingManager(@Param("meeting_id")String meeting_id);
	/**
	 * 回执请假的条件
	 */
	ManagerAndRecept getMeetingManagerAndRecept(@Param("meeting_id")String meeting_id, @Param("member_id")String member_id);
}
