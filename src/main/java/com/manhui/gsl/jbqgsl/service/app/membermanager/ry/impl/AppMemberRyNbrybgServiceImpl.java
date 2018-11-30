package com.manhui.gsl.jbqgsl.service.app.membermanager.ry.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyNbrybgMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyXlyxwMapper;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyNbrybg;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyNbrybgService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyXlyxwService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，内部人员办公管理
 */
@Service
public class AppMemberRyNbrybgServiceImpl implements AppMemberRyNbrybgService {
	@Resource
	private MemberRyNbrybgMapper memberRyNbrybgMapper;
	@Resource
	private ISysParamService sysParamService;

	@Override
	public void save(MemberRyNbrybg entity) {

		memberRyNbrybgMapper.save(entity);
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
		return memberRyNbrybgMapper.appQueryMapByRyid(rybid);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public int updateById(MemberRyNbrybg entity) {
		return memberRyNbrybgMapper.updateById(entity);
	}
}
