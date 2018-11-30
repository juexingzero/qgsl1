
package com.manhui.gsl.jbqgsl.service.app.memberapproval.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberHistoryInfo;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.result.CommerceResult;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.dao.app.memberapproval.AppMemberApprovalMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberJoinManagerMapper;
import com.manhui.gsl.jbqgsl.dao.app.shldbz.AppLeadershipMapper;
import com.manhui.gsl.jbqgsl.dao.web.sysparam.SysParamMapper;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.service.app.memberapproval.IAppMemberApprovalService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl.AppMemberQyQyjbxxServiceImpl;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;

/**
 * @Title: AppCommerceServiceImpl.java
 * @Package com.manhui.gsl.jbqgsl.service.app.commerce.impl
 * @Description: TODO(APP端审批业务实现层)
 * @author LiuBin
 * @date 2018年11月1日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Service
public class AppMemberApprovalServiceImpl implements IAppMemberApprovalService {
	@Resource
	private AppMemberApprovalMapper appMemberApprovalMapper;
	@Resource
	private MemberJoinManagerMapper memberJoinManagerMapper;
	@Resource
	private SysParamMapper sysParamMapper;
	@Resource
	private AppMemberJgStjbxxService appMemberJgStjbxxService;
	@Resource
	private AppMemberQyQyjbxxService appMemberQyQyjbxxService;
	@Resource
    private MessageMapper       messageMapper;
	@Resource
	private AppLeadershipMapper appLeadershipMapper;

	/**
	 * 个人入会信息管理
	 */
	@Resource
	private AppMemberRyRyjbxxService appMemberRyRyjbxxService;

	/**
	 * 根据用户登录电话,获取是否是商会账户
	 */
	@Override
	public CommerceResult checkAccound(String user_phone) {
		CommerceResult checkAccound = appMemberApprovalMapper.checkAccound(user_phone);
		return checkAccound;
	}

	/**
	 * 根据商会id获取待审批的入会会员基本信息(会员名称,会员状态,入会时间,入会类型)
	 */
	@Override
	public List<MemberBaseInfo> getMemberList(String id) {
		MemberJoinManager joinManager = new MemberJoinManager();
		joinManager.setJoinObjId(id);
		// 新增状态
		List<String> paramIdList = new ArrayList<>();
		paramIdList.add(MemberEnum.mmberState.ADD.getCode());// 新增
		paramIdList.add(MemberEnum.mmberState.PENDING.getCode());// 待审批
		joinManager.setStateList(paramIdList);
		List<MemberBaseInfo> MemberBaseInfoList = memberJoinManagerMapper.queryMemberBaseInfo(joinManager);
		return MemberBaseInfoList;
	}

	/**
	 * 根据会员ID获取到--个人,企业,团体会员基本信息
	 */
	@Override
	public Map<String, Object> getMemberBaseInfoDetail(Map<String, Object> conditionMap) {
		String member_type = String.valueOf(conditionMap.get("member_type"));
		Map<String, Object> dataMap = new HashMap<>();
		// 个人会员
		if (member_type != null && MemberEnum.mmberType.INDIVIDUAL.getCode().equals(member_type)) {
			dataMap = appMemberRyRyjbxxService.queryIndividualMemberData(conditionMap.get("joinId").toString());
			// 团体 入会
		} else if (member_type != null && MemberEnum.mmberType.GROUP.getCode().equals(member_type)) {
			dataMap = appMemberJgStjbxxService.queryLeaguelMemberData(conditionMap.get("joinId").toString());
			// 企业会员
		} else if (member_type != null && MemberEnum.mmberType.ENTERPRISE.getCode().equals(member_type)) {
			dataMap = appMemberQyQyjbxxService.queryCompanyMemberData(conditionMap.get("joinId").toString());
		}
		return dataMap;
	}

	/**
	 * APP端--入会审批
	 */
	@Override
	public void updateApprovelMember(Map<String, Object> dataMap) {
		// 根据会员id获取到入的商会ID
		CommerceResult commerce = appMemberApprovalMapper.queryCommerce(dataMap.get("member_id").toString());
		MemberJoinManager mjm= memberJoinManagerMapper.getMemberInfoByMemberId(dataMap.get("member_id").toString());
		dataMap.put("approve_id", UUIDUtil.getUUID());// ID
		dataMap.put("approver_id", commerce.getID());// 审批人ID<就是商会ID>
		dataMap.put("approver_name", commerce.getSHMC());// 审批人姓名
		dataMap.put("create_time", DateUtil.getTime());// 创建时间
		dataMap.put("update_time", DateUtil.getTime());// 修改时间

		// 修改会员商会中间表
		int mjmNum = appMemberApprovalMapper.updateMemberjoinManager(dataMap);
		// 向审批记录表中添加审批记录--查看会员审批表中有几条记录(会员ID以及审批人ID)
		appMemberApprovalMapper.saveMemberApprove(dataMap);
		//审批通过,--向 member_jg_shldbz 表插入 人员id 以及申请 商会的职务 
		 if(MemberEnum.mmberState.APPROVED.getCode().equals(String.valueOf(dataMap.get("approve_status")))) {
			 Map<String,Object> conditionMap = new HashMap<>();
			 conditionMap.put("ID", UUIDUtil.getUUID());
			 conditionMap.put("SHZWLX", mjm.getPosition());
			 conditionMap.put("SHBMID", commerce.getID());
			 conditionMap.put("RYBM", String.valueOf(dataMap.get("member_id")));
			 appLeadershipMapper.saveLeaderShipInfo(dataMap);
		 }
		
		
		//消息通知
		String message_content = "";
	        if ( "HYSP-02".equals( dataMap.get("approve_status")+"" ) ) {
	            message_content = "您好：现将告知您的入会申请的审批结果为“" + "审批通过" + "”，请知悉！";
	        }
	        else if ( "HYSP-03".equals( dataMap.get("approve_status")+"" ) ) {
	            message_content = "您好：现将告知您的入会申请的审批结果为“" + "审批拒绝" + "”，具体原因为“"+dataMap.get("approve_fail_reason")+"”，请知悉！";
	        }
		  MessageInfo info = new MessageInfo();
	        info.setMessage_id( UUIDUtil.getUUID() );
	        info.setMessage_type( "1" );
	        info.setMessage_mode( "1" );
	        info.setMessage_content( message_content );
	        info.setCreator_id( dataMap.get( "approver_id" ) + "" );
	        info.setCreator_name( dataMap.get( "approver_name" ) + "" );
	        info.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
	        int flag = messageMapper.insertMessageInfo( info );
	        //发送消息
	        MessageFlowing flowing = new MessageFlowing();
	        flowing.setMessage_id( info.getMessage_id() );
	        flowing.setFlowing_id( UUIDUtil.getUUID() );
	        flowing.setSend_id( info.getCreator_id() );
	        flowing.setSend_name( info.getCreator_name() );
	        flowing.setSend_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
	        flowing.setReceive_id( mjm.getCreateUserId() );
	        flowing.setReceive_name( mjm.getCreateUserName() );
	        flowing.setIs_read( "0" );
	        flag = messageMapper.insertMessageFlowing( flowing );
	}

	/**
	 * 根据审批人id查询审批记录
	 */
	@Override
	public List<MemberHistoryInfo> queryApprovalHistory(Map<String, Object> dataMap) {
		return appMemberApprovalMapper.queryApprovalHistory(dataMap);
	}

	/**
	 * 根据会员名字以及审批人ID模糊查询被审批会员列表
	 */
	@Override
	public List<MemberHistoryInfo> queryMemberList(Map<String, Object> conditionMap) {
		return appMemberApprovalMapper.queryMemberList(conditionMap);
	}

}
