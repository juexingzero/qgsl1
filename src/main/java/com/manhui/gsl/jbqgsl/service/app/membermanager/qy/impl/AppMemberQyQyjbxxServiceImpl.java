package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyfrxxResult;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberRyFgrszyryMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyjbxxMapper;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQylxrxx;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyrzqk;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.*;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyzjService;
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
 * 企业会员，企业基本信息表
 */
@Service
public class AppMemberQyQyjbxxServiceImpl implements AppMemberQyQyjbxxService {
	@Resource
	private MemberQyQyjbxxMapper memberQyQyjbxxMapper;
	@Resource
	private ISysParamService iSysParamService;
	/**
	 * 企业介绍信息
	 */
	@Resource
	private AppMemberQyQyjsService appMemberQyQyjsService;
	/**
	 * 企业联系人信息
	 */
	@Resource
	private AppMemberQyQylxrxxService appMemberQyQylxrxxService;
	/**
	 * 企业认证情况
	 */
	@Resource
	private AppMemberQyQyrzqkService appMemberQyQyrzqkService;

	/**
	 * 企业法人信息
	 */
	@Resource
	private AppMemberQyQyfrxxService appMemberQyQyfrxxService;
	/**
	 * 企业荣誉（授奖情况）
	 */
	@Resource
	private AppMemberRyFgrszyryService appMemberRyFgrszyryService;
	/**
	 * 企业证件资料
	 */
	@Resource
	private AppMemberRyRyzjService appMemberRyRyzjService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;

	/**
	 * 新增 企业会员入会基本信息
	 * @param entity
	 */
	@Override
	public void addCompanyMemberData(QyxxBaseResult entity) {
		String qyid = entity.getQyid();
		//保存基本信息
		MemberQyQyjbxx qyQyjbxx = new MemberQyQyjbxx();
		qyQyjbxx.setJbxxzj(qyid);
		qyQyjbxx.setQymc(entity.getQymc());
		qyQyjbxx.setTyshxydm(entity.getTyshxydm());
		qyQyjbxx.setHyfl(entity.getHyfl());
		qyQyjbxx.setQylx(entity.getQylx());
		qyQyjbxx.setQyzcdz(entity.getQyzcdz());
		qyQyjbxx.setZczj(entity.getZczj());
		qyQyjbxx.setClrq(entity.getClrq());
		memberQyQyjbxxMapper.save(qyQyjbxx);

		//保存企业介绍信息
		MemberQyQyjs qyQyjs = new MemberQyQyjs();
		qyQyjs.setQyid(qyid);
		qyQyjs.setQywz(entity.getQywz());
		qyQyjs.setPhone(entity.getPhone());
		qyQyjs.setFax(entity.getFax());
		qyQyjs.setZip_code(entity.getZip_code());
		qyQyjs.setStaff_number(entity.getStaff_number());
		qyQyjs.setParty_number(entity.getParty_number());
		qyQyjs.setCollege_number(entity.getCollege_number());
		qyQyjs.setIs_society(entity.getIs_society());
		qyQyjs.setQypp(entity.getQypp());
		qyQyjs.setJyfw(entity.getJyfw());
		List<Map<String,Object>> qyjsList = appMemberQyQyjsService.queryByQyid(qyid);
		if(qyjsList == null || qyjsList.isEmpty()){
			//新增
			appMemberQyQyjsService.save(qyQyjs);
		}else {
			//修改
			Map<String,Object> qyjsMap = qyjsList.get(0);
			qyQyjs.setQyjszj(qyjsMap.get("qyjszj").toString());
			appMemberQyQyjsService.updateByid(qyQyjs);
		}

		//保存企业联系人新
		MemberQyQylxrxx qyQylxrxx = new MemberQyQylxrxx();
		qyQylxrxx.setQyid(qyid);
		qyQylxrxx.setLxrxm(entity.getLxrxm());
		qyQylxrxx.setLxrlxfs(entity.getLxrlxfs());
		appMemberQyQylxrxxService.save(qyQylxrxx);

		//保存企业认证信息
		MemberQyQyrzqk qyrzqk = new MemberQyQyrzqk();
		qyrzqk.setQyid(qyid);
		qyrzqk.setSfbrzwgxjsqy(entity.getSfbrzwgxjsqy());
		qyrzqk.setGxjsqyrzjg(entity.getGxjsqyrzjg());
		qyrzqk.setSfhdwmzyjckq(entity.getSfhdwmzyjckq());
		qyrzqk.setWmzyrzbm(entity.getWmzyrzbm());
		qyrzqk.setSftgzlglrz(entity.getSftgzlglrz());
		qyrzqk.setZlglrzmb(entity.getZlglrzmb());
		appMemberQyQyrzqkService.save(qyrzqk);
	}

