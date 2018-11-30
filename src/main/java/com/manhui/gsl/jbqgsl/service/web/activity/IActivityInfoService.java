package com.manhui.gsl.jbqgsl.service.web.activity;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityInfo;

/**
 * @类名称 IActivityInfoService.java
 * @类描述 活动管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月17日 下午17:33:15
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月17日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface IActivityInfoService {
	List<ActivityInfo> getActivityInfoList(String activity_theme,List<String> activity_stateList,Integer pageIndex, Integer pageSize);
	
	Integer queryActivityInfoTotal(String activity_theme,List<String> activity_stateList,Integer pageIndex, Integer pageSize);
	
	Integer updateActivityInfo(ActivityInfo ai);
	
	Integer addActivityInfo(ActivityInfo ai);
	
	ActivityInfo queryActivityInfo(ActivityInfo ai);
	
	Integer queryActivityInfoNum(ActivityInfo ai);
}
