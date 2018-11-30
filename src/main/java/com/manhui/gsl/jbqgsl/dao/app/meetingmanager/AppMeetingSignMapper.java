package com.manhui.gsl.jbqgsl.dao.app.meetingmanager;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.MeetingPerson;


/**
* @Title: AppMeetingMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.meeting
* @Description: TODO(会务签到)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppMeetingSignMapper {
	/**
	 * 按钮签到--保存
	 */
	int saveSign(Map<String, Object> conditionMap);
	/**
	 * 根据会议ID获取所有的签到手机账号
	 */
	List<String> getSignPhone(Map<String, Object> conditionMap);
	/**
	 * 根据user_id获取到个人 企业 团体 会员的公司名称
	 */
	Map<String, Object> queryMemberPerson(@Param("member_id")String member_id);
	/**
	 * 根据user_id以及meeting_id查看是否已经签到
	 */
	Map<String, Object> querySign(@Param("member_id")String meeting_id, @Param("user_id")String user_id);


}
