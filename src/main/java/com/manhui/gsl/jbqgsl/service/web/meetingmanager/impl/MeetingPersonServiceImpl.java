package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingPersonMapper;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingPersonService;

@Service
public class MeetingPersonServiceImpl implements MeetingPersonService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingPersonServiceImpl.class);
	
	@Resource
	private MeetingPersonMapper meetingPersonMapper;

	@Override
	public Integer insertMeetingPerson(List<MeetingPerson> mpList) {
		logger.info( "----- 系统参数-添加参会人员信息 -----" );
		return meetingPersonMapper.insertMeetingPerson(mpList);
	}

	@Override
	public List<MeetingPerson> queryMeetingPersonList(String meeting_id) {
		logger.info( "----- 系统参数-根据id查询参会人员信息 -----" );
		return meetingPersonMapper.queryMeetingPersonList(meeting_id);
	}

	@Override
	public Integer updateMeetingPerson(MeetingPerson mp) {
		logger.info( "----- 系统参数-根据id修改参会人员信息 -----" );
		return meetingPersonMapper.updateMeetingPerson(mp);
	}

	@Override
	public Integer deleteMeetingPerson(MeetingPerson mp) {
		logger.info( "----- 系统参数-根据id删除参会人员信息 -----" );
		return meetingPersonMapper.deleteMeetingPerson(mp);
	}

	@Override
	public Integer queryMeetingPersonNum(String meeting_id) {
		logger.info( "----- 系统参数-根据id查询参会人员数量 -----" );
		return meetingPersonMapper.queryMeetingPersonNum(meeting_id);
	}

	@Override
	public List<MeetingPerson> getMeetingPersonLists(String meeting_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会人员列表信息 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return meetingPersonMapper.getMeetingPersonLists(conditions);
	}

	@Override
	public Integer queryMeetingPersonTotal(String meeting_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会人员列表总数 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return meetingPersonMapper.queryMeetingPersonTotal(conditions);
	}

	@Override
	public Integer querypersonNum(String vote_options) {
		logger.info( "----- 系统参数-获取特定投票人数 -----" );
		return meetingPersonMapper.querypersonNum(vote_options);
	}

	@Override
	public List<MeetingPerson> queryvotePerson(String meeting_id,String vote_options, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取投票人员列表数据 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put("vote_options", vote_options);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return meetingPersonMapper.queryvotePerson(conditions);
	}

	@Override
	public Integer queryvotePersonNum(String meeting_id, String vote_options, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取投票人员列表总数-----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put("vote_options", vote_options);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return meetingPersonMapper.queryvotePersonNum(conditions);
	}


}
