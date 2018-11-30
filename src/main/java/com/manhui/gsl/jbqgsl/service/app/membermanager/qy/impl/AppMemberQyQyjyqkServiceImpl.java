package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyjyqkMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyrzqkMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjyqkService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyrzqkService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业会员，企业经营情况表
 */
@Service
public class AppMemberQyQyjyqkServiceImpl implements AppMemberQyQyjyqkService {
	@Resource
	private MemberQyQyjyqkMapper memberQyQyjyqkMapper;
	@Resource
	private ISysParamService iSysParamService;


}
