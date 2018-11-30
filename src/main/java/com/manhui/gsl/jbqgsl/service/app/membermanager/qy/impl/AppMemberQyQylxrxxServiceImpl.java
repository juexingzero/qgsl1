package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQylxrxxMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyrzqkMapper;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQylxrxx;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQylxrxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyrzqkService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业联系人信息表
 */
@Service
public class AppMemberQyQylxrxxServiceImpl implements AppMemberQyQylxrxxService {
	@Resource
	private MemberQyQylxrxxMapper memberQyQylxrxxMapper;
	@Resource
	private ISysParamService iSysParamService;


	@Override
	public void save(MemberQyQylxrxx entity) {
		entity.setLxrxxzj(UUIDUtil.getUUID());
		memberQyQylxrxxMapper.save(entity);
	}

	/**
	 * 根企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryByQyid(String qyid) {
		return memberQyQylxrxxMapper.queryByQyid(qyid);
	}

	@Override
	public void updateById(MemberQyQylxrxx entity) {
		memberQyQylxrxxMapper.updateById(entity);
	}
}
