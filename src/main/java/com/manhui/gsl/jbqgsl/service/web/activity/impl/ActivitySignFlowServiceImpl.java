package com.manhui.gsl.jbqgsl.service.web.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.activity.ActivitySignFlowMapper;
import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivitySignFlow;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivitySignFlowService;

@Service
public class ActivitySignFlowServiceImpl implements ActivitySignFlowService {
	
	private static final Logger logger=LoggerFactory.getLogger(ActivitySignFlowServiceImpl.class);
	
	@Resource
	private ActivitySignFlowMapper activitySignFlowMapper;

	@Override
	public Integer queryPersonNum(String activity_id) {
		logger.info("----- 系统参数-获取活动签到人员数量  -----");
		return activitySignFlowMapper.queryPersonNum(activity_id);
	}

	@Override
	public List<ActivitySignFlow> getActivitySignLists(String activity_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询活动签到人员列表 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("activity_id", activity_id);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return activitySignFlowMapper.getActivitySignLists(conditions);
	}

	@Override
	public Integer queryActivitySignTotal(String activity_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询活动签到人员列表总数 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("activity_id", activity_id);
		if(pageIndex!=null&&pageSize!=null){
			conditions.put( "pageNo", pageIndex * pageSize);	
		}
        conditions.put( "pageSize", pageSize);
		return activitySignFlowMapper.queryActivitySignTotal(conditions);
	}

}
