
package com.manhui.gsl.jbqgsl.service.app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicEvaluateMapper;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: AppTopicEvaluateServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(app端 双向评价--主题评价service接口层实现层)
* @author LiuBin
* @date 2018年8月16日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/


@Service
public class AppTopicEvaluateServiceImpl implements IAppTopicEvaluateService {
	@Resource
	private AppTopicEvaluateMapper appTopicEvaluateMapper;
	@Override
	public String queryInstitutionType(String institution_id) {
		return appTopicEvaluateMapper.queryInstitutionType(institution_id);
	}
	@Override
	public int CountToTopicEvaluateByInstitution_id(String institution_id, String topic_type, String evaluate_state) {
		
		return appTopicEvaluateMapper.CountToTopicEvaluateByInstitution_id(institution_id, topic_type,evaluate_state);
	}
	@Override
	public int CountFinalTopicEvaluateByInstitution_id(String institution_id, String topic_type, String evaluate_state,
			String is_evaluate) {
		
		return appTopicEvaluateMapper.CountFinalTopicEvaluateByInstitution_id(institution_id, topic_type,evaluate_state,is_evaluate);
	}
	@Override
	public List<Map<String, Object>> queryTopicEvaluateByInstitutionId(String institution_id, String topic_type,
			String evaluate_state, String is_evaluate) {
		
		return appTopicEvaluateMapper.queryTopicEvaluateByInstitutionId(institution_id, topic_type,evaluate_state,is_evaluate);
	}
	/**
	 * 获取被评价机构简介
	 */
	@Override
	public List<Institution> queryPassiveInstitution(String institution_id, String topic_id) {
		
		return appTopicEvaluateMapper.queryPassiveInstitution(institution_id,topic_id);
	}
	/**
	 * 通过topic_id获取到被评价机构信息
	 */
	@Override
	public List<Institution> queryInstitution(List<TopicEvaluate> topicEvaluateList) {
		
		return appTopicEvaluateMapper.queryInstitution(topicEvaluateList);
	}


	
	/**
	 * 根据主题id来获取主题对象信息
	 */
	@Override
	public PaginationVO<TopicEvaluate> queryTopicEvaluates(List<String> topicEvaluateIdsList,long pageNo,long pageSize,String evaluate_state) {
		 PaginationVO<TopicEvaluate> pg= new PaginationVO<>();
		 pg.setTotal(appTopicEvaluateMapper.queryTopicTotal(topicEvaluateIdsList,evaluate_state));
		 pageNo = (pageNo-1)*pageSize;
		// List<TopicEvaluate> queryTopicEvaluates = appTopicEvaluateMapper.queryTopicEvaluates(topicEvaluateIdsList,pageNo,pageSize);
		 List<TopicEvaluate> queryTopicEvaluates = appTopicEvaluateMapper.queryTopicEvaluates(topicEvaluateIdsList,pageNo,pageSize,evaluate_state);
		 pg.setDataList(queryTopicEvaluates);
	
		 return pg;
	}
	/**
	 * 根据机构id获取到主题id
	 */
	@Override
	public List<String> getTopicEvaluateIds(String institution_id,String is_evaluate) {
		return appTopicEvaluateMapper.queryTopicEvaluateIds(institution_id,is_evaluate);
	}
	/**
	 * 生效标准下的机构id以及主题id
	 */
	@Override
	public List<String> getTopicEvaluateIds2(String institution_id, String is_evaluate, List<String> queryIsEffectStandard) {
		
		return appTopicEvaluateMapper.getTopicEvaluateIds2(institution_id,is_evaluate,queryIsEffectStandard);
	}
	/**
	 * 按照评价方类型 以及行业进行查找被评机构
	 */
	@Override
	public List<Institution> queryPassiveInstitution2(String institution_id, String topic_id, String institution_type,
			String industry_id) {
		
		return appTopicEvaluateMapper.queryPassiveInstitution2(institution_id, topic_id,institution_type,industry_id);
	}
}
