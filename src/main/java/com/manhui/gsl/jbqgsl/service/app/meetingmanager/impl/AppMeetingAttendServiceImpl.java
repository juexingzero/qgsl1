package com.manhui.gsl.jbqgsl.service.app.meetingmanager.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingAttendMapper;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingAttendService;
/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(会务活动实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppMeetingAttendServiceImpl implements IAppMeetingAttendService {
	@Resource
	private AppMeetingAttendMapper appMeetingAttendMapper;
	

}