	/**
	 * 修改企业会员 入会基本信息
	 * @param entity
	 */
	@Override
	public void updateCompanyMemberData(QyxxBaseResult entity) {
		String qyid = entity.getQyid();
		//保存基本信息
		MemberQyQyjbxx qyQyjbxx = new MemberQyQyjbxx();
		qyQyjbxx.setJbxxzj(qyid);
		qyQyjbxx.setQymc(entity.getQymc());
		qyQyjbxx.setTyshxydm(entity.getTyshxydm());
		qyQyjbxx.setHyfl(entity.getHyfl());
		qyQyjbxx.setQylx(entity.getQylx());
		qyQyjbxx.setQyzcdz(entity.getQyzcdz());
		qyQyjbxx.setZczj(entity.getZczj());
		qyQyjbxx.setClrq(entity.getClrq());
		qyQyjbxx.setState("");
		memberQyQyjbxxMapper.updateById(qyQyjbxx);

		//保存企业介绍信息
		MemberQyQyjs qyQyjs = new MemberQyQyjs();
		qyQyjs.setQyid(qyid);
		qyQyjs.setQywz(entity.getQywz());
		qyQyjs.setPhone(entity.getPhone());
		qyQyjs.setFax(entity.getFax());
		qyQyjs.setZip_code(entity.getZip_code());
		qyQyjs.setStaff_number(entity.getStaff_number());
		qyQyjs.setParty_number(entity.getParty_number());
		qyQyjs.setCollege_number(entity.getCollege_number());
		qyQyjs.setIs_society(entity.getIs_society());
		qyQyjs.setQypp(entity.getQypp());
		qyQyjs.setJyfw(entity.getJyfw());
		List<Map<String,Object>> qyjsList = appMemberQyQyjsService.queryByQyid(qyid);
		if(qyjsList == null || qyjsList.isEmpty()){
			//新增
			appMemberQyQyjsService.save(qyQyjs);
		}else {
			//修改
			Map<String,Object> qyjsMap = qyjsList.get(0);
			qyQyjs.setQyjszj(qyjsMap.get("qyjszj").toString());
			appMemberQyQyjsService.updateByid(qyQyjs);
		}




		//保存企业联系人新
		MemberQyQylxrxx qyQylxrxx = new MemberQyQylxrxx();
		qyQylxrxx.setQyid(qyid);
		qyQylxrxx.setLxrxm(entity.getLxrxm());
		qyQylxrxx.setLxrlxfs(entity.getLxrlxfs());
		List<Map<String,Object>> qyQylxrxxList = appMemberQyQylxrxxService.queryByQyid(qyid);
		if(qyQylxrxxList == null || qyQylxrxxList.isEmpty()){
			//新增
			appMemberQyQylxrxxService.save(qyQylxrxx);
		}else {
			//修改
			qyQylxrxx.setLxrxxzj(qyQylxrxxList.get(0).get("lxrxxzj").toString());
			appMemberQyQylxrxxService.updateById(qyQylxrxx);
		}



		//保存企业认证信息
		MemberQyQyrzqk qyrzqk = new MemberQyQyrzqk();
		qyrzqk.setQyid(qyid);
		qyrzqk.setSfbrzwgxjsqy(entity.getSfbrzwgxjsqy());
		qyrzqk.setGxjsqyrzjg(entity.getGxjsqyrzjg());
		qyrzqk.setSfhdwmzyjckq(entity.getSfhdwmzyjckq());
		qyrzqk.setWmzyrzbm(entity.getWmzyrzbm());
		qyrzqk.setSftgzlglrz(entity.getSftgzlglrz());
		qyrzqk.setZlglrzmb(entity.getZlglrzmb());
		List<Map<String,Object>> qyrzqkList = appMemberQyQyrzqkService.queryByQyid(qyid);
		if(qyrzqkList == null || qyrzqkList.isEmpty()){
			//新增
			appMemberQyQyrzqkService.save(qyrzqk);
		}else {
			//修改
			qyrzqk.setRzqkzj(qyrzqkList.get(0).get("rzqkzj").toString());
			appMemberQyQyrzqkService.updateById(qyrzqk);
		}

		if(StringUtils.isNotBlank(entity.getApprove_fail_info()) &&
				entity.getApprove_fail_info().indexOf(MemberEnum.checkType.BASE.getCode())!=-1){
			//修改审批流水表 审批未通过类别
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("approve_fail_info",MemberEnum.checkType.BASE.getCode());
			paramMap.put("member_id",entity.getJoinId());
			iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
		}
	}

