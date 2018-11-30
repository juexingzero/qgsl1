
package com.manhui.gsl.jbqgsl.service.app.meetingmanager;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteObjectResult;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingVoteOptionResult;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(参会人员表投票service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppMeetingVoteService {

	/**
	 * 会议管理--会议投票回显(列表展示以及包括所选所选)
	 */
	MeetingVoteOptionResult getMeetingVoteList(Map<String, Object> conditionMap);
	/**
	 * 会议管理--会议投票表详情
	 */
	MeetingVoteObjectResult queryMeetingVote(String meeting_id);



}
