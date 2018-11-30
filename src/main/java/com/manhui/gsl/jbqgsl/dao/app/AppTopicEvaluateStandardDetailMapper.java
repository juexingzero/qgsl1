

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;

/**
* @Title: AppTopicEvaluateStandardDetailMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTopicEvaluateStandardDetailMapper {

	List<TopicEvaluateStandardDetail> showEvaluateDeatil(@Param("topic_id")String topic_id, @Param("standard_id")String standard_id,@Param("detail_id")String detail_id, @Param("detail_level")int detail_level);

}
