
package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: AppTopicEvaluateMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(app端-双向评价-主题评价数据持久层)
* @author LiuBin
* @date 2018年8月16日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppTopicEvaluateMapper {
	/**
	 * 计算待评价总数
	 */
	int CountToTopicEvaluateByInstitution_id(String institution_id,String topic_type, String evaluate_state);
	/**
	 * 计算已评价总数
	 */
	int CountFinalTopicEvaluateByInstitution_id(String institution_id,String topic_type, String evaluate_state, String is_evaluate);
	/**
	 * 获取到主题 评价方信息
	 */
	List<Map<String, Object>> queryTopicEvaluateByInstitutionId(String institution_id,String topic_type, String evaluate_state, String is_evaluate);
	/**
	 * 根据机构id判断机构类型
	 */
	String queryInstitutionType(String institution_id);
	/**
	 * 获取到被评价方简介
	 */
	List<Institution> queryPassiveInstitution(@Param("institution_id")String institution_id,@Param("topic_id") String topic_id);
	/**
	 * 通过topicEvaluateList获取到被评价结构信息
	 */
	List<Institution> queryInstitution(@Param("topicEvaluateList") List<TopicEvaluate> topicEvaluateList);
	
	/**
	 * 根据主题id来获取到主题对象
	 * @param pageSize 
	 * @param pageNo 
	 * @param evaluate_state 
	 */
	List<TopicEvaluate> queryTopicEvaluates(@Param("topicEvaluateIdsList") List<String> topicEvaluateIdsList, @Param("pageNo")long pageNo,@Param("pageSize") long pageSize, @Param("evaluate_state")String evaluate_state);
	/**
	 * 总共多少条
	 * @param evaluate_state 
	 */
	Long queryTopicTotal(@Param("topicEvaluateIdsList") List<String> topicEvaluateIdsList,@Param("evaluate_state") String evaluate_state);
	
	List<EvaluateFlowingScore> showEvaluateDeatil(Map<String, Object> paramMap, Object object, int i);
	/**
	 * 通过机构id获取到主题id
	 * @param is_evaluate 
	 */
	List<String> queryTopicEvaluateIds(@Param("institution_id") String institution_id, @Param("is_evaluate")String is_evaluate);
	/**
	 * 生效的标准id下的主题id
	 */
	List<String> getTopicEvaluateIds2(@Param("institution_id")String institution_id, @Param("is_evaluate")String is_evaluate,@Param("standardIds") List<String> queryIsEffectStandard);
	/**
	 * 按照评价方类型 以及行业进行查找被评机构
	 */
	List<Institution> queryPassiveInstitution2(@Param("institution_id")String institution_id, @Param("topic_id")String topic_id, @Param("institution_type")String institution_type,
			@Param("industry_id")String industry_id);

}
