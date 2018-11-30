

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: AppTopicEvaluateStandardMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(主题评价标准表)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTopicEvaluateStandardMapper {

	 TopicEvaluateStandard showTopicEvaluateStandard(@Param("standard_id")String standard_id, @Param("topic_id")String topic_id);
	List<String> queryStandardIds(@Param("topicEvaluateIdsList")List<String> topicEvaluateIdsList);

}
