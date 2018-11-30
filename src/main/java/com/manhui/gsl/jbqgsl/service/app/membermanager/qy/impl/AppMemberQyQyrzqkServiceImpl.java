package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyjsMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyrzqkMapper;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyrzqk;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjsService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyrzqkService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业认证情况表
 */
@Service
public class AppMemberQyQyrzqkServiceImpl implements AppMemberQyQyrzqkService {
	@Resource
	private MemberQyQyrzqkMapper memberQyQyrzqkMapper;
	@Resource
	private ISysParamService iSysParamService;


	@Override
	public void save(MemberQyQyrzqk entity) {
		entity.setRzqkzj(UUIDUtil.getUUID());
		memberQyQyrzqkMapper.save(entity);
	}

	/**
	 *根企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryByQyid(String qyid) {
		return memberQyQyrzqkMapper.queryByQyid(qyid);
	}

	@Override
	public int updateById(MemberQyQyrzqk entity) {
		return memberQyQyrzqkMapper.updateById(entity);
	}
}
