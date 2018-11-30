package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;


/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(参会人员数据交互层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingPersonMapper {
	/**
	 * 签到
	 */
	int updateSign(Map<String, Object> conditionMap);
	/**
	 * 根据会议ID,会员ID查看签到详情
	 */
	MeetingPerson queryMeetingSign(@Param("meeting_id")String meeting_id,@Param("member_id") String member_id);
	/**
	 * 签到详情
	 */
	MeetingSignFlow getMeetingPerson(Map<String, Object> conditionMap);
	/**
	 * 会议管理--会议投票-结果保存
	 */
	int saveVoteResult(Map<String, Object> conditionMap);
	/**
	 * 二维码签到保存--有会员id的扫二维码
	 */
	int updateMeetingSignQr(Map<String, Object> conditionMap);
	/**
	 * 二维码签到保存--没有会员id的扫二维码
	 */
	int saveMeetingSignQr(Map<String, Object> conditionMap);
	/**
	 * 二维码签到--公司模糊查询
	 */
	List<MeetingPerson> queryGroupName(@Param("group_name")String group_name,@Param("meeting_id")String meeting_id);
	/**
	 * 获取参会公司名称
	 */
	MeetingPerson queryMeetingGroupName(Map<String, Object> conditionMap);
	/**
	 * 查看是否已经投过票了
	 */
	String queryIsVote(Map<String, Object> conditionMap);
	


}
