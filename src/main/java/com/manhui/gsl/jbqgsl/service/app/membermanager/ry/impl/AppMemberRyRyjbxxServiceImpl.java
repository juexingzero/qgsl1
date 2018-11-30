package com.manhui.gsl.jbqgsl.service.app.membermanager.ry.impl;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.RyxxBaseResult;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyRyjbxxMapper;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyzw;
import com.manhui.gsl.jbqgsl.model.member.ry.*;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.APPMemberRyFgrszyzwService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.*;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，人员基本信息管理
 */
@Service
public class AppMemberRyRyjbxxServiceImpl implements AppMemberRyRyjbxxService {
	@Resource
	private ISysParamService iSysParamService;
	@Resource
	private MemberRyRyjbxxMapper memberRyRyjbxxMapper;
	/**
	 * 个人入会。学位学历管理
	 */
	@Resource
	private AppMemberRyXlyxwService appMemberRyXlyxwService;
	/**
	 * 个人入会，党派信息
	 */
	@Resource
	private AppMemberRyDpsfService appMemberRyDpsfService;
	/**
	 * 个人入会，通讯信息
	 */
	@Resource
	private AppMemberRyTxfsService appMemberRyTxfsService;
	/**
	 * 个人入会，人员证件
	 */
	@Resource
	private AppMemberRyRyzjService appMemberRyRyzjService;
	/**
	 * 个人入会，荣誉信息
	 */
	@Resource
	private AppMemberRyFgrszyryService appMemberRyFgrszyryService;
	/**
	 * 职务管理表
	 */
	@Resource
	private APPMemberRyFgrszyzwService aPPMemberRyFgrszyzwService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;

	@Override
	public void save(MemberRyRyjbxx entity) {
		memberRyRyjbxxMapper.save(entity);
	}

