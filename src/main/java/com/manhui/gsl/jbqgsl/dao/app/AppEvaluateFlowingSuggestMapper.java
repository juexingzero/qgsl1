

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
* @Title: AppEvaluateFlowingSuggestMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppEvaluateFlowingSuggestMapper {

	int save(EvaluateFlowingSuggest evaluateFlowingSuggest);
	/**
	 * 根据主题id 被评价方id 以及评价方id获取到建议内容
	 */
	EvaluateFlowingSuggest queryContentAndSuggestByIds(Map<String, Object> paramMap);
	
	int queryPassiveNum(EvaluateFlowingSuggest evaluateFlowingSuggest);
	/**
	 * 根据机构id获取到流水内容所有信息
	 */
	List<EvaluateFlowingSuggest> querySuggest(@Param("institution_id")String institution_id);
	/**
	 * 搜索 根据主题ID 机构ID 完成 未完成状态类查找被评价机构信息
	 */
	List<String> queryPassiveInstitutions(@Param("topic_id")String topic_id, 
											@Param("active_id")String active_id);
	
	int querySuggestByIds(EvaluateFlowingSuggest evaluateFlowingSuggest);
	/**
	 * 即时评价评价
	 */
	EvaluateFlowingSuggest queryContentAndSuggestByIds2(Map<String, Object> paramMap);
	/**
	 * 插入流水分数表钱保证流水表中 意见表中没有该主题 该评价方 被平方的的id保证数据唯一性
	 */
	int countRecoredsByIds(EvaluateFlowingScore evaluateFlowingScore);
	
	EvaluateFlowingSuggest query(ForthwithEvaluate forthwithEvaluate);
	/**
	 * 根据主题ID 评价方ID 被评价方ID 查找流水意见表
	 */
	EvaluateFlowingSuggest queryTopicEvaluateSuggestByIds(@Param("active_id")String active_id,@Param("passive_id")String passive_id, @Param("topic_id")String topic_id);
	/**
	 * 根据主题ID 评价方ID 被评价方ID 删除流水意见表
	 */
	void deleteEvaluateFlowingSuggest(@Param("active_id")String active_id, @Param("passive_id")String passive_id, @Param("topic_id")String topic_id);
	
	
}
