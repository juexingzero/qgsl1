


package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;

/**
* @Title: AppEvaluateFlowingScoreMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(AppEvaluateFlowingScore数据交互)
* @author LiuBin
* @date 2018年8月17日
* @version V1.0 
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppEvaluateFlowingScoreMapper {
	/**
	 * 根据评价方id 主题id 被评价方id 获取当前机构的已评价 未评价
	 */
	List<EvaluateFlowingScore> queryEvaluateFlowingScoreList(
			@Param("institution_id") String institution_id, 
			@Param("institutionList") List<Institution> institutionList,
			@Param("topic_id") String topic_id);

	List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds(@Param("active_institution_id")String active_institution_id,
			@Param("passive_institution_id")String passive_institution_id, @Param("topic_id")String topic_id);

	EvaluateFlowingScore showEvaluateStandard(String standard_id);

	int save(EvaluateFlowingScore evaluateFlowingScore);
	/**
	 * 展示即时评价流水表
	 */
	List<EvaluateFlowingScore> queryEvaluateFlowingScore2(@Param("forthwith_id")String forthwith_id,@Param("standard_id") String standard_id,@Param("detail_id") String detail_id, @Param("active_id")String active_id,
			@Param("passive_id")String passive_id);
	/**
	 * 展示主题流水表
	 */
	List<EvaluateFlowingScore> queryEvaluateFlowingScore(@Param("topic_id")String topic_id,@Param("standard_id") String standard_id,@Param("detail_id") String detail_id, @Param("active_id")String active_id,
			@Param("passive_id")String passive_id);
	/**
	 * 通过被评价机构id获取到即时id以及评价方id
	 */
	List<EvaluateFlowingScore> queryForthwithIdByPassiveId(@Param("passive_id")String passive_id);
	/**
	 * 根据评价方id  被评级方id 主题ID 删除主题评价表对应数据
	 */
	void deleteEvaluateFlowingScore(@Param("active_id")String active_id, @Param("passive_id")String passive_id,@Param("topic_id")String topic_id);

	//List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds(@Param("paramMap")Map<String, Object> paramMap);

	
	
}