	@Override
	public Map<String, Object> queryIndividualMemberData(String joinId) {
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> userMap = memberRyRyjbxxMapper.getAppIndividualApplyDataById(joinId);
		if(userMap == null || userMap.isEmpty()){
			return null;
		}
		dataMap.put("state",userMap.get("state"));
		//获得审批未通过类别
		String approve_fail_info = userMap.get("approve_fail_info") == null ? null : userMap.get("approve_fail_info").toString();
		String[] stateTypes = null;
		if(StringUtils.isNotBlank(approve_fail_info)){
			stateTypes = approve_fail_info.toString().split(",");
		}
		Map<String,Object> baseMap = new HashMap<>();
		baseMap.put("data",userMap);
		baseMap.put("state",null);
		if(stateTypes != null && stateTypes.length>0){
			baseMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.BASE.getCode()));
		}
		dataMap.put("base",baseMap);
		String rybid = userMap.get("ryid").toString();

		//查询证件信息
		List<Map<String,Object>> zjListObj = appMemberRyRyzjService.appQueryMapByRyid(rybid);
		if(zjListObj == null || zjListObj.isEmpty()){
			dataMap.put("zj",null);
		}else{
			Map<String,Object> zjMap = new HashMap<>();
			zjMap.put("data",zjListObj);
			zjMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				zjMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.ZJXX.getCode()));
			}
			dataMap.put("zj",zjMap);
		}

		//查询荣誉信息
		List<Map<String,Object>> ryListObj = appMemberRyFgrszyryService.appQueryMapByRyid(rybid);
		if(ryListObj == null || ryListObj.isEmpty()){
			dataMap.put("ry",null);
		}else {
			Map<String,Object> ryMap = new HashMap<>();
			ryMap.put("data",ryListObj);
			ryMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				ryMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.RY.getCode()));
			}
			dataMap.put("ry",ryMap);
		}
		return dataMap;
	}

	@Override
	public MemberRyRyjbxx queryById(String id) {
		if(StringUtils.isBlank(id)){
			throw new RuntimeException("queryById---参数错误");
		}
		return memberRyRyjbxxMapper.queryById(id);
	}

	@Override
	public int updateById(MemberRyRyjbxx entity) {
		return memberRyRyjbxxMapper.updateById(entity);
	}

	/**
	 * 新增人员 入会基本信息
	 * @param entity
	 */
	@Override
	public void addIndividualMemberData(RyxxBaseResult entity) {
		//人员基本信息保存
		MemberRyRyjbxx ryRyjbxx = new MemberRyRyjbxx();
		ryRyjbxx.setId(entity.getRyid());
		ryRyjbxx.setXm(entity.getXm());
		ryRyjbxx.setXb(entity.getXb());
		ryRyjbxx.setCsrq(entity.getCsrq());
		ryRyjbxx.setJg(entity.getJg());
		ryRyjbxx.setMz(entity.getMz());
		ryRyjbxx.setHyzk(entity.getHyzk());
		ryRyjbxx.setZy(entity.getZy());
		ryRyjbxx.setZc(entity.getZc());
		ryRyjbxx.setRyzjlx(entity.getRyzjlx());
		ryRyjbxx.setRyzjbm(entity.getRyzjbm());
		ryRyjbxx.setPeopleType(entity.getPeopleType() == null ? MemberEnum.peopleType.ONE.getCode() : entity.getPeopleType());
		memberRyRyjbxxMapper.save(ryRyjbxx);
		String ryid = entity.getRyid();
		//党派身份信息保存
		MemberRyDpsf ryDpsf = new MemberRyDpsf();
		ryDpsf.setRyid(ryid);
		ryDpsf.setDpsf(entity.getDpsf());
		appMemberRyDpsfService.save(ryDpsf);

		//学历学位信息保存
		MemberRyXlyxw ryXlyxw = new MemberRyXlyxw();
		ryXlyxw.setRyid(ryid);
		ryXlyxw.setXlmc(entity.getXlmc());
		ryXlyxw.setXxmc(entity.getXxmc());
		appMemberRyXlyxwService.save(ryXlyxw);

		//通讯信息保存
		MemberRyTxfs ryTxfs = new MemberRyTxfs();
		ryTxfs.setRyid(ryid);
		ryTxfs.setYddh(entity.getYddh());
		ryTxfs.setGddh(entity.getGddh());
		ryTxfs.setDzyx(entity.getDzyx());
		ryTxfs.setGsmc(entity.getGsmc());
		ryTxfs.setGszw(entity.getGszw());
		appMemberRyTxfsService.save(ryTxfs);

		//保存职务信息
		MemberRyFgrszyzw ryFgrszyzw = new MemberRyFgrszyzw();
		ryFgrszyzw.setParamid(ryid);
		ryFgrszyzw.setRdzw(entity.getRdzw());
		ryFgrszyzw.setZxzw(entity.getZxzw());
		ryFgrszyzw.setTtxhmc(entity.getTtxhmc());
		ryFgrszyzw.setTtxhzw(entity.getTtxhzw());
		aPPMemberRyFgrszyzwService.save(ryFgrszyzw);
	}

	/**
	 * 修改个人入会基本信息
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult updateIndividualMemberData(RyxxBaseResult entity) {
		SysParam sysParam;
		MemberRyRyjbxx ryRyjbxx = new MemberRyRyjbxx();
		ryRyjbxx.setId(entity.getRyid());
		ryRyjbxx.setXm(entity.getXm());
		ryRyjbxx.setXb(entity.getXb());
		ryRyjbxx.setCsrq(entity.getCsrq());
		ryRyjbxx.setJg(entity.getJg());
		ryRyjbxx.setMz(entity.getMz());
		ryRyjbxx.setHyzk(entity.getHyzk());
		ryRyjbxx.setZy(entity.getZy());
		ryRyjbxx.setZc(entity.getZc());
		ryRyjbxx.setRyzjlx(entity.getRyzjlx());
		ryRyjbxx.setRyzjbm(entity.getRyzjbm());
		//状态
		ryRyjbxx.setState("");
		ryRyjbxx.setPeopleType(entity.getPeopleType() == null ? MemberEnum.peopleType.ONE.getCode() : entity.getPeopleType());
		memberRyRyjbxxMapper.updateById(ryRyjbxx);

		String ryid = entity.getRyid();

		//党派身份信息修改
		MemberRyDpsf ryDpsf = new MemberRyDpsf();
		ryDpsf.setRyid(ryid);
		ryDpsf.setDpsf(entity.getDpsf());
		//验证数据是否存在
		List<Map<String,Object>> ryDpsfList = appMemberRyDpsfService.appQueryMapByRyid(ryid);
		if(ryDpsfList == null || ryDpsfList.isEmpty()){
			//新增
			appMemberRyDpsfService.save(ryDpsf);
		}else {
			//修改
			appMemberRyDpsfService.updateByRyid(ryDpsf);
		}


		//学历学位信息修改
		MemberRyXlyxw ryXlyxw = new MemberRyXlyxw();
		ryXlyxw.setRyid(ryid);
		ryXlyxw.setXlmc(entity.getXlmc());
		ryXlyxw.setXxmc(entity.getXxmc());
		//验证数据是否存在
		List<Map<String,Object>> ryXlyxwList = appMemberRyXlyxwService.appQueryMapByRyid(ryid);
		if(ryXlyxwList == null || ryXlyxwList.isEmpty()){
			//新增
			appMemberRyXlyxwService.save(ryXlyxw);
		}else{
			//修改
			appMemberRyXlyxwService.updateByRyid(ryXlyxw);
		}

		//通讯信息修改
		MemberRyTxfs ryTxfs = new MemberRyTxfs();
		ryTxfs.setRyid(entity.getRyid());
		ryTxfs.setYddh(entity.getYddh());
		ryTxfs.setGddh(entity.getGddh());
		ryTxfs.setDzyx(entity.getDzyx());
		ryTxfs.setGsmc(entity.getGsmc());
		ryTxfs.setGszw(entity.getGszw());
		List<Map<String,Object>> ryTxfsList = appMemberRyTxfsService.appQueryMapByRyid(ryid);
		if(ryTxfsList == null || ryTxfsList.isEmpty()){
			appMemberRyTxfsService.save(ryTxfs);
		}else {
			appMemberRyTxfsService.updateByRyid(ryTxfs);
		}

		//修改职务信息
		MemberRyFgrszyzw ryFgrszyzw = new MemberRyFgrszyzw();
		ryFgrszyzw.setParamid(ryid);
		ryFgrszyzw.setRdzw(entity.getRdzw());
		ryFgrszyzw.setZxzw(entity.getZxzw());
		ryFgrszyzw.setTtxhmc(entity.getTtxhmc());
		ryFgrszyzw.setTtxhzw(entity.getTtxhzw());

		List<Map<String,Object>>  ryFgrszyzwList = aPPMemberRyFgrszyzwService.appQueryMapByParamid(ryid);
		if(ryFgrszyzwList == null || ryFgrszyzwList.isEmpty()){
			//新增
			aPPMemberRyFgrszyzwService.save(ryFgrszyzw);
		}else {
			//修改
			aPPMemberRyFgrszyzwService.updateByParamid(ryFgrszyzw);
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

	/**
	 * 个人入会，继续入会
	 * @param paramMap
	 */
	@Override
	public JsonResult individualContinueAddMember(Map<String, Object> paramMap) {
		//查询历史 入会数据
		Map<String,Object> memberData = queryIndividualMemberData(paramMap.get("joinId").toString());
		if(memberData == null){
			return new JsonResult(new Throwable("历史入会信息异常"));
		}
		Map<String,Object> objMap;
		Map<String,Object> dataMap;
		List<Map<String,Object>> dataListMap;
		objMap = (Map<String, Object>) memberData.get("base");
		dataMap = (Map<String, Object>) objMap.get("data");
		String ryid = paramMap.get("ryid").toString();
		//基本信息
		RyxxBaseResult base = new RyxxBaseResult();
		if(dataMap != null && !dataMap.isEmpty()){
			base.setRyid(ryid);
			base.setXm(dataMap.get("xm") == null ? null : dataMap.get("xm").toString());
			base.setXb(dataMap.get("xb") == null ? null : dataMap.get("xb").toString());
			base.setCsrq(dataMap.get("csrq") == null ? null : dataMap.get("csrq").toString());
			base.setJg(dataMap.get("jg") == null ? null : dataMap.get("jg").toString());
			base.setMz(dataMap.get("mz") == null ? null : dataMap.get("mz").toString());
			base.setHyzk(dataMap.get("hyzk") == null ? null : dataMap.get("hyzk").toString());
			base.setZy(dataMap.get("zy") == null ? null : dataMap.get("zy").toString());
			base.setZc(dataMap.get("zc") == null ? null : dataMap.get("zc").toString());
			base.setRyzjlx(dataMap.get("ryzjlx") == null ? null : dataMap.get("ryzjlx").toString());
			base.setRyzjbm(dataMap.get("ryzjbm") == null ? null : dataMap.get("ryzjbm").toString());
			//党派身份表
			base.setDpsf(dataMap.get("dpsf") == null ? null : dataMap.get("dpsf").toString());
			//学历学位信息表
			base.setXlmc(dataMap.get("xlmc") == null ? null : dataMap.get("xlmc").toString());
			base.setXxmc(dataMap.get("xxmc") == null ? null : dataMap.get("xxmc").toString());
			//通讯信息表
			base.setYddh(dataMap.get("yddh") == null ? null : dataMap.get("yddh").toString());
			base.setGddh(dataMap.get("gddh") == null ? null : dataMap.get("gddh").toString());
			base.setDzyx(dataMap.get("dzyx") == null ? null : dataMap.get("dzyx").toString());
			base.setGsmc(dataMap.get("gsmc") == null ? null : dataMap.get("gsmc").toString());
			base.setGszw(dataMap.get("gszw") == null ? null : dataMap.get("gszw").toString());
			//职务信息表
			base.setRdzw(dataMap.get("rdzw") == null ? null : dataMap.get("rdzw").toString());
			base.setZxzw(dataMap.get("zxzw") == null ? null : dataMap.get("zxzw").toString());
			base.setTtxhmc(dataMap.get("ttxhmc") == null ? null : dataMap.get("ttxhmc").toString());
			base.setTtxhzw(dataMap.get("ttxhzw") == null ? null : dataMap.get("ttxhzw").toString());


		}
		//证件
		objMap = (Map<String, Object>) memberData.get("zj");
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

		//荣誉
		objMap = (Map<String, Object>) memberData.get("zj");
		dataListMap = (List<Map<String,Object>>) objMap.get("data");
		List<MemberRyFgrszyry> ryList = new ArrayList<>();
		if(dataListMap == null && !dataListMap.isEmpty()) {
			MemberRyFgrszyry ry;
			for (Map<String, Object> e : dataListMap) {
				ry = new MemberRyFgrszyry();
				if (e.get("zyrylb") == null) {
					return new JsonResult(new Throwable("数据异常，荣誉类型错误！"));
				}
				ry.setZyrylb(e.get("zyrylb").toString());
				ry.setRyjb(e.get("ryjb") == null ? null : e.get("ryjb").toString());
				ry.setJe(e.get("je") == null ? null : e.get("je").toString());
				ry.setHqsj(e.get("hqsj") == null ? null : e.get("hqsj").toString());
				ry.setRyzsbh(e.get("ryzsbh") == null ? null : e.get("ryzsbh").toString());
				ry.setBfdw(e.get("bfdw") == null ? null : e.get("bfdw").toString());
				ry.setHdyy(e.get("hdyy") == null ? null : e.get("hdyy").toString());
				ry.setQtzyry(e.get("qtzyry") == null ? null : e.get("qtzyry").toString());
				ryList.add(ry);
			}
		}

		//企业介绍信息

		addIndividualMemberData(base);
		appMemberRyRyzjService.addIndividualMemberZjData(zjList,ryid,null);//证件
		appMemberRyFgrszyryService.addIndividualMemberRyData(ryList,ryid,null);//荣誉
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
