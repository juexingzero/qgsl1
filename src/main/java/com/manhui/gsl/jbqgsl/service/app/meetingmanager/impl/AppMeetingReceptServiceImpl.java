package com.manhui.gsl.jbqgsl.service.app.meetingmanager.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingAttendMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingManagerMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingReceptMapper;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingReceptService;
/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(会务活动--会议人员实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppMeetingReceptServiceImpl implements IAppMeetingReceptService {
	@Resource
	private AppMeetingReceptMapper appMeetingReceptMapper;
	@Resource
	private AppMeetingManagerMapper appMeetingManagerMapper;
	@Resource
	private AppMeetingAttendMapper appMeetingAttendMapper;
	/**
	 * 会议详情 --会议回执详情
	 */
	@Override
	public MeetingReceipt queryMeetingReceptDetail(Map<String, Object> conditionMap) {
		return appMeetingReceptMapper.queryMeetingReceptDetail(conditionMap);
	}
	@Override
	public int meetingSaveRecept(Map<String, Object> conditionMap) {
		//参会表中联系人以及公司名
		Map<String,Object> dataMap = appMeetingAttendMapper.queryData(conditionMap);
		//保存回执前先修改会议管理表中的回执状态为1:已回执
		if("1".equals(String.valueOf(conditionMap.get("participate_state")))) {
			conditionMap.put("is_leave","0"); //是否请假（0：否，1：是，默认：0）
		}else {
			conditionMap.put("is_leave","1"); //是否请假（0：否，1：是，默认：0）
			
		}
		conditionMap.put("company_name",dataMap.get("group_name")); //企业名称
		conditionMap.put("person",String.valueOf(dataMap.get("person_name"))+String.valueOf(dataMap.get("phone"))); //企业联系人与电话号码
		conditionMap.put("phone",dataMap.get("phone")); 
		conditionMap.put("receipt_id",UUIDUtil.getUUID()); //receipt_id
		conditionMap.put("create_time", DateUtil.getTime());
		conditionMap.put("update_time", DateUtil.getTime());
		int receptNum = appMeetingReceptMapper.saveMeetingRecept(conditionMap);
		return receptNum;
	}
	@Override
	public int meetingReceptLeave(Map<String, Object> conditionMap) {
		conditionMap.put("is_leave","1"); //是否请假（0：否，1：是，默认：0）
//		conditionMap.put("participate_state","0"); //是否参与（0：否，1：是，默认：0）
		conditionMap.put("update_time", DateUtil.getTime());
		int num = appMeetingReceptMapper.meetingReceptLeave(conditionMap);
		return num;
	}
	

}
