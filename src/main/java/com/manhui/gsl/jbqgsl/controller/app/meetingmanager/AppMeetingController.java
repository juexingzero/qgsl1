package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.util.StringUtil;
import com.manhui.gsl.jbqgsl.common.enums.MeetingRecept;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.ParamCheckUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.model.MeetingVote;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingAttendService;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingManagerService;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingPersonService;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingReceptService;
import com.manhui.gsl.jbqgsl.service.app.meetingmanager.IAppMeetingVoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppCompanyElegantController.java
 * @类描述 企业风采
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:23:21
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api(tags = "我的会议")
@Controller
@RequestMapping(AppMeetingController.ROOT_URL)
public class AppMeetingController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/meeting";
	@Resource
	private IAppMeetingManagerService meetingManagerService;
	@Resource
	private IAppMeetingReceptService meetingReceptService;
	@Resource
	private IAppMeetingAttendService meetingAttendService;
	@Resource
	private IAppMeetingPersonService meetingPersonService;
	@Resource   
	private IAppMeetingVoteService meetingVoteService;

	
	@ApiOperation(value = "我的会议", notes = "我的会议")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_state", value = "会议状态（0：未发布，1：未开始，2：会议中，3：已结束，4：已撤销，默认：0）", dataType = "字符串") })
	@RequestMapping(value = "getMeetingList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getMeetingList(@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "meeting_state", required = true) String meeting_state) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("member_id", user_id);
		conditionMap.put("meeting_state", meeting_state);
		List<MeetingListResult> meetingManagerList = meetingManagerService.getMeetingList(conditionMap);
		return new JsonResult(meetingManagerList);
	}

	@ApiOperation(value = "会议详情--会议内容", notes = "会议详情--会议内容")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串") })
	@RequestMapping(value = "meetingContent", method = RequestMethod.GET)
	public String meetingContent(HttpServletRequest request,
			@RequestParam(value = "meeting_id", required = true) String meeting_id) {
		MeetingContent meetingContent = meetingManagerService.getMeetingContent(meeting_id);
		request.setAttribute("meeting_id", meeting_id);
		request.setAttribute("requestUrl",String.valueOf( request.getRequestURL() ).split( "app" )[0]);
		request.setAttribute("meetingContent", meetingContent);
		return "/web/html/meetingManager/app/meetingContent";
	}
	
	@ApiOperation(value = "会议详情--会议纪要", notes = "会议详情--会议纪要")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串") })
	@RequestMapping(value = "meetingMinutes", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingMinutes(@RequestParam(value = "meeting_id", required = true) String meeting_id) {
		MeetingContent meetingMinutes = meetingManagerService.getMeetingMinutes(meeting_id);
		return new JsonResult(meetingMinutes);
	}

	@ApiOperation(value = "会议详情--会议资料", notes = "会议详情--会议资料")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串") })
	@RequestMapping(value = "meetingData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingData(@RequestParam(value = "meeting_id", required = true) String meeting_id) {
		MeetingData meetingData = meetingManagerService.getMeetingData(meeting_id);
		if(meetingData !=null && meetingData.getMeeting_data() !=null && !"".equals(meetingData.getMeeting_data())) {
			String[] data = meetingData.getMeeting_data().split(",");
			meetingData.setData(data);
		}
		
		return new JsonResult(meetingData);
	}

	@ApiOperation(value = "会议详情--会议回执详情--查看", notes = "会议详情--会议回执详情")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串") })
	@RequestMapping(value = "showMeetingReceptDetail", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult showMeetingReceptDetail(@RequestParam(value = "meeting_id", required = true) String meeting_id,
			@RequestParam(value = "user_id", required = true) String user_id) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
		MeetingReceipt meetingReceipt = meetingReceptService.queryMeetingReceptDetail(conditionMap);
		return new JsonResult(meetingReceipt);

	}

	@ApiOperation(value = "会议详情--会议回执--保存", notes = "会议详情--保存会议回执--保存")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "participate_state", value = "是否参与（0：否，1：是，默认：0）", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = false, name = "plate_number", value = "车牌号码", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = false, name = "leave_reason", value = "请假原因", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = false, name = "person_content", value = "参会人员信息 格式(name,title,phone-name,title,phone-name,title,phone)", dataType = "字符串") })
	@RequestMapping(value = "saveMeetingRecept", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveMeetingRecept(@RequestParam(value = "meeting_id", required = true) String meeting_id,
			@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "participate_state", required = true) String participate_state,
			@RequestParam(value = "plate_number", required = false) String plate_number,
			@RequestParam(value = "leave_reason", required = false) String leave_reason,
			@RequestParam(value = "person_content", required = false) String person_content) {
		Map<String,Object> dataMap = new HashMap<>();
		//获取会议回执截止时间
		String receipt_time = meetingManagerService.getMeetingManager(meeting_id);
		
		if(DateUtil.getTime().compareTo(receipt_time)>0) {
			dataMap.put("state","0");
			dataMap.put("message","已经超过会议回执截止时间");
			return new JsonResult(dataMap);
		}
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
		conditionMap.put("participate_state", participate_state);// 参加回执
		//请假理由
		conditionMap.put("leave_reason", leave_reason);
		// 车辆
		conditionMap.put("plate_number", plate_number);
		// 参加会议,则参会人员必须不能为空 0:不参加 1:参加
		if ("1".equals(participate_state)) {
			if (StringUtil.isEmpty(person_content)) {
				dataMap.put("state","0");
				dataMap.put("message","请填写参加会议的人员信息");
				return new JsonResult(dataMap);
			}
		}
		//不参加--原因必填
		if("0".equals(participate_state)) {
			if(leave_reason==null || "".equals(leave_reason)) {
				dataMap.put("state","0");
				dataMap.put("message","请假理由必填!!");
				return new JsonResult(dataMap);
			}
		}
		// 参会人员信息--计算参会人数
		conditionMap.put("person_content", person_content);
		String[] personArray = person_content.split("-");
		String person_num = "0";
		if (personArray.length >0  && personArray.length <= 3) {
			person_num = String.valueOf(personArray.length);
		}else {
			dataMap.put("state","0");
			dataMap.put("message","请填写至少一人,至多三人的参会人员");
			return new JsonResult(dataMap);
		}
		// 参会人数
		conditionMap.put("person_num", person_num);
		int num = meetingReceptService.meetingSaveRecept(conditionMap);
		if (num != 0) {
			dataMap.put("state","1");
			dataMap.put("message","回执成功");
			return new JsonResult(dataMap);
		} else {
			dataMap.put("state","0");
			dataMap.put("message","回执失败");
			return new JsonResult(dataMap);
		}
	}

	@ApiOperation(value = "会议详情--会议回执--请假", notes = "会议详情--会议回执详情--请假")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "leave_reason", value = "请假原因", dataType = "字符串") })
	@RequestMapping(value = "meetingReceptLeave", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingReceptLeave(@RequestParam(value = "meeting_id", required = true) String meeting_id,
			@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "leave_reason", required = true) String leave_reason) {
		//1回执参加   2会议未开始  才可以请假
		ManagerAndRecept managerRecept = meetingManagerService.getMeetingManagerAndRecept(meeting_id,user_id);
		Map<String,Object> dataMap = new HashMap<>();
		if(managerRecept==null) {
			dataMap.put("state","0");
			dataMap.put("message","您还没有参加回执!!不能请假");
			return new JsonResult(dataMap);
		}
		if(managerRecept !=null) {
			if(DateUtil.getTime().compareTo(managerRecept.getMeeting_starttime())>0) {
				dataMap.put("state","0");
				dataMap.put("message","会议已经开始,不能请假");
				return new JsonResult(dataMap);
			}
		}
		
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
		conditionMap.put("leave_reason", leave_reason);
		int num = meetingReceptService.meetingReceptLeave(conditionMap);
		if (num != 0) {
			dataMap.put("state","1");
			dataMap.put("message","请假成功");
			return new JsonResult(dataMap);
		} else {
			dataMap.put("state","0");
			dataMap.put("message","请假失败");
			return new JsonResult(dataMap);
		}

	}

	@ApiOperation(value = "会议详情--导航", notes = "会议详情--导航")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串") })
	@RequestMapping(value = "meetingAddress", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingAddress(@RequestParam(value = "meeting_id", required = true) String meeting_id) {
		MeetingAddress meetingAddress = meetingManagerService.getMeetingAddress(meeting_id);
		return new JsonResult(meetingAddress);

	}
	@ApiOperation(value = "会议详情--签到", notes = "会议详情--签到")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串")
	})
	@RequestMapping(value = "meetingSign", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingSign(
			@RequestParam(value = "meeting_id", required = true) String meeting_id, 
			@RequestParam(value = "user_id", required = true) String user_id){
		MeetingManager meetingManager= meetingManagerService.queryMeetingTime(meeting_id);
		Map<String,Object> conditionMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
			
		//判断签到时间
			if(meetingManager !=null) {
				Calendar cStart = Calendar.getInstance(); 
				try {
					cStart.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(meetingManager.getMeeting_starttime()));
					long startTime= cStart.getTimeInMillis();
					long nowTime = System.currentTimeMillis();
					long beforeTime = startTime - nowTime;
					long afterTime = startTime + nowTime;
					if(!((0<beforeTime && beforeTime<=30*60*1000) ||  (startTime<afterTime&&afterTime<=startTime+30*60*1000))) {//会议开始时间前30分钟或者开会后30分钟
						dataMap.put("state","0");
						dataMap.put("message","当前时间不能--签到时间在会议开始前半小时或开始后半小时内可签到");
						return new JsonResult(dataMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException("签到时间格式不正确");
				}
			}
	
		int num = meetingPersonService.meetingSign(conditionMap);
		if(num ==1) {
			dataMap.put("state", "1");
			dataMap.put("message", "签到成功");
			return new JsonResult(dataMap);
		}else {
			dataMap.put("state", "0");
			dataMap.put("message", "签到失败");
			return new JsonResult(dataMap);
		}
		
	}
	
	
	
	@ApiOperation(value = "会议详情--二维码签到", notes = "会议详情--二维码签到")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = false, name = "user_id", value = "用户ID", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "group_name", value = "企业名称", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "sign_name", value = "签到人员姓名", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "sign_phone", value = "签到人手机号码", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = false, name = "company_title", value = "企业职务", dataType = "字符串")
	})
	@RequestMapping(value = "meetingSignQr", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingSignQr(
			@RequestParam(value = "meeting_id", required = true) String meeting_id, 
			@RequestParam(value = "user_id", required = false) String user_id, 
			@RequestParam(value = "group_name", required = true) String group_name, 
			@RequestParam(value = "sign_name", required = true) String sign_name, 
			@RequestParam(value = "sign_phone", required = true) String sign_phone, 
			@RequestParam(value = "company_title", required = false) String company_title){
		Map<String,Object> conditionMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("company_name", group_name);
		conditionMap.put("company_title", company_title);
		conditionMap.put("sign_name", sign_name);
		if(sign_phone !=null && !"".equals(sign_phone)) {
			if(!ParamCheckUtil.isTelePhone(sign_phone)){//验证手机号码
				return new JsonResult(new RuntimeException("手机号码格式不正确"));
			}else {
				conditionMap.put("sign_phone", sign_phone);
			}
		}
		if(user_id !=null && !"".equals(user_id)) {
			conditionMap.put("member_id", user_id);
		}
		//验证该手机是否已经签到过了
		List<String> phoneList = meetingPersonService.getSignPhone(conditionMap);
		if(phoneList !=null && phoneList.size()>0) {
			if(phoneList.contains(sign_phone)) {
				dataMap.put("state","0");
				dataMap.put("message","该手机已经签到过了");
				return new JsonResult(dataMap);
			}
		}
		int num = meetingPersonService.meetingSignQr(conditionMap);
		if(num ==1) {
			dataMap.put("state","1");
			dataMap.put("message","签到成功");
			return new JsonResult(dataMap);
		}else if(num ==2){
			dataMap.put("state","0");
			dataMap.put("message","您已经签过到了");
			return new JsonResult(dataMap);
		}else {
			dataMap.put("state","0");
			dataMap.put("message","签到失败");
			return new JsonResult(dataMap);
		}
	}
	@ApiOperation(value = "会议详情--签到搜索公司", notes = "会议详情--签到搜索公司")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = false, name = "group_name", value = "公司名称", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议ID", dataType = "字符串")
	})
	@RequestMapping(value = "meetingSignQuery", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingSignQuery(
			@RequestParam(value = "group_name", required = false) String group_name,
			@RequestParam(value = "meeting_id", required = true) String meeting_id
			){
		List<MeetingPerson> meetingPersonList = meetingPersonService.queryGroupName(group_name,meeting_id);
		return new JsonResult(meetingPersonList);
		
	}
	
	@ApiOperation(value = "会议详情--签到详情", notes = "会议详情--签到详情")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串")
	})
	@RequestMapping(value = "meetingSignDetail", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingSignDetail(
			@RequestParam(value = "meeting_id", required = true) String meeting_id, 
			@RequestParam(value = "user_id", required = true) String user_id){
		Map<String,Object> conditionMap = new HashMap<>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
		MeetingSignFlow meetingPerson = meetingPersonService.getMeetingPerson(conditionMap);
		return new JsonResult(meetingPerson);
		
	}
	@ApiOperation(value = "会议详情--投票--回显", notes = "会议详情--投票--回显")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户id", dataType = "字符串")
	})
	@RequestMapping(value = "meetingVoteList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult meetingVoteList(
			@RequestParam(value = "meeting_id", required = true) String meeting_id, 
			@RequestParam(value = "user_id", required = true) String user_id){
		Map<String,Object> conditionMap = new HashMap<>();
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("member_id", user_id);
		MeetingVoteOptionResult meetingVoteList = meetingVoteService.getMeetingVoteList(conditionMap);
		return new JsonResult(meetingVoteList);
		
	}
	
	@ApiOperation(value = "会议详情--投票--保存", notes = "会议详情--投票--保存")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "meeting_id", value = "会议id", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "会员id", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "vote_options", value = "投票选项", dataType = "字符串")
	})
	@RequestMapping(value = "saveMeetingVote", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveMeetingVote(
			@RequestParam(value = "meeting_id", required = true) String meeting_id, 
			@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "vote_options", required = true) String vote_options){
		Map<String,Object> conditionMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		conditionMap.put("member_id", user_id);
		conditionMap.put("meeting_id", meeting_id);
		conditionMap.put("vote_options", vote_options);
		//验证会议时间 以及会议替票允许选择几条
		MeetingVoteObjectResult meetingVoteResult = meetingVoteService.queryMeetingVote(meeting_id);
		if(meetingVoteResult !=null && (meetingVoteResult.getAllow_item() !=null && !"".equals(meetingVoteResult.getAllow_item()))) {
			if(vote_options !=null && !"".equals(vote_options)) {
				String[] optionArray = vote_options.split(",");
				//验证选项
				if(optionArray.length>(Integer.valueOf(meetingVoteResult.getAllow_item()))){
					dataMap.put("state","0");
					dataMap.put("message","至少选择"+meetingVoteResult.getAllow_item()+":项");
					return new JsonResult(dataMap);
				}
				//验证投票时间
				long nowTime = System.currentTimeMillis();
				
				try {
					if(nowTime<DateUtil.dateToStamp(meetingVoteResult.getVote_starttime()) ||nowTime > DateUtil.dateToStamp(meetingVoteResult.getVote_starttime())) {
						dataMap.put("state","0");
						dataMap.put("message","至少选择"+meetingVoteResult.getAllow_item()+":项");
						return new JsonResult(dataMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
		}
		//验证是否已经投过票了
		String voteOption = meetingPersonService.queryIsVote(conditionMap);
		if(voteOption !=null) {
			dataMap.put("state","0");
			dataMap.put("message","您已经投过票了");
			return new JsonResult(dataMap);
		}
		int num = meetingPersonService.saveVoteResult(conditionMap);
		if(num !=0) {
			dataMap.put("state","1");
			dataMap.put("message","过票成功");
			return new JsonResult(dataMap);
		}else {
			dataMap.put("state","0");
			dataMap.put("message","投票失败");
			return new JsonResult(dataMap);
		}
		
	}

}
