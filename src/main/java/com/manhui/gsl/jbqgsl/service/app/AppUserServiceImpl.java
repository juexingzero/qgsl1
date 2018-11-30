package com.manhui.gsl.jbqgsl.service.app;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppInstitutionMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
 * @Title: AppUserServiceImpl.java
 * @Package com.manhui.gsl.jbqgsl.service.impl
 * @Description: TODO(app_user实现类)
 * @author LiuBin
 * @date 2018年8月13日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Service
public class AppUserServiceImpl implements IAppUserService {
	@Resource
	private AppUserMapper appUserMapper;
	@Resource
	private AppInstitutionMapper appInstitutionMapper;

	/**
	 * 注册
	 */
	@Override
	public int save(AppUser user) {
		user.setLogin_username(user.getUser_phone());
		user.setUser_name(user.getUser_phone());
		user.setDel_flag( "0" );
		return appUserMapper.save(user);
	}

	/**
	 * 获取用户信息
	 */
	@Override
	public HashMap<String, Object> getByUser(AppUser user) {
		HashMap<String,Object> map = appUserMapper.getByUser(user);
		if(map == null || map.isEmpty()){
			return null;
		}
		//查看登录用户类型,区别机构类型(1：区级部门，2：街镇，3：企业，默认1)以方便APP端双向评价 即时评价按钮的隐现
		if(map !=null&&map.get("institution_id") !=null && !"".equals(String.valueOf(map.get("institution_id")))) {
			String institution_id = String.valueOf(map.get("institution_id"));
			String type = appInstitutionMapper.queryInstitutionType(institution_id);
			map.put("institution_type", type);
		}else {
			map.put("institution_type", "");
		}
		return map;
	}

	/**
	 * 验证注册手机号码不重复
	 */
	@Override
	public int getUserPhone(AppUser user) {
		return appUserMapper.getUserPhonesByUser(user);
	}

	/**
	 * 修改密码
	 */
	@Override
	public int update(AppUser user) {
		
		return appUserMapper.updateUserInfo(user);
	}
	/**
	 * 根据id获取用户信息
	 */
	@Override
	public AppUser queryUserMemberModelById(String user_id) {
		return appUserMapper.queryAppUser(user_id);
	}
	/**
	 * 根据 user_id以及headImg 封装进Appuser中进行头像路径上传头像
	 * 
	 */
	@Override
	public void updateUserHeadPhoto(AppUser user) {
		appUserMapper.updateUserHeadPhoto(user);
		
	}

	@Override
	public AppUser queryUserInfo(ForthwithEvaluate forthwithEvaluate) {
		
		return appUserMapper.queryUserInfo(forthwithEvaluate);
	}

	@Override
	public AppUser queryUser(String institution_id) {
		
		return appUserMapper.queryUser(institution_id);
	}

	/**
	 *  获得用户角色类型，多个角色用逗号隔开
	 * 角色参照字典PeopleEnum.peopleRoleType
	 * @param userId
	 * @return
	 */
	@Override
	public String getUserRoleType(String userId) {
		if(StringUtils.isBlank(userId)){
			throw new RuntimeException("参数错误---userId");
		}
		Map<String,Object> typeMap = appUserMapper.getUserRoleType(userId);
		String userRoleType = "";
		if(typeMap != null && !typeMap.isEmpty()){
			for(Map.Entry<String,Object> e : typeMap.entrySet()){
				if(StringUtils.isNotBlank(e.getValue()+"")){
					userRoleType += e.getValue()+",";
				}
			}
			userRoleType = userRoleType.substring(0,userRoleType.length()-1);
		}
		return userRoleType;
	}

}