	/**
	 *
	 * @param jbxxzj
	 * @return
	 */
	@Override
	public MemberQyQyjbxx queryById(String jbxxzj) {
		return memberQyQyjbxxMapper.queryById(jbxxzj);
	}

	/**
	 * 查询企业入会数据
	 * @param joinId
	 * @return
	 */
	@Override
	public Map<String, Object> queryCompanyMemberData(String joinId) {
		Map<String,Object> dataMap = new HashMap<>();
		//查询基本信息
		Map<String, Object> baseMap = memberQyQyjbxxMapper.queryCompanyApplyData(joinId);
		if(baseMap == null || baseMap.isEmpty()){
			return null;
		}
		dataMap.put("state",baseMap.get("state"));
		//获得审批未通过类别
		String approve_fail_info = baseMap.get("approve_fail_info")==null ?null : baseMap.get("approve_fail_info").toString();
		String[] stateTypes = null;
		if(StringUtils.isNotBlank(approve_fail_info)){
			stateTypes = approve_fail_info.toString().split(",");
		}

		Map<String,Object> baseDataMap = new HashMap<>();
		baseDataMap.put("data",baseMap);
		baseDataMap.put("state",null);
		if(stateTypes != null && stateTypes.length>0){
			baseDataMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.BASE.getCode()));
		}
		dataMap.put("base",baseDataMap);
		String qyid = baseMap.get("qyid").toString();

		//查询法人信息
		Map<String, Object> qyfrxxMap = appMemberQyQyfrxxService.queryCompanyApplyData(qyid);
		if(qyfrxxMap == null || qyfrxxMap.isEmpty()){
			dataMap.put("qyfr",null);
		}else {
			Map<String,Object> qyfrMap = new HashMap<>();
			qyfrMap.put("data",qyfrxxMap);
			qyfrMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				qyfrMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.FR.getCode()));
			}
			dataMap.put("qyfr",qyfrMap);
		}

		//查询荣誉授奖情况
		List<Map<String,Object>> qyryListMap = appMemberRyFgrszyryService.appQueryMapByRyid(qyid);
		if(qyryListMap == null || qyryListMap.isEmpty()){
			dataMap.put("qyry",null);
		}else {
			Map<String,Object> qyryMap = new HashMap<>();
			qyryMap.put("data",qyryListMap);
			qyryMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				qyryMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.RY.getCode()));
			}
			dataMap.put("qyry",qyryMap);
		}

		//查询企业介绍内容
		Map<String,Object> qyjsMap = appMemberQyQyjsService.queryCompanyApplyJsData(qyid);
		if(qyjsMap == null || qyjsMap.isEmpty()){
			dataMap.put("qyjs",null);
		}else {
			Map<String,Object> jsMap = new HashMap<>();
			jsMap.put("data",qyjsMap);
			jsMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				jsMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.QYJS.getCode()));
			}
			dataMap.put("qyjs",jsMap);
		}

		//查询证件资料
		List<Map<String,Object>> qyzjMap = appMemberRyRyzjService.appQueryMapByRyid(qyid);
		if(qyzjMap == null || qyzjMap.isEmpty()){
			dataMap.put("qyzj",null);
		}else {
			Map<String,Object> zjMap = new HashMap<>();
			zjMap.put("data",qyzjMap);
			zjMap.put("state",null);
			if(stateTypes != null && stateTypes.length>0){
				zjMap.put("state",getTypeState(stateTypes,MemberEnum.checkType.ZJXX.getCode()));
			}
			dataMap.put("qyzj",zjMap);
		}

		return dataMap;
	}


	/**
	 *  企业入会，继续入会
	 * @param addMemberParamMap
	 */
	@Override
	public JsonResult companyContinueAddMember(Map<String, Object> addMemberParamMap) {
		Map<String, Object> companyDataMap  = queryCompanyMemberData(addMemberParamMap.get("joinId").toString());
		if(companyDataMap == null || companyDataMap.isEmpty()){
			return new JsonResult(new Throwable("历史入会数据异常，请刷新重试!"));
		}
		Map<String,Object> objMap;
		Map<String,Object> dataMap;
		List<Map<String,Object>> dataListMap;
		String qyid = addMemberParamMap.get("qyid").toString();
		//基本信息
		objMap = (Map<String, Object>) companyDataMap.get("base");
		dataMap = (Map<String, Object>) objMap.get("data");
		QyxxBaseResult base = new QyxxBaseResult();
		if(dataMap != null && !dataMap.isEmpty()){
			base.setQyid(qyid);
			base.setQymc(dataMap.get("qymc").toString());
			base.setTyshxydm(dataMap.get("tyshxydm") == null ? null : dataMap.get("tyshxydm").toString());
			base.setHyfl(dataMap.get("hyfl") == null ? null : dataMap.get("hyfl").toString());
			base.setQylx(dataMap.get("qylx") == null ? null : dataMap.get("qylx").toString());
			base.setQyzcdz(dataMap.get("qyzcdz") == null ? null : dataMap.get("qyzcdz").toString());
			base.setZczj(dataMap.get("zczj") == null ? null : (double)dataMap.get("zczj"));
			base.setClrq(dataMap.get("clrq") == null ? null : dataMap.get("clrq").toString());
			base.setQywz(dataMap.get("qywz") == null ? null : dataMap.get("qywz").toString());
			base.setPhone(dataMap.get("phone") == null ? null : dataMap.get("phone").toString());
			base.setFax(dataMap.get("fax") == null ? null : dataMap.get("fax").toString());
			base.setZip_code(dataMap.get("zip_code") == null ? null : dataMap.get("zip_code").toString());
			base.setStaff_number(dataMap.get("staff_number") == null ? null : (int)dataMap.get("staff_number"));
			base.setParty_number(dataMap.get("party_number") == null ? null : (int)dataMap.get("party_number"));
			base.setCollege_number(dataMap.get("college_number") == null ? null : (int)dataMap.get("college_number"));
			base.setIs_society(dataMap.get("is_society") == null ? null : dataMap.get("is_society").toString());
			base.setQypp(dataMap.get("qypp") == null ? null : dataMap.get("qypp").toString());
			base.setJyfw(dataMap.get("jyfw") == null ? null : dataMap.get("jyfw").toString());
			base.setLxrxm(dataMap.get("lxrxm") == null ? null : dataMap.get("lxrxm").toString());
			base.setLxrlxfs(dataMap.get("lxrlxfs") == null ? null : dataMap.get("lxrlxfs").toString());
			base.setSfbrzwgxjsqy(dataMap.get("sfbrzwgxjsqy") == null ? null : dataMap.get("sfbrzwgxjsqy").toString());
			base.setGxjsqyrzjg(dataMap.get("gxjsqyrzjg") == null ? null : dataMap.get("gxjsqyrzjg").toString());
			base.setSfhdwmzyjckq(dataMap.get("sfhdwmzyjckq") == null ? null : dataMap.get("sfhdwmzyjckq").toString());
			base.setWmzyrzbm(dataMap.get("wmzyrzbm") == null ? null : dataMap.get("wmzyrzbm").toString());
			base.setSftgzlglrz(dataMap.get("sftgzlglrz") == null ? null : dataMap.get("sftgzlglrz").toString());
			base.setZlglrzmb(dataMap.get("zlglrzmb") == null ? null : dataMap.get("zlglrzmb").toString());
		}

		//企业法人 信息
		objMap = (Map<String, Object>) companyDataMap.get("qyfr");
		if(objMap == null || objMap.isEmpty()){
			return new JsonResult(new Throwable("企业法人信息异常!"));
		}
		dataMap = (Map<String, Object>)objMap.get("data");
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("企业法人信息异常!"));
		}
		QyfrxxResult qyfr = new QyfrxxResult();
		qyfr.setQyid(qyid);
		qyfr.setXm(dataMap.get("xm") == null ? null : dataMap.get("xm").toString());
		qyfr.setCsny(dataMap.get("csny") == null ? null : dataMap.get("csny").toString());
		qyfr.setXb(dataMap.get("xb") == null ? null : dataMap.get("xb").toString());
		qyfr.setJg(dataMap.get("jg") == null ? null : dataMap.get("jg").toString());
		qyfr.setIdentity(dataMap.get("identity") == null ? null : dataMap.get("identity").toString());
		qyfr.setMz(dataMap.get("mz") == null ? null : dataMap.get("mz").toString());
		qyfr.setZzmm(dataMap.get("zzmm") == null ? null : dataMap.get("zzmm").toString());
		qyfr.setXl(dataMap.get("xl") == null ? null : dataMap.get("xl").toString());
		qyfr.setGszw(dataMap.get("gszw") == null ? null : dataMap.get("gszw").toString());
		qyfr.setZc(dataMap.get("zc") == null ? null : dataMap.get("zc").toString());
		qyfr.setLxdh(dataMap.get("lxdh") == null ? null : dataMap.get("lxdh").toString());
		qyfr.setZj(dataMap.get("zj") == null ? null : dataMap.get("zj").toString());
		qyfr.setGqqk(dataMap.get("gqqk") == null ? null : dataMap.get("gqqk").toString());
		qyfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());

		qyfr.setRdzw(dataMap.get("rdzw") == null ? null : dataMap.get("rdzw").toString());
		qyfr.setZxzw(dataMap.get("zxzw") == null ? null : dataMap.get("zxzw").toString());
		qyfr.setTtxhmc(dataMap.get("ttxhmc") == null ? null : dataMap.get("ttxhmc").toString());
		qyfr.setTtxhzw(dataMap.get("ttxhzw") == null ? null : dataMap.get("ttxhzw").toString());
		qyfr.setShzw(dataMap.get("shzw") == null ? null : dataMap.get("shzw").toString());
		qyfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());
		qyfr.setSyqjyqsfhy(dataMap.get("syqjyqsfhy") == null ? null : dataMap.get("syqjyqsfhy").toString());

		//企业荣誉信息
		objMap = (Map<String, Object>) companyDataMap.get("qyry");
		List<MemberRyFgrszyry> ryList = new ArrayList<>();
		if(objMap != null && !objMap.isEmpty()){
			dataListMap = (List<Map<String, Object>>) objMap.get("data");
			if(dataListMap != null && !dataListMap.isEmpty()){
				MemberRyFgrszyry ry;
				for(Map<String,Object> e : dataListMap){
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
		}
		//企业 介绍信息
		objMap = (Map<String, Object>) companyDataMap.get("qyjs");
		if(objMap == null || objMap.isEmpty()){
			return new JsonResult(new Throwable("企业介绍信息异常!"));
		}
		dataMap = (Map<String, Object>) objMap.get("data");
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("企业介绍信息异常!"));
		}
		QyxxBaseResult qyjs = new QyxxBaseResult();
		qyjs.setQyid(qyid);
		qyjs.setQyfzlc(dataMap.get("qyfzlc") == null ? null : dataMap.get("qyfzlc").toString());
		qyjs.setQyjj(dataMap.get("qyjj") == null ? null : dataMap.get("qyjj").toString());
		qyjs.setQywh(dataMap.get("qywh") == null ? null : dataMap.get("qywh").toString());
		qyjs.setJoinId(addMemberParamMap.get("new_joinId").toString());

		//证件资料
		objMap = (Map<String, Object>) companyDataMap.get("qyzj");
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
		appMemberRyRyzjService.addIndividualMemberZjData(zjList,qyid,null);//证件
		appMemberQyQyjsService.addCompanyIntroduceData(qyjs);//荣誉
		appMemberQyQyfrxxService.addLegalPersonData(qyfr);//法人
		appMemberRyFgrszyryService.addIndividualMemberRyData(ryList,qyid,null);//荣誉
		addCompanyMemberData(base);
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
