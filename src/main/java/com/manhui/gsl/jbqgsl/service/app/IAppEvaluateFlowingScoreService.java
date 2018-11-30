
package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;

/**
* @Title: AppEvaluateFlowingScore.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(评价流水评分表Service接口)
* @author LiuBin
* @date 2018年8月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppEvaluateFlowingScoreService {
	
	List<EvaluateFlowingScore> getEvaluateFlowingScore(String institution_id, List<Institution> institutionList,
			String topic_id);

	List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds(String active_institution_id,
			String passive_institution_id, String topic_id);
	/**
	 * 根据机构id 主题id 被评价方id来获取流水表
	 */
	//List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds(Map<String, Object> paramMap);
	/**
	 * 展示
	 */
	EvaluateFlowingScore showFlowingScoreByIds(Map<String, Object> paramMap);

	int save(EvaluateFlowingScore evaluateFlowingScore);
/**
 * 根据评价方id  被评级方id 主题ID 删除主题评价表对应数据
 */
	void deleteEvaluateFlowingScore(String active_id, String passive_id, String topic_id);
	
}
