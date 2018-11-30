package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingManagerMapper;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingManagerService;

@Service
public class MeetingManagerServiceImpl implements MeetingManagerService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingManagerServiceImpl.class);
	
	@Resource
	private MeetingManagerMapper meetingManagerMapper;

	@Override
	public List<MeetingManager> getMeetingManagerList(String meeting_theme, List<String> meeting_stateList, Integer pageIndex,
			Integer pageSize) {
		logger.info( "----- 系统参数-获取会议管理列表数据  -----" );
		 Map<String, Object> conditions = new HashMap<>();
	        conditions.put( "meeting_theme", meeting_theme );
	        conditions.put( "meeting_state", meeting_stateList );
	        conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		return meetingManagerMapper.getMeetingManagerList(conditions);
	}

	@Override
	public Integer queryMeetingManagerTotal(String meeting_theme, List<String> meeting_stateList, Integer pageIndex,
			Integer pageSize) {
		logger.info( "----- 系统参数-获取会议管理列表总数  -----" );
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "meeting_theme", meeting_theme );
        conditions.put( "meeting_state", meeting_stateList );
        conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);

		return meetingManagerMapper.queryMeetingManagerTotal(conditions);
	}

	@Override
	public Integer updateMeetingManager(MeetingManager mm) {
		logger.info( "----- 系统参数-修改会议管理 -----" );
		return meetingManagerMapper.updateMeetingManager(mm);
	}

	@Override
	public Integer insertMeetingManager(MeetingManager mm) {
		logger.info( "----- 系统参数-添加会议管理 -----" );
		return meetingManagerMapper.insertMeetingManager(mm);
	}

	@Override
	public MeetingManager queryMeetingManager(MeetingManager mm) {
		logger.info( "----- 系统参数-根据id查询会议管理 -----" );
		return meetingManagerMapper.queryMeetingManager(mm);
	}

	@Override
	public List<MeetingManager> querymeetingManagerList(MeetingManager mm) {
		logger.info( "----- 系统参数-根据指定条件查询会议管理 -----" );
		return meetingManagerMapper.querymeetingManagerList(mm);
	}

	@Override
	public Integer querymmTotal(MeetingManager mm) {
		logger.info( "----- 系统参数-查询会议总数 -----" );
		return meetingManagerMapper.querymmTotal(mm);
	}

}
