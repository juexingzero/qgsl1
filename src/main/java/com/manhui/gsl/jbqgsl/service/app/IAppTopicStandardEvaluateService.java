
package com.manhui.gsl.jbqgsl.service.app;

/**
* @Title: IAppTopicStandardEvaluate.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(app端主题标准评价表)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/


public interface IAppTopicStandardEvaluateService {
	/**
	 * 根据主题id来查找标准id
	 */
	String queryEvaluateStandardId(String topic_id);

}
