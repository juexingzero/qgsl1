package com.manhui.gsl.jbqgsl.service.app.membermanager.ry.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyXlyxwMapper;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyXlyxwService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，学位学历管理
 */
@Service
public class AppMemberRyXlyxwServiceImpl implements AppMemberRyXlyxwService {
	@Resource
	private MemberRyXlyxwMapper memberRyXlyxwMapper;
	@Resource
	private ISysParamService sysParamService;

	@Override
	public void save(MemberRyXlyxw entity) {
		entity.setId(UUIDUtil.getUUID());
		memberRyXlyxwMapper.save(entity);
	}

	/**
	 * 根据人员信息表id 查询数据
	 * @param rybid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> appQueryMapByRyid(String rybid) {
		if(StringUtils.isBlank(rybid)){
			throw new RuntimeException("queryMapByRyid--参数异常!");
		}
		return memberRyXlyxwMapper.appQueryMapByRyid(rybid);
	}

	@Override
	public int updateById(MemberRyXlyxw entity) {
		return memberRyXlyxwMapper.updateById(entity);
	}

	/**
	 *根基人员基本信息表id ，修改数据
	 * @param entity
	 */
	@Override
	public void updateByRyid(MemberRyXlyxw entity) {
		int count = memberRyXlyxwMapper.updateByRyid(entity);
	}
}
