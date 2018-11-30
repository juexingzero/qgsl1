package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingPerson;

@Mapper
public interface MeetingPersonMapper {
	//选择人员
	Integer insertMeetingPerson(List<MeetingPerson> mpList);
	
	List<MeetingPerson> queryMeetingPersonList(String meeting_id);
	
	Integer updateMeetingPerson(MeetingPerson mp);
	
	Integer deleteMeetingPerson(MeetingPerson mp);
	
	
	//选择签到人员
	Integer queryMeetingPersonNum(String meeting_id);
	
	List<MeetingPerson> getMeetingPersonLists(Map<String, Object> conditions);
	
	Integer queryMeetingPersonTotal( Map<String, Object> conditions );
	
	//查询投票人数
	Integer querypersonNum(String vote_options);
	
	List<MeetingPerson> queryvotePerson(Map<String, Object> conditions);
	
	Integer queryvotePersonNum(Map<String, Object> conditions);
	
}
