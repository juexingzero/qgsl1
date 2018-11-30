package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.controller.web.meetingmanager.AttendeesResult;
import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.AttendeesResultMapper;
import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingManagerMapper;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.AttendeesResultService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingManagerService;

@Service
public class AttendeesResultServiceImpl implements AttendeesResultService {
	
	private static final Logger logger=LoggerFactory.getLogger(AttendeesResultServiceImpl.class);
	
	@Resource
	private AttendeesResultMapper attendeesResultMapper;

	@Override
	public List<AttendeesResult> getAttendeesResultList(String company_name, String concats_title,
			String approve_organization_name, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会人员选项列表数据  -----" );
		 Map<String, Object> conditions = new HashMap<>();
	        conditions.put( "company_name", company_name );
	        conditions.put( "concats_title", concats_title );
	        conditions.put( "approve_organization_name", approve_organization_name );
	        conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		return attendeesResultMapper.getAttendeesResultList(conditions);
	}

	@Override
	public Integer queryAttendeesResultTotal(String company_name, String concats_title,
			String approve_organization_name, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会人员选项总数  -----" );
		 Map<String, Object> conditions = new HashMap<>();
		 conditions.put( "company_name", company_name );
	        conditions.put( "concats_title", concats_title );
	        conditions.put( "approve_organization_name", approve_organization_name );
	        conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		return attendeesResultMapper.queryAttendeesResultTotal(conditions);
	}

}
