
package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: IAppTopicEvaluateStandard.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(app端主题评价标准service)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppTopicEvaluateStandardService {

	TopicEvaluateStandard showTopicEvaluateStandard(Map<String, Object> paramMap);
	List<String> queryStandardIds(List<String> topicEvaluateIdsList);

}
