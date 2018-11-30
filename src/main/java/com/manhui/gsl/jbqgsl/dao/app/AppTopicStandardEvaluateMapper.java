
package com.manhui.gsl.jbqgsl.dao.app;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @Title: AppTopicStandsrdEvaluateMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(主题标准评价表数据交互层)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTopicStandardEvaluateMapper {

	String queryStandardId(@Param("topic_id") String topic_id);
	
}
