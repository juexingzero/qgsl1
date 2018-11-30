package com.manhui.gsl.jbqgsl.dao.web.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityPerson;

@Mapper
public interface ActivityPersonMapper {
	//选择人员
	Integer insertActivityPerson(List<ActivityPerson> apList);
	
	List<ActivityPerson> queryActivityPersonList(String activity_id);
	
	Integer updateActivityPerson(ActivityPerson ap);
	
	Integer deleteActivityPerson(ActivityPerson ap);
	
}
