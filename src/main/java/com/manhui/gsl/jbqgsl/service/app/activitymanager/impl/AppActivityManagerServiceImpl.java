package com.manhui.gsl.jbqgsl.service.app.activitymanager.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityMemberName;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityResultList;
import com.manhui.gsl.jbqgsl.dao.app.activitymanager.AppActivityManagerMapper;
import com.manhui.gsl.jbqgsl.service.app.activitymanager.IAppActivityManagerService;
/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(活动管理--活动实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppActivityManagerServiceImpl implements IAppActivityManagerService {
	@Resource
	private AppActivityManagerMapper appActivityManagerMapper;
	/**
	 * 我的活动列表展示
	 */
	@Override
	public List<ActivityResultList> queryActivityList(Map<String, Object> conditionMap) {
		Map<String,Object> dataMap = appActivityManagerMapper.queryActivityPerson(conditionMap);
		if(dataMap ==null) {
			return null;
		}
		conditionMap.put("group_name",String.valueOf(dataMap.get("member_name")));
		return appActivityManagerMapper.getActivityList(conditionMap);
	}
	/**
	 * 活动附件
	 */
	@Override
	public String getActivityFile(String activity_id) {
		return appActivityManagerMapper.getActivityFile(activity_id);
	}
	/**
	 * 根据活动ID,会员ID查看是否已经报名了
	 */
	@Override
	public Map<String, Object> queryEntryActivity(Map<String, Object> conditionMap) {
		return appActivityManagerMapper.queryEntryActivity(conditionMap);
	}
	/**
	 * 报名活动
	 */
	@Override
	public int entryActivity(Map<String, Object> conditionMap) {
		Map<String,Object> dataMap = appActivityManagerMapper.queryCompanyName(conditionMap);
		conditionMap.put("entry_id", UUIDUtil.getUUID());
		conditionMap.put("member_name",String.valueOf(dataMap.get("company_name")));
		conditionMap.put("sign_time", DateUtil.getTime());
		conditionMap.put("create_time", DateUtil.getTime());
		conditionMap.put("update_time", DateUtil.getTime());
		
		return appActivityManagerMapper.saveEntryActivity(conditionMap);
	}
	/**
	 * 活动签到--按钮
	 */
	@Override
	public int saveActivitySign(Map<String, Object> conditionMap) {
		Map<String,Object> dataMap = appActivityManagerMapper.queryCompanyName(conditionMap);
		conditionMap.put("sign_id", UUIDUtil.getUUID());
		conditionMap.put("group_name",String.valueOf(dataMap.get("company_name")));
		conditionMap.put("sign_name",String.valueOf(dataMap.get("sign_name")));
		conditionMap.put("sign_title",String.valueOf(dataMap.get("company_title")));
		conditionMap.put("sign_phone",String.valueOf(dataMap.get("sign_phone")));
		conditionMap.put("sign_time",DateUtil.getTime());
		conditionMap.put("create_time",DateUtil.getTime());
		conditionMap.put("update_time",DateUtil.getTime());
		return appActivityManagerMapper.saveActivitySign(conditionMap);
	}
	/**
	 * 验证手机是否已经签到
	 */
	@Override
	public List<String> querySignPhone(Map<String, Object> conditionMap) {
		return appActivityManagerMapper.querySignPhone(conditionMap);
	}
	/**
	 * 二维码签到
	 */
	@Override
	public int saveActivitySignQr(Map<String, Object> conditionMap) {
		conditionMap.put("sign_id", UUIDUtil.getUUID());
		conditionMap.put("sign_time", DateUtil.getTime());
		conditionMap.put("create_time", DateUtil.getTime());
		conditionMap.put("update_time", DateUtil.getTime());
		return appActivityManagerMapper.saveActivitySignQr(conditionMap);
	}
	/**
	 * 根据activity_id获取活动详情
	 */
	@Override
	public Map<String, Object> queryActivityInfo(Map<String, Object> conditionMap) {
		return appActivityManagerMapper.queryActivityInfo(conditionMap);
	}
	/**
	 * 根据活动id模糊查询企业名称
	 */
	@Override
	public List<ActivityMemberName> queryMemberName(Map<String, Object> conditionMap) {
		return appActivityManagerMapper.queryMemberName(conditionMap);
	}
	/**
	 * 根据用户ID判断是否已经签到过了
	 */
	@Override
	public String querySignUserId(Map<String, Object> conditionMap) {
		return appActivityManagerMapper.querySignUserId(conditionMap);
	}
	/**
	 * 根据会议ID获取活动内容
	 */
	@Override
	public Map<String, Object> getActivityContent(String activity_id) {
		return appActivityManagerMapper.getActivityContent(activity_id);
	}
	
	

}
