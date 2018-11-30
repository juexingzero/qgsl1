package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import com.manhui.gsl.jbqgsl.model.MeetingVote;

public interface MeetingVoteService {

	Integer insertMeetingVote(MeetingVote mv);
	
	MeetingVote queryMeetingVote(String meetingId);
	
	Integer updateMeetingVote(MeetingVote mv);
}
