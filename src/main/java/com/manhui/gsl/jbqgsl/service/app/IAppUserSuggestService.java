


package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.common.util.PageObject;
import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
* @Title: IAppUserSuggestService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/	
public interface IAppUserSuggestService {

	int save(UserSuggest model);

	List<Map<String, Object>> list(UserSuggest model);

	UserSuggest getUserSuggestDetail(Map<String, Object> conditionMap);

}
