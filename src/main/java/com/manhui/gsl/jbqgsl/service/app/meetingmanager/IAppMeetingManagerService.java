
package com.manhui.gsl.jbqgsl.service.app.meetingmanager;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.ManagerAndRecept;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingAddress;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingContent;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingData;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingListResult;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(会务活动service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppMeetingManagerService {

	List<MeetingListResult> getMeetingList(Map<String, Object> conditionMap);

	MeetingContent getMeetingContent(String meeting_id);

	MeetingData getMeetingData(String meeting_id);

	MeetingAddress getMeetingAddress(String meeting_id);
	/**
	 * 签到 --会议开始时间--会议结束时间
	 */
	MeetingManager queryMeetingTime(String meeting_id);
	/**
	 * 会议详情 --会议纪要
	 */
	MeetingContent getMeetingMinutes(String meeting_id);
	/**
	 * 

	 */
	String getMeetingManager(String meeting_id);
	/**
	 * 获取会议开始时间以及回执状态
	 */
	ManagerAndRecept getMeetingManagerAndRecept(String meeting_id, String member_id);


}
