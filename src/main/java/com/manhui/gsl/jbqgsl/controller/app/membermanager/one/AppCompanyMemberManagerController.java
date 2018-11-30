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
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyjbxx;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyfrxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjsService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyzjService;
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
 * @类名称 AppCompanyMemberManagerController.java
 * @类描述 企业会员
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
@Api(tags = "会员管理--企业会员")
@RestController(value = "companyMemberManagerController")
@RequestMapping(AppCompanyMemberManagerController.ROOT_URL)
public class AppCompanyMemberManagerController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/companyMember";
	@Value("${file_personMember_path}")
	private String filePersonMemberImg;
	@Resource
	private ISysParamService iSysParamService;

	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;

	/**
	 * 企业基本信息保存
	 */
	@Resource
	private AppMemberQyQyjbxxService appMemberQyQyjbxxService;
	/**
	 * 企业法人信息
	 */
	@Resource
	private AppMemberQyQyfrxxService appMemberQyQyfrxxService;
	/**
	 * 荣誉信息
	 */
	@Resource
	private AppMemberRyFgrszyryService appMemberRyFgrszyryService;
	/**
	 * 企业介绍信息
	 */
	@Resource
	private AppMemberQyQyjsService appMemberQyQyjsService;
	/**
	 * 企业证件
	 */
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
	@RequestMapping(value ="/addCompanyBase",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addCompanyBase(QyxxBaseResult entity,
							  @RequestParam( value = "createUserId", required = true ) String createUserId,
							  @RequestParam( value = "createUserName", required = true ) String createUserName,
							  @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							  @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							  @RequestParam( value = "position", required = true ) String position){
		if(StringUtils.isBlank(entity.getQymc()) || StringUtils.isBlank(entity.getTyshxydm()) ||
				StringUtils.isBlank(entity.getLxrxm())  || StringUtils.isBlank(entity.getLxrlxfs())){

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
		//企业入会，基本信息保存
		JsonResult jsonResult = appMemberJoinManagerService.addCompanyMember(joinManager,entity);
		return jsonResult;
	}

	/**
	 * 企业入会，基本信息修改
	 * @param entity
	 * @param createUserId
	 * @param joinObjId
	 * @param joinObjName
	 * @param position
	 * @return
	 */
	@RequestMapping(value ="/editCompanyBase",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editCompanyBase(QyxxBaseResult entity,
							   @RequestParam( value = "createUserId", required = true ) String createUserId,
							   @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							   @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							   @RequestParam( value = "position", required = true ) String position){
		if(StringUtils.isBlank(entity.getQymc()) || StringUtils.isBlank(entity.getTyshxydm()) ||
				StringUtils.isBlank(entity.getLxrxm())  || StringUtils.isBlank(entity.getLxrlxfs()) ||
				StringUtils.isBlank(entity.getQyid()) || StringUtils.isBlank(entity.getJoinId())){
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
		}else {
				return new JsonResult(new Throwable(joinObjName+"入会数据不存在或已被删除！"));
		}
		//验证数据
		JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(entity.getJoinId(),joinManagerList);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}

		//修改数据
		joinManager.setJoinObjName(joinObjName);
		joinManager.setPosition(position);
		entity.setApprove_fail_info(joinManagerList.get(0).getApprove_fail_info());
		jsonResult = appMemberJoinManagerService.updateCompanyMember(joinManager,entity);
		return jsonResult;
	}


	/**
	 * 企业入会，法人信息保存
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
		MemberQyQyjbxx qyjbxx = appMemberQyQyjbxxService.queryById(entity.getQyid());
		if(qyjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}
		//保存法人信息
		entity = appMemberQyQyfrxxService.addLegalPersonData(entity);
		return new JsonResult(entity);
	}


	/**
	 * 企业入会，法人信息修改
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
		//验证是否先保存了企业基本信息
		MemberQyQyjbxx qyjbxx = appMemberQyQyjbxxService.queryById(entity.getQyid());
		if(qyjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}
		//保存法人信息
		entity = appMemberQyQyfrxxService.updateLegalPersonData(entity);
		return new JsonResult(entity);
	}


	/**
	 * 新增(修改) 企业荣誉信息
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value ="/addRyData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addRyData(String dataJson){
		if(StringUtils.isBlank(dataJson)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		JSONObject jsonObject = JSON.parseObject( dataJson );
		QyxxBaseResult entity = jsonObject.toJavaObject(QyxxBaseResult.class);

		if(entity.getRyryList() == null || entity.getRyryList().isEmpty()
				||StringUtils.isBlank(entity.getQyid()) || StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}
		//验证是否先保存了企业基本信息
		MemberQyQyjbxx qyjbxx = appMemberQyQyjbxxService.queryById(entity.getQyid());
		if(qyjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}
		List<MemberRyFgrszyry> ryList = entity.getRyryList();
		if(ryList != null && !ryList.isEmpty()){
			for(MemberRyFgrszyry e : ryList){
				if(StringUtils.isBlank(e.getRymc())){
					return new JsonResult(new Throwable("荣誉名称不能为空！"));
				}
			}
		}

		//保存荣誉信息
		JsonResult jsonResult = appMemberRyFgrszyryService.addIndividualMemberRyData(ryList,entity.getQyid(),entity.getJoinId());
		return jsonResult;
	}

	/**
	 * 企业 介绍信息 新增（修改）
	 * @param entity
	 * @return
	 */
	@RequestMapping(value ="/addCompanyIntroduceData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addCompanyIntroduceData(QyxxBaseResult entity){

		if(StringUtils.isBlank(entity.getQyjj()) || StringUtils.isBlank(entity.getQyid()) || StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否先保存了企业基本信息
		MemberQyQyjbxx qyjbxx = appMemberQyQyjbxxService.queryById(entity.getQyid());
		if(qyjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}

		//保存企业介绍信息
		JsonResult jsonResult = appMemberQyQyjsService.addCompanyIntroduceData(entity);
		return jsonResult;
	}

    /**
     * 保存企业证件资料（新增，修改）
     * @param dataJson
     * @return
     */
	@RequestMapping(value ="/addCompanyZjData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addCompanyZjData(String dataJson){
		if(StringUtils.isBlank(dataJson)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		JSONObject jsonObject = JSON.parseObject( dataJson );
		QyxxBaseResult entity = jsonObject.toJavaObject(QyxxBaseResult.class);

		if(entity.getRyzjList() == null ||StringUtils.isBlank(entity.getQyid()) ||StringUtils.isBlank(entity.getJoinId())){
			return new JsonResult(new Throwable("参数错误!"));
		}

		//验证是否先保存了企业基本信息
		MemberQyQyjbxx qyjbxx = appMemberQyQyjbxxService.queryById(entity.getQyid());
		if(qyjbxx == null){
			return new JsonResult(new Throwable("还没有企业基本信息!"));
		}

		//保存企业证件信息
		JsonResult jsonResult = appMemberRyRyzjService.addIndividualMemberZjData(entity.getRyzjList(),entity.getQyid(),entity.getJoinId());
		return jsonResult;
	}


}
