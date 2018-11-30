package com.manhui.gsl.jbqgsl.dao.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
 * @Title: AppUserMapper.java
 * @Package com.manhui.gsl.jbqgsl.dao
 * @Description: TODO(app_user_info数据持久层)
 * @author LiuBin
 * @date 2018年8月13日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface AppUserMapper {
	/**
	 * app端个人用户注册
	 */
	int save(AppUser user);
	
	/**
	 * 保存到用户信息
	 */
	int saveUser(AppUser user);

	/**
	 * 获取到用户信息
	 */
	HashMap<String, Object> getByUser(AppUser user);

	/**
	 * 验证注册手机号码不重复
	 */
	int getUserPhonesByUser(AppUser user);

	/**
	 * 修改用户密码
	 */
	// int updateUserPassWord(AppUser user);
	/**
	 * 修改个人信息 公开
	 */
	int updateUserInfo(AppUser user);
	/**
	 * 根据用户user_id获取到用户信息
	 */
	AppUser queryAppUser(String user_id);
	/**
	 * 上传修改用户头像
	 */
	void updateUserHeadPhoto(AppUser user);

	List<AppUser> queryInstitutionHeadPhoto(@Param("passiveIdsList")List<String> passiveIdsList);

	AppUser queryUserInfo(ForthwithEvaluate forthwithEvaluate);

	AppUser queryUser(@Param("institution_id")String institution_id);
	/**
	 * 验证手机号码唯一
	 */
	AppUser checkUserPhone(@Param("institution_linkman_phone")String institution_linkman_phone);
	/**
	 * 圈子--根据id获取到该用户手机号码以及所在机构名称和担任的职务
	 */
	AppUser queryUserAndInstitutionInfo(@Param("caller_id")String caller_id);

	/**
	 *  获得用户角色类型，多个角色用逗号隔开
	 * 角色参照字典PeopleEnum.peopleRoleType
	 * @param userId
	 * @return
	 */
    Map<String,Object> getUserRoleType(String userId);
}
