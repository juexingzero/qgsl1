

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;

/**
* @Title: IAppEvaluateFlowingSuggestService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(流水建议内容service)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppEvaluateFlowingSuggestService {

	int save(EvaluateFlowingSuggest evaluateFlowingSuggest);
	/**
	 * 根据机构id
	 */
	String querySuggestIsAnswer(String passive_id);
	/**
	 * 根据机构查询意见内容表
	 */
	List<EvaluateFlowingSuggest> querySuggest(String institution_id);
	/**
	 * 根据机构id 主题id is_alreadyEvaluate(0:未完成 1:已完成 )查询流水建议表中的被评价方信息
	 */
	List<Institution> queryPassiveInstitutions(Map<String, Object> paramMap);
	/**
	 * 根据即时评价中的数据获取到流水意见表中的对应数据
	 */
	EvaluateFlowingSuggest queryIsSuggest(ForthwithEvaluate forthwithEvaluate);
	/**
	 * 主题评价搜索版本2.0
	 */
	List<Institution> queryPassiveInstitutions2(Map<String, Object> paramMap);
/**
 * 根据主题ID 评价方ID 被评价方ID 查找流水意见表
 */
	EvaluateFlowingSuggest queryTopicEvaluateSuggestByIds(String active_id, String passive_id, String topic_id);
/**
 * 根据主题ID 评价方ID 被评价方ID 删除流水意见表
 */
	void deleteEvaluateFlowingSuggest(String active_id, String passive_id, String topic_id);

}
