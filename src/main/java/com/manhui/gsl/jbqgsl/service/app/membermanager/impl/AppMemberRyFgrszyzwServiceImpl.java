package com.manhui.gsl.jbqgsl.service.app.membermanager.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberRyFgrszyzwMapper;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyzw;
import com.manhui.gsl.jbqgsl.service.app.membermanager.APPMemberRyFgrszyzwService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 非公人事主要职务表
 */
@Service
public class AppMemberRyFgrszyzwServiceImpl implements APPMemberRyFgrszyzwService {
	@Resource
	private MemberRyFgrszyzwMapper memberRyFgrszyzwMapper;
	@Resource
	private ISysParamService iSysParamService;

	@Override
	public void save(MemberRyFgrszyzw entity) {
		entity.setId(UUIDUtil.getUUID());
		memberRyFgrszyzwMapper.save(entity);
	}

	/**
	 * 根据关联表id 查询数据
	 * @param paramid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> appQueryMapByParamid(String paramid) {
		return memberRyFgrszyzwMapper.appQueryMapByParamid(paramid);
	}

	/**
	 * 根据关联表paramid 修改数据
	 * @param entity
	 * @return
	 */
	@Override
	public int updateByParamid(MemberRyFgrszyzw entity) {
		return memberRyFgrszyzwMapper.updateByParamid(entity);
	}
}
