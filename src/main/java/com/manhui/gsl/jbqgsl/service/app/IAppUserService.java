package com.manhui.gsl.jbqgsl.service.app;

import java.util.HashMap;
import java.util.Map;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
 * @Title: IAppUserService.java
 * @Package com.manhui.gsl.jbqgsl.service
 * @Description: TODO(app_userService接口)
 * @author LiuBin
 * @date 2018年8月13日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public interface IAppUserService {
	int save(AppUser user);

	HashMap<String, Object> getByUser(AppUser user);

	int getUserPhone(AppUser user);

	int update(AppUser user);
	
	AppUser queryUserMemberModelById(String user_id);

	void updateUserHeadPhoto(AppUser user);

	AppUser queryUserInfo(ForthwithEvaluate forthwithEvaluate);

	AppUser queryUser(String institution_id);

	/**
	 * 获得用户角色类型，多个角色用逗号隔开
	 * 角色参照字典PeopleEnum.peopleRoleType
	 * @param userId
	 * @return
	 */
	String getUserRoleType(String userId);
}
