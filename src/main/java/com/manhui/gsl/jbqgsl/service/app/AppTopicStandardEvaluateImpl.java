

package com.manhui.gsl.jbqgsl.service.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicStandardEvaluateMapper;

/**
* @Title: AppTopicStandardEvaluateImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(app端主题标准评价service实现层)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Service
public class AppTopicStandardEvaluateImpl implements IAppTopicStandardEvaluateService {
	@Resource
	private AppTopicStandardEvaluateMapper appTopicStandardEvaluateMapper;

	@Override
	public String queryEvaluateStandardId(String topic_id) {
		
		return appTopicStandardEvaluateMapper.queryStandardId(topic_id);
	}
}
