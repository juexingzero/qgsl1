package com.manhui.gsl.jbqgsl.service.app.meetingmanager.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.meetingmanager.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingPersonMapper;
import com.manhui.gsl.jbqgsl.dao.app.meetingmanager.AppMeetingSignMapper;
import com.manhui.gsl.jbqgsl.dao.app.memberapproval.AppMemberApprovalMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingPersonService;

/**
* @Title: AppMeetingServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager.impl
* @Description: TODO(参会人员实现层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppMeetingPersonServiceImpl implements IAppMeetingPersonService {
	@Resource
	private AppMeetingPersonMapper appMeetingPersonMapper;
	@Resource
	private AppMemberApprovalMapper appMemberApprovalMapper;
	@Resource
	private AppMeetingSignMapper appMeetingSignMapper;
	@Resource
	private AppUserMapper appUserMapper;
	/**
	 * 签到
	 */
	@Override
	public int meetingSign(Map<String, Object> conditionMap) {
		String member_id = String.valueOf(conditionMap.get("member_id"));
		Map<String,Object> dataMap = appMeetingSignMapper.queryMemberPerson(member_id);
		 AppUser queryAppUser = appUserMapper.queryAppUser(String.valueOf(dataMap.get("user_id")));
		conditionMap.put("sign_id", UUIDUtil.getUUID());
		conditionMap.put("sign_name", String.valueOf(dataMap.get("sign_name")));
		conditionMap.put("company_name", String.valueOf(dataMap.get("company_name")));
		conditionMap.put("company_title", String.valueOf(dataMap.get("company_title")));
		conditionMap.put("sign_phone", queryAppUser.getUser_phone());
		conditionMap.put("sign_time", DateUtil.getTime());
			
		return appMeetingSignMapper.saveSign(conditionMap);
	}
	/**
	 * 根据会议ID,会员ID查看签到详情
	 */
	@Override
	public MeetingPerson queryMeetingSign(String meeting_id, String member_id) {
		return appMeetingPersonMapper.queryMeetingSign(meeting_id,member_id);
	}
	/**
	 * 查看签到详情
	 */
	@Override
	public MeetingSignFlow getMeetingPerson(Map<String, Object> conditionMap) {
		return appMeetingPersonMapper.getMeetingPerson(conditionMap);
	}
	/**
	 * 会议管理--会议投票-结果保存
	 */
	@Override
	public int saveVoteResult(Map<String, Object> conditionMap) {
		return appMeetingPersonMapper.saveVoteResult(conditionMap);
	}
	/**
	 * 二维码签到
	 */
	@Override
	public int meetingSignQr(Map<String, Object> conditionMap) {
		int num = 0;
		String member_id = String.valueOf(conditionMap.get("member_id"));
		if(member_id !=null || !"".equals(member_id)) {//有会员ID账号的使用二维码签到
			//验证该会员是否已经签过到了
			MeetingSignFlow queryMeetingSign = appMeetingPersonMapper.getMeetingPerson(conditionMap);
			if(queryMeetingSign !=null && queryMeetingSign.getSing_time() !=null && StringUtil.isNotEmpty(queryMeetingSign.getSing_time())) {
				num=2;
				
			}else {//有会员ID但是还没有签到
				conditionMap.put("sign_id", UUIDUtil.getUUID());
				conditionMap.put("sing_time", DateUtil.getTime());
				num = appMeetingSignMapper.saveSign(conditionMap);
			}
		}else {//没有会员id账号的
			num = appMeetingSignMapper.saveSign(conditionMap);
		}
		return num;
	}
	/**
	 * 二维码签到--公司模糊查询
	 */
	@Override
	public List<MeetingPerson> queryGroupName(String group_name,String meeting_id) {
		return appMeetingPersonMapper.queryGroupName(group_name,meeting_id);
	}
	/**
	 * 查看是否已经投过票了
	 */
	@Override
	public String queryIsVote(Map<String, Object> conditionMap) {
		return appMeetingPersonMapper.queryIsVote(conditionMap);
	}
	/**
	 * 根据会议ID获取所有的签到手机账号
	 */
	@Override
	public List<String> getSignPhone(Map<String, Object> conditionMap) {
		return appMeetingSignMapper.getSignPhone(conditionMap);
	}
	/**
	 * 根据user_id以及meeting_id查看是否已经签到
	 */
	@Override
	public Map<String, Object> querySign(String meeting_id, String user_id) {
		return appMeetingSignMapper.querySign(meeting_id,user_id);
	}

	

}
