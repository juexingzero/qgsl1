package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;
import com.manhui.gsl.jbqgsl.model.MeetingManager;

public interface MeetingManagerService {

	List<MeetingManager> getMeetingManagerList(String meeting_theme,List<String> meeting_stateList,Integer pageIndex, Integer pageSize);
	
	Integer queryMeetingManagerTotal(String meeting_theme,List<String> meeting_stateList,Integer pageIndex, Integer pageSize);
	
	Integer updateMeetingManager(MeetingManager mm);
	
	Integer insertMeetingManager(MeetingManager mm);
	
	MeetingManager queryMeetingManager(MeetingManager mm);
	
	List<MeetingManager> querymeetingManagerList(MeetingManager mm);
	
	Integer querymmTotal(MeetingManager mm);
	
}
