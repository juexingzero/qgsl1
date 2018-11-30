package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyGslqybqMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyjsMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyGslqybqService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjsService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业会员，工商联企业标签表
 */
@Service
public class AppMemberQyGslqybqServiceImpl implements AppMemberQyGslqybqService {
	@Resource
	private MemberQyGslqybqMapper memberQyGslqybqMapper;
	@Resource
	private ISysParamService iSysParamService;


}
