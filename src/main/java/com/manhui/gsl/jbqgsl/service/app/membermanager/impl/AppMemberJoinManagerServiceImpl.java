package com.manhui.gsl.jbqgsl.service.app.membermanager.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsState;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.StxxBaseResult;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberJoinManagerMapper;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ParamModel;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.*;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;
import com.manhui.gsl.jbqgsl.service.web.commerce.ICommerceManagerService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员入会管理
 */
@Service
public class AppMemberJoinManagerServiceImpl implements AppMemberJoinManagerService {
	@Resource
	private MemberJoinManagerMapper memberJoinManagerMapper;
	@Resource
	private AppMemberJgStjbxxService appMemberJgStjbxxService;

	/**
	 * 个人入会信息管理
	 */
	@Resource
	private AppMemberRyRyjbxxService appMemberRyRyjbxxService;

	/**
	 * 企业入会信息管理
	 */
	@Resource
	private AppMemberQyQyjbxxService appMemberQyQyjbxxService;
	/**
	 * 商会信息
	 */
	@Resource
	private ICommerceManagerService iCommerceManagerService;
	/**
	 * 查询集合 数据，不分页
	 * @param entity
	 * @return
	 */
	@Override
	public List<MemberJoinManager> queryAllList(MemberJoinManager entity) {
		List<MemberJoinManager> list = memberJoinManagerMapper.queryAllList(entity);
		if(list != null && !list.isEmpty()){
			MemberJgShxx shxx;
			for(MemberJoinManager e : list){
				shxx = iCommerceManagerService.queryCommerceDetail(e.getJoinObjId());
				if(shxx != null){
					e.setJoinObjLxr(shxx.getLXR());
					e.setJoinObjLxrDh(shxx.getSJ());
				}
			}
		}
		return list;
	}

	@Override
	public void save(MemberJoinManager joinManager) {
		String strTime = new DateTime().toString(DateUtil.dateTimeFormat);
		joinManager.setCreateTime(strTime);
		joinManager.setUpdateTime(strTime);
		if(StringUtils.isBlank(joinManager.getState())){
			joinManager.setState(MemberEnum.mmberState.ADD.getCode());//新增状态
		}
		memberJoinManagerMapper.save(joinManager);

		//删除审核未通过的数据
		MemberJoinManager entity = new MemberJoinManager();
		entity.setCreateUserId(joinManager.getCreateUserId());
		entity.setState(MemberEnum.mmberState.DEL.getCode());//删除状态
		memberJoinManagerMapper.delByCreateUserIdAndState(entity);
	}

	/**
	 * 获得手机端 个人入会 入会申请填写资料
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> getAppApplyData(Map<String,Object> paramMap) {
		Map<String,Object> dataMap = new HashMap<>();
		if(MemberEnum.mmberType.INDIVIDUAL.getCode().equals(paramMap.get("memberType"))){
			//个人 入会
			dataMap = appMemberRyRyjbxxService.queryIndividualMemberData(paramMap.get("joinId").toString());

		}else if(MemberEnum.mmberType.GROUP.getCode().equals(paramMap.get("memberType"))){
			//团体 入会
			dataMap = appMemberJgStjbxxService.queryLeaguelMemberData(paramMap.get("joinId").toString());

		}else if(MemberEnum.mmberType.ENTERPRISE.getCode().equals(paramMap.get("memberType"))){
			//企业 入会
			dataMap = appMemberQyQyjbxxService.queryCompanyMemberData(paramMap.get("joinId").toString());
		}



		return dataMap;
	}

	@Override
	public int updateById(MemberJoinManager entity) {
		entity.setUpdateTime(new DateTime().toString(DateUtil.dateTimeFormat));
		return memberJoinManagerMapper.updateById(entity);
	}

	/**
	 * 根据 joinObjIdList查询数据
	 * @param entity joinObjIdList=商会id集合，state=数据状态（参照系统参数,不传查询所有）
	 * @return
	 */
	@Override
	public List<MemberJoinManager> queryByjoinObjIdList(MemberJoinManager entity) {
		return memberJoinManagerMapper.queryByjoinObjIdList(entity);
	}
	/**
	 * 根据商会ID以及状态,获取到入会会员的基本信息(会员名称,入会时间 会员类型)
	 */
	@Override
	public List<MemberBaseInfo> queryMemberBaseInfo(MemberJoinManager joinManager) {
		return memberJoinManagerMapper.queryMemberBaseInfo(joinManager);
	}

