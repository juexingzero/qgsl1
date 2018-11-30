
/**
* @Title: AppTopicEvaluateStandardServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingSuggestMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicEvaluateStandardDetailMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicEvaluateStandardMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;

 
@Service
public class AppTopicEvaluateStandardServiceImpl implements IAppTopicEvaluateStandardService {
	@Resource
	private AppTopicEvaluateStandardMapper appTopicEvaluateStandardMapper;
	@Resource
	private AppTopicEvaluateStandardDetailMapper appTopicEvaluateStandardDetailMapper;
	@Resource
	private AppEvaluateFlowingScoreMapper appEvaluateFlowingScoreMapper;
	@Resource
	private AppEvaluateFlowingSuggestMapper appEvaluateFlowingSuggestMapper;
	
	
	@Override 
	public TopicEvaluateStandard showTopicEvaluateStandard(Map<String, Object> paramMap) {
		String standard_id =String.valueOf(paramMap.get("standardId"));
		String topic_id = String.valueOf(paramMap.get("topic_id"));
		String active_id =String.valueOf(paramMap.get("active_id"));
		String passive_id = String.valueOf(paramMap.get("passive_id"));
		//获取主题评价标准表
		TopicEvaluateStandard topEvaluateStandard = appTopicEvaluateStandardMapper.showTopicEvaluateStandard(standard_id,topic_id);
		
		//主题评价标准详情表
		List<TopicEvaluateStandardDetail> paramData = appTopicEvaluateStandardDetailMapper.showEvaluateDeatil(topic_id, standard_id, null, 1);
		double real_score = 0.0;
		for (TopicEvaluateStandardDetail tsd1 : paramData) {
			//根据主题id 机构id,主题详情id,评价方id,被评价方id
			List<EvaluateFlowingScore> efsList1 = appEvaluateFlowingScoreMapper.queryEvaluateFlowingScore(topic_id,standard_id,tsd1.getDetail_id(),active_id,passive_id);
			Double total = 0.0;
			for (EvaluateFlowingScore t : efsList1) {
				if(tsd1.getDetail_id().equals(t.getTopic_standard_detail_id())) {
					total+=t.getReal_score();
				}
				tsd1.setDetail_real_score(total);
				real_score+=total;
				topEvaluateStandard.setReal_score(real_score);
			}
		}
		for(TopicEvaluateStandardDetail param : paramData){
			List<TopicEvaluateStandardDetail> ed = appTopicEvaluateStandardDetailMapper.showEvaluateDeatil(topic_id, null, param.getDetail_id(), 2);
			for (TopicEvaluateStandardDetail tsd2 : ed) {
				List<EvaluateFlowingScore> efsList2 = appEvaluateFlowingScoreMapper.queryEvaluateFlowingScore(topic_id,standard_id,tsd2.getDetail_id(),active_id,passive_id);
				Double total = 0.0;
				for (EvaluateFlowingScore t2 : efsList2) {
					if(tsd2.getDetail_id().equals(t2.getTopic_standard_detail_id())) {
						total+=t2.getReal_score();
					}
				}
				tsd2.setDetail_real_score(total);
			}
			param.setEvaluateDetailChildList(ed);
		}
		
		
		//将评价意见数据放进去
		EvaluateFlowingSuggest efs =  appEvaluateFlowingSuggestMapper.queryContentAndSuggestByIds(paramMap);
		if(efs !=null) {
			topEvaluateStandard.setWork_content(efs.getWork_content());
			topEvaluateStandard.setSuggest_initiate(efs.getSuggest_initiate());
		}else {
			topEvaluateStandard.setWork_content("");
			topEvaluateStandard.setSuggest_initiate("");
		}
		//将子节点数据放进去
		topEvaluateStandard.setEvaluateDetailList(paramData);
		
		return topEvaluateStandard;
	}

	@Override
	public List<String> queryStandardIds(List<String> topicEvaluateIdsList) {
		return appTopicEvaluateStandardMapper.queryStandardIds(topicEvaluateIdsList);
	}
	
}
