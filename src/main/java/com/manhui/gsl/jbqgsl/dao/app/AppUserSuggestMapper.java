


package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
* @Title: AppUserSuggestMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppUserSuggestMapper {

	List<Map<String, Object>> list(UserSuggest model);

	int save(UserSuggest model);

	UserSuggest getUserSuggestDetail(Map<String, Object> conditionMap);
	
}
