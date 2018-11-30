
package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: IAppTopicEvaluateService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(app端 双向评价--主题评价service接口层)
* @author LiuBin
* @date 2018年8月16日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppTopicEvaluateService {
	/**
	 * 计算待评价总数 
	 */
	int CountToTopicEvaluateByInstitution_id(String institution_id,String topic_type, String evaluate_state);
	/**
	 * 计算已评价总数
	 */
	int CountFinalTopicEvaluateByInstitution_id(String institution_id,String topic_type, String evaluate_state, String is_evaluate);
	/**
	 * 查询主题表 机构表信息
	 */
	List<Map<String, Object>> queryTopicEvaluateByInstitutionId(String institution_id,String topic_type, String evaluate_state, String is_evaluate);
	/**
	 * 根据机构id判断 机构类型 企业 还是 区级部门还是 街道
	 */
	String queryInstitutionType(String institution_id);
	/**
	 * 根据机构id主题id 获取被评价机构基本信息
	 */
	List<Institution> queryPassiveInstitution(String institution_id, String topic_id);
	/**
	 * 通过topicEvaluateList获取到被评价机构的信息
	 */
	List<Institution> queryInstitution(List<TopicEvaluate> topicEvaluateList);
	/**
	 * 根据机构id获取到主题评价id
	 * @param is_evaluate 
	 */
	List<String> getTopicEvaluateIds(String institution_id, String is_evaluate);
	/**
	 * 根据主题id集合获取到主题对象数据
	 * @param pageSize 
	 * @param pageNo 
	 * @param evaluate_state 
	 */
	PaginationVO<TopicEvaluate> queryTopicEvaluates(List<String> topicEvaluateIdsList, long pageNo, long pageSize, String evaluate_state);
	List<String> getTopicEvaluateIds2(String institution_id, String is_evaluate, List<String> queryIsEffectStandard);
	/**
	 * 按照评价方类型 以及行业进行查找被评机构
	 */
	List<Institution> queryPassiveInstitution2(String institution_id, String topic_id, String institution_type,
			String industry_id);
	

	
}
