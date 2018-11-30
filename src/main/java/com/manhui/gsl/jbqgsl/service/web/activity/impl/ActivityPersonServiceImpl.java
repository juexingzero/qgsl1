package com.manhui.gsl.jbqgsl.service.web.activity.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.web.activity.ActivityPersonMapper;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityPerson;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivityPersonService;

@Service
public class ActivityPersonServiceImpl implements ActivityPersonService {
	
	private static final Logger logger=LoggerFactory.getLogger(ActivityPersonServiceImpl.class);
	
	@Resource
	private ActivityPersonMapper activityPersonMapper;

	@Override
	public Integer insertActivityPerson(List<ActivityPerson> apList) {
		logger.info( "----- 系统参数-添加参与活动人员信息 -----" );
		return activityPersonMapper.insertActivityPerson(apList);
	}

	@Override
	public List<ActivityPerson> queryActivityPersonList(String activity_id) {
		logger.info( "----- 系统参数-根据活动id查询参与活动人员信息 -----" );
		return activityPersonMapper.queryActivityPersonList(activity_id);
	}

	@Override
	public Integer updateActivityPerson(ActivityPerson ap) {
		logger.info( "----- 系统参数-修改参与活动人员信息 -----" );
		return activityPersonMapper.updateActivityPerson(ap);
	}

	@Override
	public Integer deleteActivityPerson(ActivityPerson ap) {
		logger.info( "----- 系统参数-删除参与活动人员信息 -----" );
		return activityPersonMapper.deleteActivityPerson(ap);
	}

}
