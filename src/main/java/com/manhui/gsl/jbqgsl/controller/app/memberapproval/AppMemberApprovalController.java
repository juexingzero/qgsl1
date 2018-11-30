package com.manhui.gsl.jbqgsl.controller.app.memberapproval;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.result.CommerceResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import com.manhui.gsl.jbqgsl.service.app.memberapproval.IAppMemberApprovalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppMemberApprovalController.java
 * @类描述 APP端审批
 * @作者 LiuBIn kwmo1005@163.com
 * @创建时间 2018年11月5日 上午9:23:21
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
@Api( tags = "我的-app端审批" )
@Controller
@RequestMapping( AppMemberApprovalController.ROOT_URL )
public class AppMemberApprovalController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/memberApproval";
    @Resource
	private IAppMemberApprovalService memberApprovalService;
    
    @ApiOperation(value = "检查账号是否具有审批权限--依据账号电话号码", notes = "检查账号是否具有审批权限--依据账号电话号码")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "user_phone", value = "商会班子手机号", required = true, dataType = "字符串")
    })
    @RequestMapping(value = "checkAccount", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkAccount(
    		@RequestParam(value = "user_phone", required = true) String user_phone) {
    	//商会信息实体类
    	CommerceResult commercr = memberApprovalService.checkAccound(user_phone);
    	return new JsonResult(commercr);
    }
    
    @ApiOperation(value = "审批展示--依据账号电话号码", notes = "用户手机注册--依据账号电话号码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "user_phone", value = "商会班子手机号", required = true, dataType = "字符串")
	})
	@RequestMapping(value = "showApprovalMenu", method = RequestMethod.POST)
	@ResponseBody
    public JsonResult showApprovalMenu(
    		@RequestParam(value = "user_phone", required = true) String user_phone) {
    	//商会信息实体类
    	CommerceResult commercr = memberApprovalService.checkAccound(user_phone);
    	if(commercr==null) {
    		return new JsonResult(new Throwable("该账号不是商会账号!!请确认"));
    	}
    	//根据商会id获取待审批入会申请--个人会员
    	List<MemberBaseInfo> MemberBaseInfoList= memberApprovalService.getMemberList(commercr.getID());
		return new JsonResult(MemberBaseInfoList);
	}
    
    @ApiOperation(value = "个人,企业,团体会员审核--数据回显展示", notes = "个人,企业,团体会员审核--数据回显展示")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "member_id", value = "会员ID", required = true, dataType = "字符串"),
    	@ApiImplicitParam(paramType = "query", name = "joinId", value = "id", required = true, dataType = "字符串"),
    	@ApiImplicitParam(paramType = "query", name = "member_type", value = "会员类型", required = true, dataType = "字符串")
    })
    @RequestMapping(value = "showMemberInfoDetail", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult showMemberInfoDetail(
    		@RequestParam(value = "member_id", required = true) String member_id,
    		@RequestParam(value = "joinId", required = true) String joinId,
    		@RequestParam(value = "member_type", required = true) String member_type) {
    		Map<String,Object> conditionMap = new HashMap<>();
    		conditionMap.put("member_id", member_id);
    		conditionMap.put("joinId", joinId);
    		conditionMap.put("member_type", member_type);
    		Map<String,Object> dataMap = memberApprovalService.getMemberBaseInfoDetail(conditionMap);
    	return new JsonResult(dataMap);
    }
    
    @ApiOperation(value = "审批", notes = "审批")
 	@ApiImplicitParams({
 			@ApiImplicitParam(paramType = "query", name = "member_id", value = "会员id", required = true, dataType = "字符串"),
 			@ApiImplicitParam(paramType = "query", name = "member_type", value = "会员类型", required = true, dataType = "字符串"),
 			@ApiImplicitParam(paramType = "query", name = "approve_status", value = "审批状态(2:审批通过,3:审批不通过)", required = true, dataType = "字符串"),
 			@ApiImplicitParam(paramType = "query", name = "fail_part", value = "不通过部分", required = false, dataType = "数组"),
 			@ApiImplicitParam(paramType = "query", name = "fail_reason", value = "不通过原因", required = false, dataType = "字符串")
 	})
 	@RequestMapping(value = "approve", method = RequestMethod.POST)
 	@ResponseBody
     public JsonResult approve(
    		 @RequestParam(value = "member_id", required = true) String member_id,
    		 @RequestParam(value = "member_type", required = true) String member_type,
    		 @RequestParam(value = "approve_status", required = true) String approve_status,
    		 @RequestParam(value = "fail_part", required = false) String fail_part,
    		 @RequestParam(value = "fail_reason", required = false) String fail_reason) {
    	Map<String,Object> dataMap = new HashMap<>();
    	
    	if("2".equals(approve_status)) {//审批通过
    		dataMap.put("approve_status", MemberEnum.mmberState.APPROVED.getCode());
			dataMap.put("approve_fail_reason", "");
		}else if("3".equals(approve_status)) {//审批不通过
			dataMap.put("approve_status", MemberEnum.mmberState.FAILED.getCode());
			if(fail_part ==null || "".equals(fail_part)) {
				return new JsonResult(new Throwable("审批-错误资料类别必须勾选"));
			}
			if(fail_reason ==null || "".equals(fail_reason)) {
				return new JsonResult(new Throwable("审批-审批不通过原因不能为空!"));
			}
			dataMap.put("approve_fail_info", fail_part);
			dataMap.put("approve_fail_reason", fail_reason);
		}
    	
    	dataMap.put("member_id", member_id);
    	dataMap.put("member_type", member_type);
    	memberApprovalService.updateApprovelMember(dataMap);
    	
    	return new JsonResult("审批成功");
 	}
    

    @ApiOperation(value = "我的审批历史--搜索", notes = "我的审批历史--搜索")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "member_name", value = "会员名字", required = true, dataType = "字符串"),
    	@ApiImplicitParam(paramType = "query", name = "user_phone", value = "登录手机号", required = true, dataType = "字符串")
    })
    @RequestMapping(value = "queryMemberList", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryMemberList(
    		@RequestParam(value = "member_name", required = true) String member_name,
    		@RequestParam(value = "user_phone", required = true) String user_phone) {
    	//商会信息实体类
    	CommerceResult commercr = memberApprovalService.checkAccound(user_phone);
    	if(commercr==null) {
    		return new JsonResult(new Throwable("该账号不是商会账号!!请确认"));
    	}
    	Map<String,Object> conditionMap = new HashMap<>();
    	conditionMap.put("member_name", member_name);
    	conditionMap.put("approver_id", commercr.getID());
    	List<MemberHistoryInfo> memberHistoryInfoList= memberApprovalService.queryMemberList(conditionMap);
    	return new JsonResult(memberHistoryInfoList);
    }
    
    
    @ApiOperation(value = "我的审批历史", notes = "我的审批历史")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "user_phone", value = "用户手机号码", required = true, dataType = "字符串")
    })
    @RequestMapping(value = "showApproveHistory", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult showApproveHistory(
    		@RequestParam(value = "user_phone", required = true) String user_phone) {
    	//商会信息实体类
    	CommerceResult commercr = memberApprovalService.checkAccound(user_phone);
    	if(commercr==null) {
    		return new JsonResult(new Throwable("该账号不是商会账号!!请确认"));
    	}
    	Map<String,Object> dataMap = new HashMap<>();
    	
    	dataMap.put("approver_id", commercr.getID());
    	List<MemberHistoryInfo> memberHistoryInfoList= memberApprovalService.queryApprovalHistory(dataMap);
    	return new JsonResult(memberHistoryInfoList);
    }
}
