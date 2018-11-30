package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingVoteOptions;

@Mapper
public interface MeetingVoteOptionsMapper {

	Integer insertMeetingVoteOptions(List<MeetingVoteOptions> mvoList);
	
	List<MeetingVoteOptions> queryMeetingVoteOptionsList(String vote_id);
	
	Integer updateMeetingVoteOptions(MeetingVoteOptions mv);
	
	Integer deleteMeetingVoteOptions(String vote_id);
	
}
