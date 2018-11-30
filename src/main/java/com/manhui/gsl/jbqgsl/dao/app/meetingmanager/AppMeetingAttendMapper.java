package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(会务活动数据交互层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingAttendMapper {

	Map<String, Object> queryData(Map<String, Object> conditionMap);

}
