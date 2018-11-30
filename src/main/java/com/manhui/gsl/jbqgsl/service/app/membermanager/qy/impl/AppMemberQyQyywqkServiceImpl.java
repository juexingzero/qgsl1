package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyrzqkMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyywqkMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyrzqkService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyywqkService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业会员，企业业务情况表
 */
@Service
public class AppMemberQyQyywqkServiceImpl implements AppMemberQyQyywqkService {
	@Resource
	private MemberQyQyywqkMapper memberQyQyywqkMapper;
	@Resource
	private ISysParamService iSysParamService;


}
