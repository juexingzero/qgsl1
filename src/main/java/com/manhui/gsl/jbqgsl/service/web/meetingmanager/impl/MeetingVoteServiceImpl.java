package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingVoteMapper;
import com.manhui.gsl.jbqgsl.model.MeetingVote;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingVoteService;

@Service
public class MeetingVoteServiceImpl implements MeetingVoteService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingVoteServiceImpl.class);
	
	@Resource
	private MeetingVoteMapper meetingVoteMapper;

	@Override
	public Integer insertMeetingVote(MeetingVote mv) {
		logger.info( "----- 系统参数-添加投票主体信息 -----" );
		return meetingVoteMapper.insertMeetingVote(mv);
	}

	@Override
	public MeetingVote queryMeetingVote(String meetingId) {
		logger.info( "----- 系统参数-根据id查询投票主体信息 -----" );
		return meetingVoteMapper.queryMeetingVote(meetingId);
	}

	@Override
	public Integer updateMeetingVote(MeetingVote mv) {
		logger.info( "----- 系统参数-根据id修改投票主体信息 -----" );
		return meetingVoteMapper.updateMeetingVote(mv);
	}

}
