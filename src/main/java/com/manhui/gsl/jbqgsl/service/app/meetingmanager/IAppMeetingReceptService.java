
package com.manhui.gsl.jbqgsl.service.app.meetingmanager;

import java.util.Map;

import com.manhui.gsl.jbqgsl.model.MeetingReceipt;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(会务活动--会务回执service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppMeetingReceptService {
	/**
	 * 查看会议回执详情--详情
	 */
	MeetingReceipt queryMeetingReceptDetail(Map<String, Object> conditionMap);
	/**
	 * 会议回执--回执--保存
	 */
	int meetingSaveRecept(Map<String, Object> conditionMap);
	/**
	 * 会议回执--回执--请假
	 */
	int meetingReceptLeave(Map<String, Object> conditionMap);


}
