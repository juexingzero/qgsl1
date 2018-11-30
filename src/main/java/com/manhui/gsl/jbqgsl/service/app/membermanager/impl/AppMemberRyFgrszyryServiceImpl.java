package com.manhui.gsl.jbqgsl.service.app.membermanager.impl;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberRyFgrszyryMapper;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
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
 * 公人士主要荣誉表
 */
@Service
public class AppMemberRyFgrszyryServiceImpl implements AppMemberRyFgrszyryService {
	@Resource
	private MemberRyFgrszyryMapper memberRyFgrszyryMapper;
	@Resource
	private ISysParamService iSysParamService;
	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;
	/**
	 * 保存会员个人入会，荣誉信息
	 * @param list
	 * @param ryid
	 * @return
	 */
	@Override
	public JsonResult addIndividualMemberRyData(List<MemberRyFgrszyry> list, String ryid,String joinId) {
		List<MemberRyFgrszyry> resultList = new ArrayList<>();
		if(list != null && !list.isEmpty() && StringUtils.isNotBlank(ryid)){
			boolean flag = false;
			for(MemberRyFgrszyry e : list){
				if(StringUtils.isBlank(e.getId())){
					//新增
					String uuid = UUIDUtil.getUUID();
					e.setId(uuid);
					e.setParamid(ryid);
					e.setState(CommonEnum.isNot.YES.getCode());
					memberRyFgrszyryMapper.save(e);
				}else{
					//修改\
					//验证是否允许修改
					if(StringUtils.isNotBlank(joinId)){
						JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(joinId,null);
						if(jsonResult.getState() != 1){
							return jsonResult;
						}
					}

					e.setState(CommonEnum.isNot.YES.getCode());
					memberRyFgrszyryMapper.updateById(e);
					flag = true;
				}
				resultList.add(e);
			}
			if(flag){
				//修改审批流水表 审批未通过类别
				Map<String,Object> paramMap = new HashMap<>();
				paramMap.put("approve_fail_info",MemberEnum.checkType.RY.getCode());
				paramMap.put("member_id",joinId);
				iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
			}
		}

		return new JsonResult(resultList);
	}

	/**
	 * 根据关联表 id，查询荣誉信息
	 * @param paramid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> appQueryMapByRyid(String paramid) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("paramid",paramid);
		paramMap.put("state",CommonEnum.isNot.YES.getCode());
		return memberRyFgrszyryMapper.appQueryMapByRyid(paramMap);
	}

	/**
	 * 根据id 修改数据
	 * @param entity
	 */
	@Override
	public int updateById(MemberRyFgrszyry entity) {
		int count = memberRyFgrszyryMapper.updateById(entity);
		return count;
	}
}
