package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQybfjbqkMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyxypjMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQybfjbqkService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyxypjService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**m
 * 企业会员，企业帮扶基本情况表
 */
@Service
public class AppMemberQyQybfjbqkServiceImpl implements AppMemberQyQybfjbqkService {
	@Resource
	private MemberQyQybfjbqkMapper memberQyQybfjbqkMapper;
	@Resource
	private ISysParamService iSysParamService;


}
