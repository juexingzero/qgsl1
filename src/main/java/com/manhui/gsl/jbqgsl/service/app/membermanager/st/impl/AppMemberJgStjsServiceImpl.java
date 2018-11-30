
package com.manhui.gsl.jbqgsl.service.app.membermanager.st.impl;

import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.st.MemberJgStjbxxMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.st.MemberJgStjsMapper;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjs;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjsService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 社团入会，介绍信息
 */
@Service
public class AppMemberJgStjsServiceImpl implements AppMemberJgStjsService {
	@Resource
	private MemberJgStjsMapper memberJgStjsMapper;
	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;
	/**
	 * 保存修改团体介绍信息
	 * @param entity
	 * @return
	 */
	@Override
	public JsonResult addStjsData(MemberJgStjs entity) {
		if(StringUtils.isBlank(entity.getStid())){
			throw new RuntimeException("社团id不能为空!");
		}
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtil.getUUID());
			memberJgStjsMapper.save(entity);
		}else {
			//修改
			//验证是否允许修改
			JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(entity.getJoinId(),null);
			if(jsonResult.getState() == 0){
				return jsonResult;
			}
			memberJgStjsMapper.updateById(entity);

			//修改审批流水表 审批未通过类别
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("approve_fail_info",MemberEnum.checkType.STJS.getCode());
			paramMap.put("member_id",entity.getJoinId());
			iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap);
		}
		return new JsonResult();
	}

	/**
	 * 根据社团id 查询数据
	 * @param stid
	 * @return
	 */
	@Override
	public Map<String, Object> queryCompanyApplyData(String stid) {
		return memberJgStjsMapper.queryCompanyApplyData(stid);
	}
}
