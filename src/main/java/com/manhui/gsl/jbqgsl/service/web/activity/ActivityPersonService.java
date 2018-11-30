package com.manhui.gsl.jbqgsl.service.web.activity;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityPerson;

public interface ActivityPersonService {

	Integer insertActivityPerson(List<ActivityPerson> apList);
	
	List<ActivityPerson> queryActivityPersonList(String activity_id);
	
	Integer updateActivityPerson(ActivityPerson ap);
	
	Integer deleteActivityPerson(ActivityPerson ap);
	
}
