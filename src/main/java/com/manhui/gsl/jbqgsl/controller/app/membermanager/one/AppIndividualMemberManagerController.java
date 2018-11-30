package com.manhui.gsl.jbqgsl.controller.app.membermanager.one;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.*;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.*;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @类名称 AppCompanyElegantController.java
 * @类描述 个人会员
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
@Api(tags = "会员管理--个人会员")
@RestController
@RequestMapping(AppIndividualMemberManagerController.ROOT_URL)
public class AppIndividualMemberManagerController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/individualMember";
	@Value("${file_personMember_path}")
	private String filePersonMemberImg;

	@Autowired
	private AppMemberJoinManagerService appMemberJoinManagerService;
	@Resource
	private ISysParamService iSysParamService;

	@Resource
	private AppMemberRyRyjbxxService appMemberRyRyjbxxService;

	/**
	 * 证件信息
	 */
	@Resource
	private AppMemberRyRyzjService appMemberRyRyzjService;

	/**
	 * 荣誉信息
	 */
	@Resource
	private AppMemberRyFgrszyryService appMemberRyFgrszyryService;


	/**
	 *个人 入会，新增个人基本信息
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="/addUser",method = RequestMethod.POST)
	@ResponseBody
	 public JsonResult addUser(RyxxBaseResult entity,
							   @RequestParam( value = "createUserId", required = true ) String createUserId,
							   @RequestParam( value = "createUserName", required = true ) String createUserName,
							   @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							   @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							   @RequestParam( value = "position", required = true ) String position){
	 	if(StringUtils.isBlank(entity.getXm()) || StringUtils.isBlank(entity.getXb()) ||
				 StringUtils.isBlank(entity.getCsrq())  || StringUtils.isBlank(entity.getJg()) ||
				StringUtils.isBlank(entity.getYddh()) ){

			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证当前人员是否能新增数据
		MemberJoinManager joinManager = new MemberJoinManager();
	 	joinManager.setCreateUserId(createUserId);
		joinManager.setJoinObjId(joinObjId);
		List<MemberJoinManager> joinManagerList = appMemberJoinManagerService.queryAllList(joinManager);
		if(joinManagerList != null && !joinManagerList.isEmpty()){
			return new JsonResult(new Throwable(joinObjName+"已经入会，不能重复申请！"));
		}

		joinManager.setCreateUserName(createUserName);
		joinManager.setJoinObjName(joinObjName);
		joinManager.setPosition(position);

		JsonResult jsonResult = appMemberJoinManagerService.addUserMember(entity,joinManager);

	 	return jsonResult;
	 }

	/**
	 *个人 入会，修改个人基本信息
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="/editUser",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editUser(RyxxBaseResult entity,
							  @RequestParam( value = "createUserId", required = true ) String createUserId,
							  @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							  @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							  @RequestParam( value = "position", required = true ) String position){
		if(StringUtils.isBlank(entity.getXm()) || StringUtils.isBlank(entity.getXb()) ||
				StringUtils.isBlank(entity.getCsrq())  || StringUtils.isBlank(entity.getJg()) ||
				StringUtils.isBlank(entity.getYddh()) || StringUtils.isBlank(entity.getRyid())
				|| StringUtils.isBlank(entity.getJoinId())){

			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证当前人员是否能保存数据
		MemberJoinManager joinManager = new MemberJoinManager();
		joinManager.setCreateUserId(createUserId);
		joinManager.setJoinObjId(joinObjId);
		List<MemberJoinManager> joinManagerList = appMemberJoinManagerService.queryAllList(joinManager);
		if(joinManagerList != null && !joinManagerList.isEmpty()){
			for(MemberJoinManager e : joinManagerList){
				if(!entity.getJoinId().equals(e.getJoinId())){
					return new JsonResult(new Throwable(joinObjName+"已经入会，不能重复申请！"));
				}
			}
		}else{
			return new JsonResult(new Throwable(joinObjName+"入会数据不存在或已被删除！"));
		}

		//验证数据
		JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(entity.getJoinId(),joinManagerList);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}
		joinManager.setJoinId(entity.getJoinId());
		joinManager.setJoinObjName(joinObjName);
		joinManager.setPosition(position);
		//修改数据
		entity.setApprove_fail_info(joinManagerList.get(0).getApprove_fail_info());
		jsonResult = appMemberJoinManagerService.updateUserMember(joinManager,entity);
		return jsonResult;
	}



	/**
	 * 保存(修改) 人员证件信息
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value ="/addRyZjData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addRyZjData(String dataJson){
        if(StringUtils.isBlank(dataJson)){
            return new JsonResult(new Throwable("参数错误!"));
        }
        JSONObject jsonObject = JSON.parseObject( dataJson );
        RyxxBaseResult entity = jsonObject.toJavaObject(RyxxBaseResult.class);

		if(entity.getRyzjList() == null || entity.getRyzjList().isEmpty()
				||StringUtils.isBlank(entity.getRyid()) ||StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否先保存了人员信息
		MemberRyRyjbxx ryRyjbxx = appMemberRyRyjbxxService.queryById(entity.getRyid());
		if(ryRyjbxx == null){
			return new JsonResult(new Throwable("还没有人员基本信息!"));
		}
		//保存证件信息
		JsonResult jsonResult = appMemberRyRyzjService.addIndividualMemberZjData(entity.getRyzjList(),entity.getRyid(),entity.getJoinId());
		return jsonResult;
	}


	/**
	 * 新增(修改) 人员荣誉信息
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value ="/addRyRyData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addRyRyData(String dataJson){
	    if(StringUtils.isBlank(dataJson)){
            return new JsonResult(new Throwable("参数错误!"));
        }
        JSONObject jsonObject = JSON.parseObject( dataJson );
        RyxxBaseResult entity = jsonObject.toJavaObject(RyxxBaseResult.class);

        if(entity.getRyryList() == null || entity.getRyryList().isEmpty()
				||StringUtils.isBlank(entity.getRyid()) ||StringUtils.isBlank(entity.getJoinId()) ){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否先保存了人员信息
		MemberRyRyjbxx ryRyjbxx = appMemberRyRyjbxxService.queryById(entity.getRyid());
		if(ryRyjbxx == null){
			return new JsonResult(new Throwable("还没有人员基本信息!"));
		}

		List<MemberRyFgrszyry> ryList = entity.getRyryList();
		if(ryList != null && !ryList.isEmpty()){
			Map<String,String> paramMap = MemberEnum.primaryHonor.codeMap;
			for(MemberRyFgrszyry e : ryList){
				if(StringUtils.isBlank(paramMap.get(e.getZyrylb()))){
					return new JsonResult(new Throwable("荣誉类型错误！"));
				}
			}
		}
		//保存荣誉信息
		JsonResult jsonResult = appMemberRyFgrszyryService.addIndividualMemberRyData(ryList,entity.getRyid(),entity.getJoinId());
		return jsonResult;
	}
}
