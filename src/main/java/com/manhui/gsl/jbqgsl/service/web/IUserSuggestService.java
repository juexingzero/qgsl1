

package com.manhui.gsl.jbqgsl.service.web;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
* @Title: IUserSuggestService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(意见反馈service)
* @author LiuBin
* @date 2018年9月5日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IUserSuggestService {
	/**
	 * 获取意见反馈数据
	 */
	PageInfo<UserSuggest> getUserSuggestList(Map<String, Object> conditionMap);
	/**
	 * 获取意见反馈详情
	 */
	UserSuggest getUserSuggestDetail(String id);
	/**
	 * 保存回复信息
	 */
	Integer saveUserSuggest(UserSuggest userSuggest);
	/**
	 * 删除意见反馈
	 */
	Integer deleteUserSuggest(String id);

}
