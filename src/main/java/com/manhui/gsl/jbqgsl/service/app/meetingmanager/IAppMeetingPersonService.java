
package com.manhui.gsl.jbqgsl.service.app.meetingmanager;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(参会人员表service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppMeetingPersonService {
	/**
	 * 会议签到
	 */
	int meetingSign(Map<String, Object> conditionMap);
	/**
	 * 会议签到回显
	 */
	MeetingPerson queryMeetingSign(String meeting_id, String member_id);
	/**
	 * 查看签到详情
	 */
	MeetingSignFlow getMeetingPerson(Map<String, Object> conditionMap);
	/**
	 * 会议管理--保存投票结果
	 */
	int saveVoteResult(Map<String, Object> conditionMap);
	/**
	 * 二维码签到
	 */
	int meetingSignQr(Map<String, Object> conditionMap);
	/**
	 * 二维码签到--公司模糊查询
	 */
	List<MeetingPerson> queryGroupName(String group_name,String meeting_id);
	/**
	 * 查看是否已经投过票了
	 */
	String queryIsVote(Map<String, Object> conditionMap);
	/**
	 * 根据会议ID获取所有的签到手机账号
	 */
	List<String> getSignPhone(Map<String, Object> conditionMap);
	/**
	 * 根据user_id以及meeting_id查看是否已经签到
	 */
	Map<String, Object> querySign(String meeting_id, String user_id);


	


}
