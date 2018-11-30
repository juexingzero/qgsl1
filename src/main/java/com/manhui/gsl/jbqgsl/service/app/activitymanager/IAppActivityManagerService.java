
package com.manhui.gsl.jbqgsl.service.app.activitymanager;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityMemberName;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityResultList;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(活动管理service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppActivityManagerService {
	/**
	 * 根据user_id以及状态查找我的活动
	 */
	List<ActivityResultList> queryActivityList(Map<String, Object> conditionMap);
	/**
	 * 通过活动ID获取活动附件
	 */
	String getActivityFile(String activity_id);
	/**
	 * 根据活动ID,会员ID查看是否已经报名了
	 */
	Map<String, Object> queryEntryActivity(Map<String, Object> conditionMap);
	/**
	 * 报名活动
	 */
	int entryActivity(Map<String, Object> conditionMap);
	/**
	 * 活动签到--按钮
	 */
	int saveActivitySign(Map<String, Object> conditionMap);
	/**
	 * 验证手机是否已经签到
	 */
	List<String> querySignPhone(Map<String, Object> conditionMap);
	/**
	 * 二维码签到
	 */
	int saveActivitySignQr(Map<String, Object> conditionMap);
	/**
	 * 根据activity_id获取活动详情
	 */
	Map<String, Object> queryActivityInfo(Map<String, Object> conditionMap);
	/**
	 * 根据活动id模糊查询企业名称
	 */
	List<ActivityMemberName> queryMemberName(Map<String, Object> conditionMap);
	/**
	 * 根据用户ID判断是否已经签到过了
	 */
	String querySignUserId(Map<String, Object> conditionMap);
	/**
	 * 根据会议ID获取活动内容
	 */
	Map<String, Object> getActivityContent(String activity_id);




}
