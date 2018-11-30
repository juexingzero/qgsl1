
package com.manhui.gsl.jbqgsl.dao.app.activitymanager;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityMemberName;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityResultList;
/**
* @Title: CommerceMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.commerce
* @Description: TODO(商会入会审批数据交互层)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppActivityManagerMapper {
	/**
	 * 根据membe_id 以及 activity_atate获取活动列表
	 */
	List<ActivityResultList> getActivityList(Map<String, Object> conditionMap);
	/**
	 * 活动附件
	 */
	String getActivityFile(@Param("activity_id")String activity_id);
	/**
	 * 根据活动ID,会员ID查看是否已经报名了
	 */
	Map<String, Object> queryEntryActivity(Map<String, Object> conditionMap);
	/**
	 * 报名活动
	 */
	int saveEntryActivity(Map<String, Object> conditionMap);
	/**
	 * 根据条件查找会员所述的公司名称--个人 团体 企业
	 */
	Map<String, Object> queryCompanyName(Map<String, Object> conditionMap);
	/**
	 * 活动签到 --按钮
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
	 * 根据member_id获取公司名
	 */
	Map<String, Object> queryActivityPerson(Map<String, Object> conditionMap);
	/**
	 * 根据会议ID获取活动内容
	 */
	Map<String, Object> getActivityContent(@Param("activity_id")String activity_id);
	

}
