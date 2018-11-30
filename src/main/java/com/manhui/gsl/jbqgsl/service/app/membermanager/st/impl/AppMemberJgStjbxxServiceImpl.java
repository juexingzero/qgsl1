
package com.manhui.gsl.jbqgsl.service.app.membermanager.st.impl;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyfrxxResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.StxxBaseResult;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingSuggestMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.st.MemberJgStjbxxMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjbxx;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjs;
import com.manhui.gsl.jbqgsl.service.app.IAppEvaluateFlowingScoreService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyfrxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyzjService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjsService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社团入会，基本信息
 */
@Service
public class AppMemberJgStjbxxServiceImpl implements AppMemberJgStjbxxService {
	@Resource
	private MemberJgStjbxxMapper memberJgStjbxxMapper;
	/**
	 * 社团法人信息
	 */
	@Resource
	private AppMemberQyQyfrxxService appMemberQyQyfrxxService;//
	/**
	 * 社团介绍信息
	 */
	@Resource
	private AppMemberJgStjsService appMemberJgStjsService;

	/**
	 * 社团 证件信息
	 */
	@Resource
	private AppMemberRyRyzjService appMemberRyRyzjService;

	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;
	/**
	 * 保存社团入会基本信息
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult addLeagueBase(StxxBaseResult entity) {
		MemberJgStjbxx stjbxx = new MemberJgStjbxx();
		stjbxx.setId(entity.getStid());
		stjbxx.setStmc(entity.getStmc());
		stjbxx.setStywmc(entity.getStywmc());
		stjbxx.setZjlx(entity.getZjlx());
		stjbxx.setZjhm(entity.getZjhm());
		stjbxx.setPzdjjg(entity.getPzdjjg());
		stjbxx.setYwzgbm(entity.getYwzgbm());
		stjbxx.setClsj(entity.getClsj());
		stjbxx.setCysl(entity.getCysl());
		stjbxx.setMscgzrydh(entity.getMscgzrydh());
		stjbxx.setMscgzryxm(entity.getMscgzryxm());
		stjbxx.setMscgzryzw(entity.getMscgzryzw());
		stjbxx.setDh(entity.getDh());
		stjbxx.setDz(entity.getDz());
		stjbxx.setQywz(entity.getQywz());
		stjbxx.setZip_code(entity.getZip_code());
		stjbxx.setFax(entity.getFax());
		memberJgStjbxxMapper.save(stjbxx);
		return new JsonResult();
	}

	@Override
	public JsonResult updateLeagueBase(StxxBaseResult entity) {
		MemberJgStjbxx stjbxx = new MemberJgStjbxx();
		stjbxx.setId(entity.getStid());
		stjbxx.setStmc(entity.getStmc());
		stjbxx.setStywmc(entity.getStywmc());
		stjbxx.setZjlx(entity.getZjlx());
		stjbxx.setZjhm(entity.getZjhm());
		stjbxx.setPzdjjg(entity.getPzdjjg());
		stjbxx.setYwzgbm(entity.getYwzgbm());
		stjbxx.setClsj(entity.getClsj());
		stjbxx.setCysl(entity.getCysl());
		stjbxx.setMscgzrydh(entity.getMscgzrydh());
		stjbxx.setMscgzryxm(entity.getMscgzryxm());
		stjbxx.setMscgzryzw(entity.getMscgzryzw());
		stjbxx.setDh(entity.getDh());
		stjbxx.setDz(entity.getDz());
		stjbxx.setQywz(entity.getQywz());
		stjbxx.setZip_code(entity.getZip_code());
		stjbxx.setFax(entity.getFax());
		int count = memberJgStjbxxMapper.updateById(stjbxx);
		if(count < 1){
			return new JsonResult(new Throwable("修改异常"));
		}

		if(StringUtils.isNotBlank(entity.getApprove_fail_info()) &&
				entity.getApprove_fail_info().indexOf(MemberEnum.checkType.BASE.getCode())!=-1){
			//修改审批流水表 审批未通过类别
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("approve_fail_info",MemberEnum.checkType.BASE.getCode());
			paramMap.put("member_id",entity.getJoinId());
			iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
		}
		return new JsonResult();
	}

	@Override
	public MemberJgStjbxx queryById(String id) {
		return memberJgStjbxxMapper.queryById(id);
	}

	/**
	 * 查询社团入会，入会数据
	 * @param joinId
	 * @return
	 */
	@Override
	public Map<String, Object> queryLeaguelMemberData(String joinId) {
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> leaguelMap = memberJgStjbxxMapper.queryLeaguelMemberData(joinId);
		if(leaguelMap == null || leaguelMap.isEmpty()){
			return null;
		}
		resultMap.put("state",leaguelMap.get("state"));
		//获得审批未通过类别
		String approve_fail_info = leaguelMap.get("approve_fail_info") == null ? null : leaguelMap.get("approve_fail_info").toString();
		String[] stateTypes = null;
		if(StringUtils.isNotBlank(approve_fail_info)){
			stateTypes = approve_fail_info.toString().split(",");
		}
		Map<String,Object> baseMap = new HashMap<>();
		baseMap.put("data",leaguelMap);
		baseMap.put("state",null);
		if(stateTypes != null && stateTypes.length>0){
			baseMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.BASE.getCode()));
		}
		resultMap.put("base",baseMap);
		String stid = leaguelMap.get("stid").toString();

		//查询法人信息
		Map<String, Object> stfrxxMap = appMemberQyQyfrxxService.queryCompanyApplyData(stid);
		if(stfrxxMap == null || stfrxxMap.isEmpty()){
			resultMap.put("stfr",null);
		}else {
			Map<String,Object> stfrMap = new HashMap<>();
			stfrMap.put("data",stfrxxMap);
			stfrMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				stfrMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.FR.getCode()));
			}
			resultMap.put("stfr",stfrMap);
		}

		//社团介绍信息
		Map<String, Object> qystxxMap = appMemberJgStjsService.queryCompanyApplyData(stid);
		if(qystxxMap == null || qystxxMap.isEmpty()){
			resultMap.put("stjs",null);
		}else {
			Map<String,Object> qystMap = new HashMap<>();
			qystMap.put("data",qystMap);
			qystMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				qystMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.STJS.getCode()));
			}
			resultMap.put("stjs",qystMap);
		}

		//证件信息
		List<Map<String,Object>> stzjxxMap = appMemberRyRyzjService.appQueryMapByRyid(stid);
		if(stzjxxMap == null || stzjxxMap.isEmpty()){
			resultMap.put("stzj",null);
		}else {
			Map<String,Object> stzjMap = new HashMap<>();
			stzjMap.put("data",stzjxxMap);
			stzjMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				stzjMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.ZJXX.getCode()));
			}
			resultMap.put("stzj",stzjMap);
		}

		return resultMap;
	}

	/**
	 * 社团入会，继续入会
	 * @param addMemberParamMap
	 * @return
	 */
	@Override
	public JsonResult leagueContinueAddMember(Map<String, Object> addMemberParamMap) {
		Map<String, Object> leagueDataMap = queryLeaguelMemberData(addMemberParamMap.get("joinId").toString());
		if(leagueDataMap == null || leagueDataMap.isEmpty()){
			return new JsonResult(new Throwable("历史入会数据异常，请刷新重试!"));
		}
		String stid = addMemberParamMap.get("stid").toString();
		Map<String,Object> objMap;
		Map<String,Object> dataMap;
		List<Map<String,Object>> dataListMap;

		//基本信息
		objMap = (Map<String, Object>) leagueDataMap.get("base");
		dataMap = (Map<String, Object>) objMap.get("data");
		StxxBaseResult stxx = new StxxBaseResult();
		stxx.setStid(stid);
		if(dataMap != null && !dataMap.isEmpty()){
			stxx.setStmc(dataMap.get("stmc") == null ? null : dataMap.get("stmc").toString());
			stxx.setStywmc(dataMap.get("stywmc") == null ? null : dataMap.get("stywmc").toString());
			stxx.setZjlx(dataMap.get("zjlx") == null ? null : dataMap.get("zjlx").toString());
			stxx.setZjhm(dataMap.get("zjhm") == null ? null : dataMap.get("zjhm").toString());
			stxx.setPzdjjg(dataMap.get("pzdjjg") == null ? null : dataMap.get("pzdjjg").toString());
			stxx.setYwzgbm(dataMap.get("ywzgbm") == null ? null : dataMap.get("ywzgbm").toString());
			stxx.setClsj(dataMap.get("clsj") == null ? null : dataMap.get("clsj").toString());
			stxx.setCysl(dataMap.get("cysl") == null ? null : (int)dataMap.get("cysl"));
			stxx.setMscgzrydh(dataMap.get("mscgzrydh") == null ? null : dataMap.get("mscgzrydh").toString());
			stxx.setMscgzryxm(dataMap.get("mscgzryxm") == null ? null : dataMap.get("mscgzryxm").toString());
			stxx.setMscgzryzw(dataMap.get("mscgzryzw") == null ? null : dataMap.get("mscgzryzw").toString());
			stxx.setDh(dataMap.get("dh") == null ? null : dataMap.get("dh").toString());
			stxx.setDz(dataMap.get("dz") == null ? null : dataMap.get("dz").toString());
			stxx.setQywz(dataMap.get("qywz") == null ? null : dataMap.get("qywz").toString());
			stxx.setZip_code(dataMap.get("zip_code") == null ? null : (int)dataMap.get("zip_code"));
			stxx.setFax(dataMap.get("fax") == null ? null : dataMap.get("fax").toString());
		}

		//法人信息
		objMap = (Map<String, Object>) leagueDataMap.get("stfr");
		if(objMap == null || objMap.isEmpty()){
			return new JsonResult(new Throwable("社团法人信息异常!"));
		}
		dataMap = (Map<String, Object>) objMap.get("data");
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("社团法人信息异常!"));
		}
		QyfrxxResult stfr = new QyfrxxResult();
		stfr.setQyid(stid);
		stfr.setXm(dataMap.get("xm") == null ? null : dataMap.get("xm").toString());
		stfr.setCsny(dataMap.get("csny") == null ? null : dataMap.get("csny").toString());
		stfr.setXb(dataMap.get("xb") == null ? null : dataMap.get("xb").toString());
		stfr.setJg(dataMap.get("jg") == null ? null : dataMap.get("jg").toString());
		stfr.setIdentity(dataMap.get("identity") == null ? null : dataMap.get("identity").toString());
		stfr.setMz(dataMap.get("mz") == null ? null : dataMap.get("mz").toString());
		stfr.setZzmm(dataMap.get("zzmm") == null ? null : dataMap.get("zzmm").toString());
		stfr.setXl(dataMap.get("xl") == null ? null : dataMap.get("xl").toString());
		stfr.setGszw(dataMap.get("gszw") == null ? null : dataMap.get("gszw").toString());
		stfr.setZc(dataMap.get("zc") == null ? null : dataMap.get("zc").toString());
		stfr.setLxdh(dataMap.get("lxdh") == null ? null : dataMap.get("lxdh").toString());
		stfr.setZj(dataMap.get("zj") == null ? null : dataMap.get("zj").toString());
		stfr.setGqqk(dataMap.get("gqqk") == null ? null : dataMap.get("gqqk").toString());
		stfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());

		stfr.setRdzw(dataMap.get("rdzw") == null ? null : dataMap.get("rdzw").toString());
		stfr.setZxzw(dataMap.get("zxzw") == null ? null : dataMap.get("zxzw").toString());
		stfr.setTtxhmc(dataMap.get("ttxhmc") == null ? null : dataMap.get("ttxhmc").toString());
		stfr.setTtxhzw(dataMap.get("ttxhzw") == null ? null : dataMap.get("ttxhzw").toString());
		stfr.setShzw(dataMap.get("shzw") == null ? null : dataMap.get("shzw").toString());
		stfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());
		stfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());

		//社团介绍 stjs
		objMap = (Map<String, Object>) leagueDataMap.get("stjs");
		if(objMap == null || objMap.isEmpty()){
			return new JsonResult(new Throwable("社团介绍信息异常!"));
		}
		dataMap = (Map<String, Object>) objMap.get("data");
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("社团介绍信息异常!"));
		}
		MemberJgStjs stjs = new MemberJgStjs();
		stjs.setStid(stid);
		stjs.setStjj(dataMap.get("stjj") == null ? null : dataMap.get("stjj").toString());
		stjs.setStzzjg(dataMap.get("stzzjg") == null ? null : dataMap.get("stzzjg").toString());
		stjs.setStjj(dataMap.get("stjj") == null ? null : dataMap.get("stjj").toString());

		//证件信息
		objMap = (Map<String, Object>) leagueDataMap.get("stzj");
		if(objMap == null){
			return new JsonResult(new Throwable("数据异常，证件信息不存在！"));
		}
		dataListMap = (List<Map<String,Object>>) objMap.get("data");
		if(dataListMap == null || dataListMap.isEmpty()){
			return new JsonResult(new Throwable("数据异常，证件信息不存在！"));
		}

		List<MemberRyRyzj> zjList = new ArrayList<>();
		MemberRyRyzj zj;
		for(Map<String,Object> e : dataListMap){
			zj = new MemberRyRyzj();
			if(e.get("ryzjlx")==null){
				return new JsonResult(new Throwable("数据异常，证件证件类型错误！"));
			}
			zj.setRyzjlx(e.get("ryzjlx").toString());
			zj.setFileUrl(e.get("fileUrl") == null ? null : e.get("fileUrl").toString());
			zj.setPan(e.get("pan") == null ? null : e.get("pan").toString());
			zjList.add(zj);
		}

		appMemberRyRyzjService.addIndividualMemberZjData(zjList,stid,null);//证件
		appMemberJgStjsService.addStjsData(stjs);//社团介绍
		appMemberQyQyfrxxService.addLegalPersonData(stfr);//法人信息
		addLeagueBase(stxx);
		return new JsonResult();
	}

	public String getTypeState(String[] stateTypes,String stateType){
		String state = CommonEnum.isNot.YES.getCode();
		for(int i=0;i<stateTypes.length;i++){
			if(stateTypes[i].equals(stateType)){
				state =  CommonEnum.isNot.NO.getCode();
				break;
			}
		}
		return state;
	}
}
