
package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingSuggestMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;

/**
* @Title: AppEvaluateFlowingScoreImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(IAppEvaluateFlowingScore实现类)
* @author LiuBin
* @date 2018年8月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Service
public class AppEvaluateFlowingScoreImpl implements IAppEvaluateFlowingScoreService{
	@Resource
	private AppEvaluateFlowingScoreMapper appEvaluateFlowingScoreMapper;
	@Resource
	private AppEvaluateFlowingSuggestMapper appEvaluateFlowingSuggestMapper;
	/**
	 * 查看评价方id 主题id 被评价方id 获取当前机构的已评价 未评价
	 */
	@Override
	public List<EvaluateFlowingScore> getEvaluateFlowingScore(String institution_id, List<Institution> institutionList,
			String topic_id) {
		return appEvaluateFlowingScoreMapper.queryEvaluateFlowingScoreList(institution_id,institutionList,topic_id);
	}
	@Override
	public List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds(String active_institution_id,
			String passive_institution_id, String topic_id) {
		
		return appEvaluateFlowingScoreMapper.queryEvaluateFlowingScoreByIds(active_institution_id,passive_institution_id,topic_id);
	}
	@Override
	public EvaluateFlowingScore showFlowingScoreByIds(Map<String, Object> paramMap) {
		
		return null;
	}
	@Override
	public int save(EvaluateFlowingScore evaluateFlowingScore) {
		evaluateFlowingScore.setCreate_time(DateUtil.getTime());
		return appEvaluateFlowingScoreMapper.save(evaluateFlowingScore);
	}
	/**
	 * 根据评价方id  被评级方id 主题ID 删除主题评价表对应数据
	 */
	@Override
	public void deleteEvaluateFlowingScore(String active_id, String passive_id, String topic_id) {
		appEvaluateFlowingScoreMapper.deleteEvaluateFlowingScore(active_id,passive_id,topic_id);
	}

}
