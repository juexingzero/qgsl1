package com.manhui.gsl.jbqgsl.dao.web.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.activitymanager.ActivitySignFlow;

@Mapper
public interface ActivitySignFlowMapper {

	Integer queryPersonNum(String activity_id);
	
	List<ActivitySignFlow> getActivitySignLists(Map<String, Object> conditions);
	
	Integer queryActivitySignTotal( Map<String, Object> conditions );	
	
}
