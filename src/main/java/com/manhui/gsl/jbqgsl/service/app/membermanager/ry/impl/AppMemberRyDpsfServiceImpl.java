package com.manhui.gsl.jbqgsl.service.app.membermanager.ry.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyDpsfMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyXlyxwMapper;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyDpsf;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyDpsfService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyXlyxwService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，党派身份管理
 */
@Service
public class AppMemberRyDpsfServiceImpl implements AppMemberRyDpsfService {
	@Resource
	private MemberRyDpsfMapper memberRyDpsfMapper;
	@Resource
	private ISysParamService sysParamService;

	@Override
	public void save(MemberRyDpsf entity) {
		entity.setId(UUIDUtil.getUUID());
		memberRyDpsfMapper.save(entity);
	}

    /**
     * 根据人员信息表id 查询数据
	 * @param rybid
     * @return
     */
	@Override
	public List<Map<String, Object>> appQueryMapByRyid(String rybid) {
		if(StringUtils.isBlank(rybid)){
			throw new RuntimeException("appQueryMapByRyid--参数异常!");
		}
		return memberRyDpsfMapper.appQueryMapByRyid(rybid);
	}

	@Override
	public int updateById(MemberRyDpsf entity) {
		return memberRyDpsfMapper.updateById(entity);
	}

	/**
	 * 根据人员基本信息表id 修改数据
	 * @param entity
	 */
	@Override
	public void updateByRyid(MemberRyDpsf entity) {
		int count = memberRyDpsfMapper.updateByRyid(entity);
	}
}
