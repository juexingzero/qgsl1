package com.manhui.gsl.jbqgsl.service.app.membermanager.qy.impl;

import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQygybfqkMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.qy.MemberQyQyxypjMapper;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQygybfqkService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyxypjService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**m
 * 企业会员，企业公益帮扶情况表
 */
@Service
public class AppMemberQyQygybfqkServiceImpl implements AppMemberQyQygybfqkService {
	@Resource
	private MemberQyQygybfqkMapper memberQyQygybfqkMapper;
	@Resource
	private ISysParamService iSysParamService;


}
