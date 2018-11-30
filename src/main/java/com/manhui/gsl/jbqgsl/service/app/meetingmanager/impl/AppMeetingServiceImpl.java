package com.manhui.gsl.jbqgsl.service.app.meetingmanager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.ManagerAndRecept;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingAddress;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingContent;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingData;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingListResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingAttendMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingManagerMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingPersonMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingReceptMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberJoinManagerMapper;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingManagerService;
/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(会务活动实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppMeetingServiceImpl implements IAppMeetingManagerService {
	@Resource
	private AppMeetingManagerMapper appMeetingManagerMapper;
	@Resource
	private AppMeetingReceptMapper appMeetingReceptMapper;
	@Resource
	private AppMeetingAttendMapper appMeetingAttendMapper;
	@Resource
	private AppMeetingPersonMapper appMeetingPersonMapper;
	@Resource
	private MemberJoinManagerMapper memberJoinManagerMapper;
	/**
	 * 查看我的会议(待开会议,已开会议,)
	 */
	@Override
	public List<MeetingListResult> getMeetingList(Map<String, Object> conditionMap) {
		MeetingPerson meetingPerson = appMeetingPersonMapper.queryMeetingGroupName(conditionMap);
		conditionMap.put("now", DateUtil.getTime());
		if(meetingPerson!=null &&meetingPerson.getGroup_name() !=null &&!"".equals(meetingPerson.getGroup_name())) {
			conditionMap.put("company_name",meetingPerson.getGroup_name() );
		}else {
			return null;
		}
		return appMeetingManagerMapper.queryMeetingList(conditionMap);
	}
	/**
	 * 会议详情--会议内容
	 */
	@Override
	public MeetingContent getMeetingContent(String meeting_id) {
		return appMeetingManagerMapper.getMeetingContent( meeting_id );
	}
	/**
	 * 会议详情--会议资料
	 */
	@Override
	public MeetingData getMeetingData(String meeting_id) {
		return appMeetingManagerMapper.getMeetingData( meeting_id );
	}
	/**
	 * 会议详情--会议导航
	 */
	@Override
	public MeetingAddress getMeetingAddress(String meeting_id) {
		return appMeetingManagerMapper.getMeetingAddress(meeting_id);
	}
	/**
	 * 会议开始时间--会议结束时间
	 */
	@Override
	public MeetingManager queryMeetingTime(String meeting_id) {
		return appMeetingManagerMapper.queryMeetingTime(meeting_id);
	}
	/**
	 * 会议纪要
	 */
	@Override
	public MeetingContent getMeetingMinutes(String meeting_id) {
		return appMeetingManagerMapper.getMeetingMinutes(meeting_id);
	}
	/**
	 * 根据meeting_id获取会议回执时间
	 */
	@Override
	public String getMeetingManager(String meeting_id) {
		return appMeetingManagerMapper.getMeetingManager( meeting_id );
	}
	/**
	 * 请假 判断条件获取
	 */
	@Override
	public ManagerAndRecept getMeetingManagerAndRecept(String meeting_id, String member_id) {
		return appMeetingManagerMapper.getMeetingManagerAndRecept(meeting_id,member_id);
	}


}
