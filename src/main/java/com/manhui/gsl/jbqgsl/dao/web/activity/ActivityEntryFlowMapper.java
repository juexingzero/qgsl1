package com.manhui.gsl.jbqgsl.dao.web.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityEntryFlow;

@Mapper
public interface ActivityEntryFlowMapper {

	List<ActivityEntryFlow> getActivityEntryFlowLists(Map<String, Object> conditions);
	
	Integer queryActivityEntryFlowTotal( Map<String, Object> conditions );	
	
	Integer queryActivityEntryFlowNum( Map<String, Object> conditions);
	
}
