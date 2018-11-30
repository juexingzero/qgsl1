package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyfrxxResult;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyfrxxMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyrzqkMapper;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyzw;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyfrxx;
import com.manhui.gsl.jbqgsl.service.app.membermanager.APPMemberRyFgrszyzwService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyfrxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyrzqkService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业法人信息表
 */
@Service
public class AppMemberQyQyfrxxServiceImpl implements AppMemberQyQyfrxxService {
	@Resource
	private MemberQyQyfrxxMapper memberQyQyfrxxMapper;
	/**
	 * 法人职务信息表
	 */
	@Resource
	private APPMemberRyFgrszyzwService aPPMemberRyFgrszyzwService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;

	/**
	 * 保存企业法人信息
	 * @param entity
	 * @return
	 */
	@Override
	public QyfrxxResult addLegalPersonData(QyfrxxResult entity) {
		//保存法人基本信息
		MemberQyQyfrxx qyfrxx = new MemberQyQyfrxx();
		String frxxzj = UUIDUtil.getUUID();
		qyfrxx.setFrxxzj(frxxzj);
		qyfrxx.setXm(entity.getXm());
		qyfrxx.setCsny(entity.getCsny());
		qyfrxx.setXb(entity.getXb());
		qyfrxx.setJg(entity.getJg());
		qyfrxx.setIdentity(entity.getIdentity());
		qyfrxx.setMz(entity.getMz());
		qyfrxx.setZzmm(entity.getZzmm());
		qyfrxx.setXl(entity.getXl());
		qyfrxx.setGszw(entity.getGszw());
		qyfrxx.setZc(entity.getZc());
		qyfrxx.setLxdh(entity.getLxdh());
		qyfrxx.setZj(entity.getZj());
		qyfrxx.setGqqk(entity.getGqqk());
		qyfrxx.setSyqjyqsfhy(entity.getSyqjyqsfhy());
		qyfrxx.setQyid(entity.getQyid());
		memberQyQyfrxxMapper.save(qyfrxx);

		//保存法人职务信息
		MemberRyFgrszyzw fgrszyzw = new MemberRyFgrszyzw();
		fgrszyzw.setParamid(frxxzj);
		fgrszyzw.setRdzw(entity.getRdzw());
		fgrszyzw.setZxzw(entity.getZxzw());
		fgrszyzw.setTtxhmc(entity.getTtxhmc());
		fgrszyzw.setTtxhzw(entity.getTtxhzw());
		fgrszyzw.setShzw(entity.getShzw());
		aPPMemberRyFgrszyzwService.save(fgrszyzw);
		return entity;
	}

	/**
	 * 修改法人信息
	 * @param entity
	 * @return
	 */
	@Override
	public QyfrxxResult updateLegalPersonData(QyfrxxResult entity) {
		//保存法人基本信息
		MemberQyQyfrxx qyfrxx = new MemberQyQyfrxx();
		qyfrxx.setFrxxzj(entity.getFrxxzj());
		qyfrxx.setXm(entity.getXm());
		qyfrxx.setCsny(entity.getCsny());
		qyfrxx.setXb(entity.getXb());
		qyfrxx.setJg(entity.getJg());
		qyfrxx.setIdentity(entity.getIdentity());
		qyfrxx.setMz(entity.getMz());
		qyfrxx.setZzmm(entity.getZzmm());
		qyfrxx.setXl(entity.getXl());
		qyfrxx.setGszw(entity.getGszw());
		qyfrxx.setZc(entity.getZc());
		qyfrxx.setLxdh(entity.getLxdh());
		qyfrxx.setZj(entity.getZj());
		qyfrxx.setGqqk(entity.getGqqk());
		qyfrxx.setSyqjyqsfhy(entity.getSyqjyqsfhy());
		qyfrxx.setState("");
		memberQyQyfrxxMapper.updateById(qyfrxx);

		//保存法人职务信息
		MemberRyFgrszyzw fgrszyzw = new MemberRyFgrszyzw();
		fgrszyzw.setParamid(entity.getFrxxzj());
		fgrszyzw.setRdzw(entity.getRdzw());
		fgrszyzw.setZxzw(entity.getZxzw());
		fgrszyzw.setTtxhmc(entity.getTtxhmc());
		fgrszyzw.setTtxhzw(entity.getTtxhzw());
		fgrszyzw.setShzw(entity.getShzw());
		List<Map<String,Object>> fgrszyzwList = aPPMemberRyFgrszyzwService.appQueryMapByParamid(entity.getFrxxzj());
		if(fgrszyzwList == null || fgrszyzwList.isEmpty()){
			//新增
			aPPMemberRyFgrszyzwService.save(fgrszyzw);
		}else {
			//修改
			fgrszyzw.setParamid(qyfrxx.getFrxxzj());
			aPPMemberRyFgrszyzwService.updateByParamid(fgrszyzw);
		}
		//修改审批流水表 审批未通过类别
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("approve_fail_info",MemberEnum.checkType.FR.getCode());
		paramMap.put("member_id",entity.getJoinId());
		iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
		return entity;
	}

	@Override
	public Map<String, Object> queryCompanyApplyData(String qyid) {
		Map<String, Object> qyfrxxMap = memberQyQyfrxxMapper.qeuryByQyid(qyid);
		return qyfrxxMap;
	}
}
