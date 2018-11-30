package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.MeetingVoteOptions;

public interface MeetingVoteOptionsService {

	Integer insertMeetingVoteOptions(List<MeetingVoteOptions> mvoList);
	
	List<MeetingVoteOptions> queryMeetingVoteOptionsList(String vote_id);
	
	Integer updateMeetingVoteOptions(MeetingVoteOptions mv);
	
	Integer deleteMeetingVoteOptions(String vote_id);
}
