

package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
* @Title: UserSuggestMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(意见反馈数据交互层)
* @author LiuBin
* @date 2018年9月5日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface UserSuggestMapper {

	List<UserSuggest> getUserSuggestList(Map<String, Object> conditionMap);

	UserSuggest getUserSuggestMapperDetail(@Param("id")String id);

	Integer updateUserSuggest(UserSuggest userSuggest);

	Integer deleteUserSuggest(String id);

}
