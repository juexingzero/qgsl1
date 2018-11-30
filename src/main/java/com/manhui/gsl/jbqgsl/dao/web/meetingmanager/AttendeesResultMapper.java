package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.controller.web.meetingmanager.AttendeesResult;

/**
* @Title: AttendeesResultMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.membermanager
* @Description: TODO(会议管理数据交互层)
* @author WangSheng
* @date 2018年10月24日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AttendeesResultMapper {
	
	List<AttendeesResult> getAttendeesResultList(Map<String, Object> conditions);
	
	Integer queryAttendeesResultTotal( Map<String, Object> conditions );

}
