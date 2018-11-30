package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingSignFlowMapper;
import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingSignFlowService;

@Service
public class MeetingSignFlowServiceImpl implements MeetingSignFlowService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingSignFlowServiceImpl.class);
	
	@Resource
	private MeetingSignFlowMapper meetingSignFlowMapper;

	@Override
	public Integer querypersonNum(String meeting_id) {
		logger.info( "----- 系统参数-查询签到人员数量 -----" );
		return meetingSignFlowMapper.querypersonNum(meeting_id);
	}

	@Override
	public List<MeetingSignFlow> getMeetingSignLists(String meeting_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询签到人员列表 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return meetingSignFlowMapper.getMeetingSignLists(conditions);
	}

	@Override
	public Integer queryMeetingSignTotal(String meeting_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询签到人员列表总数 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		if(pageIndex!=null&&pageSize!=null){
			conditions.put( "pageNo", pageIndex * pageSize);	
		}
        conditions.put( "pageSize", pageSize);
		return meetingSignFlowMapper.queryMeetingSignTotal(conditions);
	}

	@Override
	public Integer queryCompanyTotal(MeetingSignFlow msf) {
		logger.info( "----- 系统参数-查询签到企业总数 -----" );
		return meetingSignFlowMapper.queryCompanyTotal(msf);
	}


}
