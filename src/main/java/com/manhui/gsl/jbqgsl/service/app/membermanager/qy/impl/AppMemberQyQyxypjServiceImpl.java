package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyxypjMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyywqkMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyxypjService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyywqkService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**m
 * 企业会员，企业信用评级表
 */
@Service
public class AppMemberQyQyxypjServiceImpl implements AppMemberQyQyxypjService {
	@Resource
	private MemberQyQyxypjMapper memberQyQyxypjMapper;
	@Resource
	private ISysParamService iSysParamService;


}
