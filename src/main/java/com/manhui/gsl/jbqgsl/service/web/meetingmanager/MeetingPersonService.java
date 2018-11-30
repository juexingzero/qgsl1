package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.MeetingPerson;

public interface MeetingPersonService {

	Integer insertMeetingPerson(List<MeetingPerson> mpList);
	
	List<MeetingPerson> queryMeetingPersonList(String meeting_id);
	
	Integer updateMeetingPerson(MeetingPerson mp);
	
	Integer deleteMeetingPerson(MeetingPerson mp);
	
	Integer queryMeetingPersonNum(String meeting_id);
	
	List<MeetingPerson> getMeetingPersonLists(String meeting_id,Integer pageIndex, Integer pageSize);
	
	Integer queryMeetingPersonTotal( String meeting_id,Integer pageIndex, Integer pageSize );
	
	Integer querypersonNum(String vote_options);
	
	List<MeetingPerson> queryvotePerson(String meeting_id,String vote_options,Integer pageIndex, Integer pageSize);
	
	Integer queryvotePersonNum(String meeting_id,String vote_options,Integer pageIndex, Integer pageSize);
}
