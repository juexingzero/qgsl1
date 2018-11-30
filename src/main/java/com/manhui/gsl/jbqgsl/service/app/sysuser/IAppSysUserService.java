
package com.manhui.gsl.jbqgsl.service.app.sysuser;

import java.util.HashMap;

import com.manhui.gsl.jbqgsl.model.AppUser;

/**
* @Title: AppMeetingService.java
* @Package com.manhui.gsl.jbqgsl.service.app.membermanager
* @Description: TODO(sysUser表service层)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppSysUserService {
	/**
	 * app端根据用户名/手机号登录
	 */
	HashMap<String, Object> querySysUser(AppUser user);



}