	/**
	 * 判断是否能提交入会申请
	 * @param paramMap
	 * @return
	 */
	@Override
	public JsonResult sendApplyJudge(Map<String, Object> paramMap) {

		Map<String,Object> dataMap = getAppApplyData(paramMap);
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("还没有申请记录"));
		}
		String applyState = dataMap.get("state").toString();
		Map<String,String> stateMap = MemberEnum.mmberState.codeMap;
		if(!MemberEnum.mmberState.ADD.getCode().equals(applyState)){
			return new JsonResult(new Throwable("当前状态为"+stateMap.get(applyState)+"不能提交入会申请！"));
		}
		if(MemberEnum.mmberType.INDIVIDUAL.getCode().equals(paramMap.get("memberType"))){
			//个人 入会
			Object obj;
			Map<String,Object> objMap;

			objMap = (Map<String, Object>) dataMap.get("base");
			String state = objMap.get("state")+"";
			if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){//状态否
				return new JsonResult(new Throwable("基本信息，审批未通过！"));
			}
			obj =  dataMap.get("zj");
			if(obj == null){
				return new JsonResult(new Throwable("还未填写证件信息"));
			}else{
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("证件信息，审批未通过！"));
				}
			}

			obj = dataMap.get("ry");
			if (obj == null){
				//return new JsonResult(new Throwable("还未填写荣誉信息"));
			}else{
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("荣誉信息，审批未通过！"));
				}
			}

		}else if(MemberEnum.mmberType.GROUP.getCode().equals(paramMap.get("memberType"))){
			//团体 入会
			Object obj;
			Map<String,Object> objMap;

			objMap = (Map<String, Object>) dataMap.get("base");
			String state = objMap.get("state")+"";
			if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){//状态否
				return new JsonResult(new Throwable("基本信息，审批未通过！"));
			}
			//法人信息
			obj =  dataMap.get("stfr");
			if(obj == null){
				return new JsonResult(new Throwable("还未填写法人信息"));
			}else{
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("法人信息，审批未通过！"));
				}
			}
			//社团介绍
			obj =  dataMap.get("stjs");
			if(obj == null){
				return new JsonResult(new Throwable("还未填写社团介绍信息"));
			}else{
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("社团介绍信息，审批未通过！"));
				}
			}
			//证件信息
			obj =  dataMap.get("stzj");
			if(obj == null){
				return new JsonResult(new Throwable("还未填写社团证件信息"));
			}else{
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("社团证件信息，审批未通过！"));
				}
			}

		}else if(MemberEnum.mmberType.ENTERPRISE.getCode().equals(paramMap.get("memberType"))){
			//企业 入会
			Object obj;
			Map<String,Object> objMap;

			objMap = (Map<String, Object>) dataMap.get("base");
			String state = objMap.get("state")+"";
			if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
				return new JsonResult(new Throwable("基本信息，审批未通过！"));
			}

			obj = dataMap.get("qyfr");
			if(obj == null){
				return new JsonResult(new Throwable("还未填写企业法人信息"));
			}else {
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("企业法人信息，审批未通过！"));
				}
			}

			obj = dataMap.get("qyry");
			if (obj == null){
				//return new JsonResult(new Throwable("还未填写企业荣誉信息"));
			}else {
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("企业荣誉信息，审批未通过！"));
				}
			}

			obj = dataMap.get("qyjs");
			if(obj == null){
				//return new JsonResult(new Throwable("还未填写企业介绍信息"));
			}else {
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("企业介绍信息，审批未通过！"));
				}
			}

			obj = dataMap.get("qyzj");
			if (obj == null){
				return new JsonResult(new Throwable("还未填写企业证件信息"));
			}else {
				objMap = (Map<String, Object>)obj;
				state = objMap.get("state")+"";
				if(StringUtils.isNotBlank(state) && state.equals(CommonEnum.isNot.NO.getCode())){
					return new JsonResult(new Throwable("企业证件信息，审批未通过！"));
				}
			}
		}
		return new JsonResult();
	}

	/**
	 * 验证修改 memberjoinManager表数据状态
	 * @param joinId
	 * @return
	 */
	public JsonResult  verifyJoinManagerState(String joinId,List<MemberJoinManager> joinManagerList){
		MemberJoinManager joinManager = new MemberJoinManager();
		joinManager.setJoinId(joinId);
		if(joinManagerList == null || joinManagerList.isEmpty()){
			joinManagerList = memberJoinManagerMapper.queryAllList(joinManager);
		}

		if(joinManagerList == null || joinManagerList.isEmpty()){
			return new JsonResult(new Throwable("数据不存在或已经被删除!"));
		}
		String joinState = "";
		String HYSP03 = MemberEnum.mmberState.PENDING.getCode();//待审批

		String HYSP02 =  MemberEnum.mmberState.APPROVED.getCode();//已经审批
		String HYSP04 = MemberEnum.mmberState.DEL.getCode();//删除
		for(MemberJoinManager e : joinManagerList){
			if(e.getState().equals(HYSP04)){
				return new JsonResult(new Throwable("当前状态不能修改数据，请刷新重试!"));
			}
			joinState = e.getState();
		}
		String HYSP01 =  MemberEnum.mmberState.FAILED.getCode();
		if(joinState.equals(HYSP01)){
			//审批不通过的情况下将状态修改为新增（未送审状态）
			joinManager.setState( MemberEnum.mmberState.ADD.getCode());
			memberJoinManagerMapper.updateById(joinManager);
		}
		return new JsonResult();
	}

	/**
	 * 新增个人入会
	 * @param entity
	 * @param joinManager
	 * @return
	 */
	@Override
	public JsonResult addUserMember(RyxxBaseResult entity, MemberJoinManager joinManager) {
		//新增数据
		String joinId = UUIDUtil.getUUID();
		String ryid = UUIDUtil.getUUID();
		joinManager.setJoinId(joinId);
		joinManager.setMemberId(ryid);
		joinManager.setMemberType(MemberEnum.mmberType.INDIVIDUAL.getCode());
		//保存
		save(joinManager);

		//个人入会，人员基本信息保存
		entity.setRyid(ryid);
		appMemberRyRyjbxxService.addIndividualMemberData(entity);

		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("ryid",ryid);
		resultMap.put("joinId",joinId);
		return new JsonResult(resultMap);
	}

	/**
	 * 修改个人入会
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult updateUserMember(MemberJoinManager joinManager, RyxxBaseResult entity) {

		updateById(joinManager);

		//个人入会，人员基本信息修改
		JsonResult jsonResult = appMemberRyRyjbxxService.updateIndividualMemberData(entity);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("ryid",entity.getRyid());
		resultMap.put("joinId",entity.getJoinId());
		return new JsonResult(resultMap);
	}

	/**
	 * 新增企业入会 基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult addCompanyMember(MemberJoinManager joinManager, QyxxBaseResult entity) {
		String joinId = UUIDUtil.getUUID();
		String qyid = UUIDUtil.getUUID();
		joinManager.setJoinId(joinId);
		joinManager.setMemberId(qyid);
		joinManager.setMemberType(MemberEnum.mmberType.ENTERPRISE.getCode());
		save(joinManager);

		//个人入会，人员基本信息保存
		entity.setQyid(qyid);
		appMemberQyQyjbxxService.addCompanyMemberData(entity);
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("qyid",qyid);
		resultMap.put("joinId",joinId);
		return new JsonResult(resultMap);
	}

	/**
	 * 企业入会，基本信息修改
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult updateCompanyMember(MemberJoinManager joinManager, QyxxBaseResult entity) {
		joinManager.setJoinId(entity.getJoinId());
		updateById(joinManager);

		//个人入会，人员基本信息修改
		appMemberQyQyjbxxService.updateCompanyMemberData(entity);
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("qyid",entity.getQyid());
		resultMap.put("joinId",entity.getJoinId());
		return new JsonResult(resultMap);
	}

	/**
	 * 新增团体入会 基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult addLeagueMember(MemberJoinManager joinManager, StxxBaseResult entity) {
		String joinId = UUIDUtil.getUUID();
		String stid = UUIDUtil.getUUID();
		joinManager.setJoinId(joinId);
		joinManager.setMemberId(stid);
		joinManager.setMemberType(MemberEnum.mmberType.GROUP.getCode());
		save(joinManager);

		//社团入会，保存基本信息
		entity.setStid(stid);
		JsonResult jsonResult = appMemberJgStjbxxService.addLeagueBase(entity);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("stid",stid);
		resultMap.put("joinId",joinId);
		return new JsonResult(resultMap);
	}

	/**
	 * 修改社团 入会基本信息
	 * @param joinManager
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult updateLeagueMember(MemberJoinManager joinManager, StxxBaseResult entity) {
		joinManager.setJoinId(entity.getJoinId());
		updateById(joinManager);

		//社团入会，基本信息修改
		JsonResult jsonResult = appMemberJgStjbxxService.updateLeagueBase(entity);
		if(jsonResult.getState() != 1){
			return jsonResult;
		}
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("stid",entity.getStid());
		resultMap.put("joinId",entity.getJoinId());
		return new JsonResult(resultMap);
	}

	/**
	 * 查询商会 会员信息
	 * @param param
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> queryMemberStaffList(ParamModel param) {
		PageHelper.startPage( param.getPageNo(), param.getPageSize() );//当前第几页，每页显示多少条
		List<Map<String, Object>> dataList = memberJoinManagerMapper.queryMemberStaffListPage( param );

		PageInfo<Map<String, Object>> info;
		if(dataList != null && !dataList.isEmpty()){
			info = new PageInfo<Map<String, Object>>( dataList );
		}else{
			info = new PageInfo<Map<String, Object>>();
		}
		return info;
	}

	/**
	 * 继续加入商会
	 * @param joinManager
	 * @return
	 */
	@Override
	public JsonResult continueAddMember(MemberJoinManager joinManager,String joinId) {
		String new_joinId = UUIDUtil.getUUID();
		String memberId = UUIDUtil.getUUID();
		joinManager.setMemberId(memberId);
		joinManager.setJoinId(new_joinId);
		joinManager.setState(MemberEnum.mmberState.PENDING.getCode());
		save(joinManager);

		Map<String,Object> addMemberParamMap = new HashMap<>();
		addMemberParamMap.put("new_joinId",new_joinId);
		addMemberParamMap.put("joinId",joinId);

		//判断入会类型
		if(joinManager.getMemberType().equals(MemberEnum.mmberType.INDIVIDUAL.getCode())){
			addMemberParamMap.put("ryid",memberId);
			JsonResult jsonResult = appMemberRyRyjbxxService.individualContinueAddMember(addMemberParamMap);
			if(jsonResult.getState() == 0){
				return jsonResult;
			}
		}else if(joinManager.getMemberType().equals(MemberEnum.mmberType.ENTERPRISE.getCode())){
			addMemberParamMap.put("qyid",memberId);
			JsonResult jsonResult = appMemberQyQyjbxxService.companyContinueAddMember(addMemberParamMap);
			if(jsonResult.getState() == 0){
				return jsonResult;
			}
		}else if(joinManager.getMemberType().equals(MemberEnum.mmberType.GROUP.getCode())){
			addMemberParamMap.put("stid",memberId);
			JsonResult jsonResult = appMemberJgStjbxxService.leagueContinueAddMember(addMemberParamMap);
			if(jsonResult.getState() == 0){
				return jsonResult;
			}
		}
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("joinId",new_joinId);

		return new JsonResult(resultMap);
	}

}
