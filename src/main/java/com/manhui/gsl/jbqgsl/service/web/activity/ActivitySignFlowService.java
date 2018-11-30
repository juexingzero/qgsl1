package com.manhui.gsl.jbqgsl.service.web.activity;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.activitymanager.ActivitySignFlow;

public interface ActivitySignFlowService {
	
	Integer queryPersonNum(String activity_id);
	
	List<ActivitySignFlow> getActivitySignLists(String activity_id,Integer pageIndex, Integer pageSize);
	
	Integer queryActivitySignTotal( String activity_id,Integer pageIndex, Integer pageSize );

}
