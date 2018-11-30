

package com.manhui.gsl.jbqgsl.service.app.sysuser.impl;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.app.sysuser.AppSysUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.service.app.sysuser.IAppSysUserService;


/**
* @Title: AppCommerceServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.commerce.impl
* @Description: TODO(IAppSysUserService实现层)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppSysUserServiceImpl implements IAppSysUserService {
	@Resource
	private AppSysUserMapper appSysUserMapper;

	@Override
	public HashMap<String, Object> querySysUser(AppUser user) {
		HashMap<String, Object> sysUserMap = appSysUserMapper.querySysUser(user);
		if(sysUserMap !=null && !sysUserMap.isEmpty()) {
			String user_sex = String.valueOf(sysUserMap.get("user_sex"));
			if(user_sex !=null && !"".equals(user_sex)) {
				if("1".equals(user_sex)) {//sys_user表与app_userinfo表中中user_sex值不匹配
					user_sex="0";
				}else {
					user_sex="1";
				}
			}
			String del_flag = String.valueOf(sysUserMap.get("del_flag"));
			if(del_flag !=null && !"".equals(del_flag)) {
				if("1".equals(del_flag)) {//sys_user表与app_userinfo表中中user_sex值不匹配
					del_flag="0";
				}else {
					del_flag="1";
				}
			}
			sysUserMap.put("user_sex",user_sex);
			sysUserMap.put("del_flag",del_flag);
			sysUserMap.put("institution_type", "");
		}
		return sysUserMap;
	}


	
}
