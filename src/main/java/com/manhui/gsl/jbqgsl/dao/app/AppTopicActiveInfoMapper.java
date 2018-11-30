

package com.manhui.gsl.jbqgsl.dao.app;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;

/**
* @Title: AppTopicActiveInfoMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(主题评价信息数据交互层)
* @author LiuBin
* @date 2018年8月24日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTopicActiveInfoMapper {
	/**
	 * 改变该主题下的完成状态
	 * @param evaluateFlowingSuggest 
	 */

	int updateState(@Param("actice_id")String actice_id,@Param("topic_id") String topic_id,@Param("evaluate_time")String evaluate_time);
	
}
