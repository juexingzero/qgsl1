package com.manhui.gsl.jbqgsl.controller.app.membermanager.one;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyfrxxResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.StxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyjbxx;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjbxx;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjs;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyfrxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjsService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyzjService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjsService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名称 AppLeagueMemberManagerController.java
 * @类描述 社团会员
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
@Api(tags = "会员管理--社团会员")
@RestController
@RequestMapping(AppLeagueMemberManagerController.ROOT_URL)
public class AppLeagueMemberManagerController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/leagueMember";

	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	@Resource
	private AppMemberJgStjbxxService appMemberJgStjbxxService;
	@Resource
	private AppMemberQyQyfrxxService appMemberQyQyfrxxService;
	@Resource
	private AppMemberJgStjsService appMemberJgStjsService;
	@Resource
	private AppMemberRyRyzjService appMemberRyRyzjService;

	/**
	 * 增加公司入会基本信息
	 * @param entity
	 * @param createUserId
	 * @param createUserName
	 * @param joinObjId
	 * @param joinObjName
	 * @param position
	 * @return
	 */
	@RequestMapping(value ="/addLeagueBase",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addCompanyBase(StxxBaseResult entity,
							  @RequestParam( value = "createUserId", required = true ) String createUserId,
							  @RequestParam( value = "createUserName", required = true ) String createUserName,
							  @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							  @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							  @RequestParam( value = "position", required = true ) String position){
		if(StringUtils.isBlank(entity.getStmc()) || StringUtils.isBlank(entity.getZjlx()) ||
				StringUtils.isBlank(entity.getZjhm()) || StringUtils.isBlank(entity.getMscgzrydh()) ||
				StringUtils.isBlank(entity.getMscgzryxm()) || StringUtils.isBlank(entity.getMscgzryzw())) {
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

		//新增数据
		joinManager.setCreateUserName(createUserName);
		joinManager.setJoinObjName(joinObjName);
		joinManager.setPosition(position);

		JsonResult jsonResult = appMemberJoinManagerService.addLeagueMember(joinManager,entity);
		return jsonResult;
	}

	/**
	 * 社团入会基本信息修改
	 * @param entity
	 * @param createUserId
	 * @param joinObjId
	 * @param joinObjName
	 * @param position
	 * @return
	 */
	@RequestMapping(value ="/editLeagueBase",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editLeagueBase(StxxBaseResult entity,
							   @RequestParam( value = "createUserId", required = true ) String createUserId,
							   @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							   @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							   @RequestParam( value = "position", required = true ) String position){
		if(StringUtils.isBlank(entity.getStmc()) || StringUtils.isBlank(entity.getZjlx()) ||
				StringUtils.isBlank(entity.getZjhm()) || StringUtils.isBlank(entity.getMscgzrydh()) ||
				StringUtils.isBlank(entity.getMscgzryxm()) || StringUtils.isBlank(entity.getMscgzryzw()) ||
				StringUtils.isBlank(entity.getJoinId())  || StringUtils.isBlank(entity.getStid())) {
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

		//修改数据
		joinManager.setJoinId(entity.getJoinId());
		entity.setApprove_fail_info(joinManagerList.get(0).getApprove_fail_info());
		jsonResult = appMemberJoinManagerService.updateLeagueMember(joinManager,entity);
		return jsonResult;
	}
	/**
	 * 团体入会，法人信息保存
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="/addLegalPersonData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addLegalPersonData(QyfrxxResult entity){

		if(StringUtils.isBlank(entity.getXm()) || StringUtils.isBlank(entity.getXb()) ||
				StringUtils.isBlank(entity.getIdentity()) || StringUtils.isBlank(entity.getQyid())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否先保存了企业基本信息
		MemberJgStjbxx stjbxx = appMemberJgStjbxxService.queryById(entity.getQyid());
		if(stjbxx == null){
			return new JsonResult(new Throwable("还没有团体基本信息!"));
		}
		//保存法人信息
		entity = appMemberQyQyfrxxService.addLegalPersonData(entity);
		return new JsonResult(entity);
	}


	/**
	 * 团体入会，法人信息修改
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="editLegalPersonData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editLegalPersonData(QyfrxxResult entity){

		if(StringUtils.isBlank(entity.getXm()) || StringUtils.isBlank(entity.getXb()) ||
				StringUtils.isBlank(entity.getIdentity()) || StringUtils.isBlank(entity.getQyid())||
				StringUtils.isBlank(entity.getFrxxzj()) ||  StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(entity.getJoinId(),null);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}
		//验证是否先保存了团体基本信息
		MemberJgStjbxx stjbxx = appMemberJgStjbxxService.queryById(entity.getQyid());
		if(stjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}
		//保存法人信息
		entity = appMemberQyQyfrxxService.updateLegalPersonData(entity);
		return new JsonResult(entity);
	}

	/**
	 * 保存修改 团体介绍信息
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="/addStjsData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addStjsData(MemberJgStjs entity){

		if(StringUtils.isBlank(entity.getStjj()) || StringUtils.isBlank(entity.getStid()) || StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否保存团体基本信息
		MemberJgStjbxx stjbxx = appMemberJgStjbxxService.queryById(entity.getStid());
		if(stjbxx == null){
			return new JsonResult(new Throwable("还没有团体基本信息!"));
		}
		//保存团体介绍信息
		JsonResult jsonResult = appMemberJgStjsService.addStjsData(entity);
		return jsonResult;
	}

	/**
	 * 保存(修改) 社团证件信息
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value ="/addStZjData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addStZjData(String dataJson){
		if(StringUtils.isBlank(dataJson)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		JSONObject jsonObject = JSON.parseObject( dataJson );
		RyxxBaseResult entity = jsonObject.toJavaObject(RyxxBaseResult.class);

		if(entity.getRyzjList() == null || entity.getRyzjList().isEmpty()
				||StringUtils.isBlank(entity.getRyid()) ||StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否保存团体基本信息
		MemberJgStjbxx stjbxx = appMemberJgStjbxxService.queryById(entity.getRyid());
		if(stjbxx == null){
			return new JsonResult(new Throwable("还没有团体基本信息!"));
		}
		//保存证件信息
		JsonResult jsonResult = appMemberRyRyzjService.addIndividualMemberZjData(entity.getRyzjList(),entity.getRyid(),entity.getJoinId());
		return jsonResult;
	}
}
