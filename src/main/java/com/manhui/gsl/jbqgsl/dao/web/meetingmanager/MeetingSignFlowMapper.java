package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;


@Mapper
public interface MeetingSignFlowMapper {
	
	//查询签到人数
	Integer querypersonNum(String meeting_id);
	
	List<MeetingSignFlow> getMeetingSignLists(Map<String, Object> conditions);
	
	Integer queryMeetingSignTotal( Map<String, Object> conditions );
	
	Integer queryCompanyTotal(MeetingSignFlow msf);
}
