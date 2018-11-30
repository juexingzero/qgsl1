package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingPersonMapper;
import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingVoteOptionsMapper;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.model.MeetingVoteOptions;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingPersonService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingVoteOptionsService;

@Service
public class MeetingVoteOptionsServiceImpl implements MeetingVoteOptionsService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingVoteOptionsServiceImpl.class);
	
	@Resource
	private MeetingVoteOptionsMapper meetingVoteOptionsMapper;


	@Override
	public Integer insertMeetingVoteOptions(List<MeetingVoteOptions> mvoList) {
		 logger.info( "----- 系统参数-添加投票选项信息 -----" );
		return meetingVoteOptionsMapper.insertMeetingVoteOptions(mvoList);
	}


	@Override
	public List<MeetingVoteOptions> queryMeetingVoteOptionsList(String vote_id) {
		 logger.info( "----- 系统参数-根据id查询投票选项信息 -----" );
		return meetingVoteOptionsMapper.queryMeetingVoteOptionsList(vote_id);
	}


	@Override
	public Integer updateMeetingVoteOptions(MeetingVoteOptions mv) {
		 logger.info( "----- 系统参数-根据id修改投票选项信息 -----" );
		return meetingVoteOptionsMapper.updateMeetingVoteOptions(mv);
	}


	@Override
	public Integer deleteMeetingVoteOptions(String vote_id) {
		logger.info( "----- 系统参数-根据id删除投票选项信息 -----" );
		return meetingVoteOptionsMapper.deleteMeetingVoteOptions(vote_id);
	}

}
