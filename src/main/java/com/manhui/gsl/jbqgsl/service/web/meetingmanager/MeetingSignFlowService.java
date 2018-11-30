package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;

public interface MeetingSignFlowService {

	Integer querypersonNum(String meeting_id);
	
	List<MeetingSignFlow> getMeetingSignLists(String meeting_id,Integer pageIndex, Integer pageSize);
	
	Integer queryMeetingSignTotal( String meeting_id,Integer pageIndex, Integer pageSize );
	
	Integer queryCompanyTotal(MeetingSignFlow msf);
}
