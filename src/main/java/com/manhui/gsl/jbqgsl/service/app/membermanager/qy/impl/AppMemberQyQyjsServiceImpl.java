package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberRyFgrszyryMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyjsMapper;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjsService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业介绍表
 */
@Service
public class AppMemberQyQyjsServiceImpl implements AppMemberQyQyjsService {
	@Resource
	private MemberQyQyjsMapper memberQyQyjsMapper;
	@Resource
	private ISysParamService iSysParamService;
	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;

	@Override
	public void save(MemberQyQyjs entity) {
		String qyjszj = UUIDUtil.getUUID();
		entity.setQyjszj(qyjszj);
		memberQyQyjsMapper.save(entity);
	}

	/**
	 * 根据企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryByQyid(String qyid) {

		return memberQyQyjsMapper.queryByQyid(qyid);
	}

	@Override
	public void updateByid(MemberQyQyjs entity) {
		memberQyQyjsMapper.updateById(entity);
	}

	@Override
	public JsonResult addCompanyIntroduceData(QyxxBaseResult entity) {
		MemberQyQyjs qyjs = new MemberQyQyjs();
		qyjs.setQyid(entity.getQyid());
		qyjs.setQyfzlc(entity.getQyfzlc());
		qyjs.setQyjj(entity.getQyjj());
		qyjs.setQywh(entity.getQywh());

		List<Map<String,Object>>  qyjsList = memberQyQyjsMapper.queryByQyid(entity.getQyid());
		if(qyjsList == null || qyjsList.isEmpty()){
			//新增
			save(qyjs);
		}else {
			//修改
			//验证是否允许修改
			JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(entity.getJoinId(),null);
			if(jsonResult.getState() != 1){
				return jsonResult;
			}
			qyjs.setState("");
			qyjs.setQyjszj(qyjsList.get(0).get("qyjszj").toString());
			memberQyQyjsMapper.updateById(qyjs);

			//修改审批流水表 审批未通过类别
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("approve_fail_info",MemberEnum.checkType.QYJS.getCode());
			paramMap.put("member_id",entity.getJoinId());
			iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
		}
		return new JsonResult();
	}

	/**
	 *查询企业介绍（只应对企业入会的，企业介绍内容）
	 *只查询，企业发展历程，企业简介，企业文化
	 * @param qyid qyid 根据企业基本信息表id 查询，并且企业简介内容不能为空
	 * @return
	 */
	@Override
	public Map<String, Object> queryCompanyApplyJsData(String qyid) {
		return memberQyQyjsMapper.queryCompanyApplyJsData(qyid);
	}


}
