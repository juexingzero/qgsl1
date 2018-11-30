package com.manhui.gsl.jbqgsl.service.app.meetingmanager.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteObjectResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteOptionListResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteOptionResult;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingManagerMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingVoteMapper;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingVoteService;
/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(会议管理--会议投票业务实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppMeetingVoteServiceImpl implements IAppMeetingVoteService {
	@Resource
	private AppMeetingVoteMapper appMeetingVoteMapper;
	@Resource
	private AppMeetingManagerMapper appMeetingManagerMapper;
	/**
	 * 会议管理--投票列表展示
	 */
	@Override
	public MeetingVoteOptionResult getMeetingVoteList(Map<String, Object> conditionMap) {
		List<MeetingVoteOptionListResult> meetingVoteOptionList = appMeetingVoteMapper.getMeetingVoteList(conditionMap);
		MeetingVoteOptionResult meetingVoteOption = new MeetingVoteOptionResult();
		if(meetingVoteOptionList !=null &&meetingVoteOptionList.size()>0 ) {
			meetingVoteOption.setNum(meetingVoteOptionList.size());
			meetingVoteOption.setMeeting_id(meetingVoteOptionList.get(1).getMeeting_id());
			meetingVoteOption.setAllow_item(meetingVoteOptionList.get(1).getAllow_item());
			meetingVoteOption.setMeetings(meetingVoteOptionList.get(1).getMeetings());
			meetingVoteOption.setDataList(meetingVoteOptionList);
			meetingVoteOption.setVote_options(meetingVoteOptionList.get(1).getVote_options());
		}
		return meetingVoteOption;
	}
	/**
	 * 会议管理--会议投票表详情 --判断选项以及投票时间
	 */
	@Override
	public MeetingVoteObjectResult queryMeetingVote(String meeting_id) {
		return appMeetingVoteMapper.queryMeetingVote(meeting_id);
	}
	

}
