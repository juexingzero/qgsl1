package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteObjectResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteOptionListResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteOptionResult;


/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(会务管理--会议投票)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingVoteMapper {
	/**
	 * 会议管理--会议投票列表展示
	 */
	List<MeetingVoteOptionListResult> getMeetingVoteList(Map<String, Object> conditionMap);
	/**
	 * 会议管理--会议投票表详情 --判断选项以及投票时间
	 */
	MeetingVoteObjectResult queryMeetingVote(String meeting_id);


}
