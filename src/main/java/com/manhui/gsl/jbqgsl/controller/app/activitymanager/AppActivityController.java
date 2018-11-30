package com.manhui.gsl.jbqgsl.controller.app.activitymanager;

import java.text.ParseException;
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

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.ParamCheckUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityMemberName;
import com.manhui.gsl.jbqgsl.controller.app.activitymanager.result.ActivityResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.manhui.gsl.jbqgsl.service.app.activitymanager.IAppActivityManagerService;;

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
@Api(tags = "我的活动")
@Controller
@RequestMapping(AppActivityController.ROOT_URL)
public class AppActivityController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/activity";
	@Resource
	private IAppActivityManagerService activityManagerService;
	
	@ApiOperation(value = "我的活动", notes = "我的活动")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "activity_state", value = "活动状态（0：未发布，1：未开始，2：进行中，3：已结束，4：已撤销，5:已参加 默认：0 ）", dataType = "字符串") })
	@RequestMapping(value = "getActivityList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getActivityList(@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "activity_state", required = true) String activity_state) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("member_id", user_id);
		conditionMap.put("activity_state", activity_state);
		List<ActivityResultList> activityResultList = activityManagerService.queryActivityList(conditionMap);
		return new JsonResult(activityResultList);
	}
	
	@ApiOperation(value = "活动附件", notes = "活动附件")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串") })
	@RequestMapping(value = "getActivityFile", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult activityFile(@RequestParam(value = "activity_id", required = true)String activity_id){		
		String activity_file = activityManagerService.getActivityFile(activity_id);
		return new JsonResult(activity_file);
	}
	@ApiOperation(value = "活动内容", notes = "活动内容")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串") })
	@RequestMapping(value = "activityContent", method = RequestMethod.GET)
	public String activityContent(HttpServletRequest request,@RequestParam(value = "activity_id", required = true)String activity_id){		
		Map<String,Object> dataMap = activityManagerService.getActivityContent(activity_id);
		request.setAttribute("requestUrl",String.valueOf( request.getRequestURL() ).split( "app" )[0]);
		request.setAttribute("dataMap", dataMap);
		return "/web/html/activity/app/activityContent";
	}

	@ApiOperation(value = "活动报名", notes = "活动报名")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = true, name = "member_linkman_name", value = "联络人姓名", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = true, name = "member_linkman_sex", value = "联络人性别男:XB-01 女:XB-02", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = false, name = "member_linkman_title", value = "联络人职务", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = true, name = "member_linkman_phone", value = "联络人手机号码", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = true, name = "entry_num", value = "报名人数", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = false, name = "remarks", value = "备注", dataType = "字符串") ,
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串") })
	@RequestMapping(value = "activityEntry", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult activityEntry(
			@RequestParam(value = "activity_id", required = true) String activity_id,
			@RequestParam(value = "member_linkman_name", required = true) String member_linkman_name,
			@RequestParam(value = "member_linkman_sex", required = true) String member_linkman_sex,
			@RequestParam(value = "member_linkman_title", required = false) String member_linkman_title,
			@RequestParam(value = "member_linkman_phone", required = true) String member_linkman_phone,
			@RequestParam(value = "entry_num", required = true) String entry_num,
			@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "remarks", required = false) String remarks){
		Map<String,Object> conditionMap = new HashMap<>();
		Map<String,Object> resultMap = new HashMap<>();
		conditionMap.put("activity_id", activity_id);
		conditionMap.put("member_linkman_name", member_linkman_name);
		conditionMap.put("member_linkman_sex", member_linkman_sex);
		conditionMap.put("member_linkman_title", member_linkman_title);
		conditionMap.put("member_linkman_phone", member_linkman_phone);
		conditionMap.put("entry_num", entry_num);
		conditionMap.put("member_id", user_id);
		conditionMap.put("remarks", remarks);
		//先判断是否已经报名了
		Map<String,Object> dataMap = activityManagerService.queryEntryActivity(conditionMap);
		if(dataMap !=null && String.valueOf(dataMap.get("entry_id"))!=null && !"".equals(String.valueOf(dataMap.get("entry_id")))) {
			resultMap.put("state","0");
			resultMap.put("message","您已经报过名了..不能重复报名!!");
			return new JsonResult(resultMap);
		}
		int num = activityManagerService.entryActivity(conditionMap);
		if(num==1) {
			resultMap.put("state","1");
			resultMap.put("message","报名成功");
			return new JsonResult(resultMap);
		}else {
			resultMap.put("state","0");
			resultMap.put("message","报名失败");
			return new JsonResult(resultMap);
		}
	}
	
	@ApiOperation(value = "活动签到--按钮", notes = "活动签到--按钮")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串"), 
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串")
		})
	@RequestMapping(value = "activitySign", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult activitySign(@RequestParam(value = "activity_id", required = true)String activity_id,
			@RequestParam(value = "user_id", required = true)String user_id){	
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		conditionMap.put("activity_id", activity_id);
		conditionMap.put("member_id", user_id);
		//获取活动开始时间-结束时间
		Map<String,Object> dataMap1 = activityManagerService.queryActivityInfo(conditionMap);
		if(dataMap1 !=null) {
			String activity_start_time = String.valueOf(dataMap1.get("activity_start_time"));
			try {
				long start = DateUtil.dateToStamp2(activity_start_time);
				long now = System.currentTimeMillis();
				if(!(start - now > 30*60*1000 || now - start>30*60*1000)) {//活动前半个小时 活动开始后半个小时可以签到
					dataMap.put("state","0");
					dataMap.put("message","活动开始前0.5小时活活动开始后0.5小时签到");
					return new JsonResult();
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return new JsonResult("时间格式不正确");
			}
		}
		int num = activityManagerService.saveActivitySign(conditionMap);
		
		if(num == 1) {
			dataMap.put("state","1");
			dataMap.put("message","签到成功!");
			return new JsonResult(dataMap);
		}else {
			dataMap.put("state","0");
			dataMap.put("message","签到失败!");
			return new JsonResult(dataMap);
		}
	}
	
	@ApiOperation(value = "签到搜索--公司", notes = "签到搜索--公司")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = false, name = "member_name", value = "公司名称", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串")
		})
	@RequestMapping(value = "getMemberName", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getMemberName(
			@RequestParam(value = "member_name", required = false)String member_name,
			@RequestParam(value = "activity_id", required = true)String activity_id
			){	
		Map<String,Object> conditionMap = new HashMap<>();
		conditionMap.put("member_name", member_name);
		conditionMap.put("activity_id", activity_id);
		List<ActivityMemberName> activityMemberName = activityManagerService.queryMemberName(conditionMap);
		return new JsonResult(activityMemberName);
	}
	
	
	
	@ApiOperation(value = "活动签到--扫码", notes = "活动签到--扫码")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "group_name", value = "公司名称", dataType = "字符串"), 
		@ApiImplicitParam(paramType = "query", required = true, name = "activity_id", value = "活动ID", dataType = "字符串"), 
		@ApiImplicitParam(paramType = "query", required = false, name = "user_id", value = "用户ID", dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", required = true, name = "sign_name", value = "姓名", dataType = "字符串"), 
		@ApiImplicitParam(paramType = "query", required = false, name = "sign_title", value = "职务", dataType = "字符串"), 
		@ApiImplicitParam(paramType = "query", required = true, name = "sign_phone", value = "手机号码", dataType = "字符串") 
	})
	@RequestMapping(value = "activitySignQr", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult activitySignQr(
			@RequestParam(value = "group_name", required = true)String group_name,
			@RequestParam(value = "activity_id", required = true)String activity_id,
			@RequestParam(value = "user_id", required = false)String user_id,
			@RequestParam(value = "sign_name", required = true)String sign_name,
			@RequestParam(value = "sign_title", required = false)String sign_title,
			@RequestParam(value = "sign_phone", required = true)String sign_phone){	
		
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		conditionMap.put("group_name", group_name);
		conditionMap.put("activity_id", activity_id);
		conditionMap.put("user_id", user_id);
		conditionMap.put("sign_name", sign_name);
		conditionMap.put("sign_title", sign_title);
		conditionMap.put("sign_phone", sign_phone);
		//验证手机号码
		if(!ParamCheckUtil.isTelePhone(sign_phone)){//验证手机号码
			dataMap.put("state","0");
			dataMap.put("message","请输入正确的手机格式");
			return new JsonResult(dataMap);
		}
		//验证手机是否已经签到过了
		List<String> phoneList = activityManagerService.querySignPhone(conditionMap);
		if(phoneList !=null && !phoneList.isEmpty()) {
			if(phoneList.contains(sign_phone)) {
				dataMap.put("state","0");
				dataMap.put("message","该手机已经签到过了");
				return new JsonResult(dataMap);
			}
		}
		//user_id不为空时,验证是否已经签到过了
		String activitySignId = activityManagerService.querySignUserId(conditionMap);
		if(activitySignId !=null) {
			dataMap.put("state","0");
			dataMap.put("message","你已经签过到了!");
			return new JsonResult(dataMap);
		}
		int num = activityManagerService.saveActivitySignQr(conditionMap);
		
		if(num == 1) {
			dataMap.put("state","1");
			dataMap.put("message","签到成功!");
			return new JsonResult(dataMap);
		}else {
			dataMap.put("state","0");
			dataMap.put("message","签到失败!");
			return new JsonResult(dataMap);
		}
	}
	
}
