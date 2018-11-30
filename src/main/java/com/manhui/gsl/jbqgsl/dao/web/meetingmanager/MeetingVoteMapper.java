package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;


import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingVote;

@Mapper
public interface MeetingVoteMapper {

	Integer insertMeetingVote(MeetingVote mv);
	
	MeetingVote queryMeetingVote(String meetingId);
	
	Integer updateMeetingVote(MeetingVote mv);
	
}
