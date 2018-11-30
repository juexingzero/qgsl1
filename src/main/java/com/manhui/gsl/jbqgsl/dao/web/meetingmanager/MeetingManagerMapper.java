package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.MeetingManager;

/**
* @Title: MeetingManagerMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.membermanager
* @Description: TODO(会议管理数据交互层)
* @author WangSheng
* @date 2018年10月173日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface MeetingManagerMapper {
	
	List<MeetingManager> getMeetingManagerList(Map<String, Object> conditions);
	
	Integer queryMeetingManagerTotal( Map<String, Object> conditions );
	
	Integer updateMeetingManager(MeetingManager mm);
	
	Integer insertMeetingManager(MeetingManager mm);
	
	MeetingManager queryMeetingManager(MeetingManager mm);
	
	List<MeetingManager> querymeetingManagerList(MeetingManager mm);
	
	Integer querymmTotal(MeetingManager mm);

}
