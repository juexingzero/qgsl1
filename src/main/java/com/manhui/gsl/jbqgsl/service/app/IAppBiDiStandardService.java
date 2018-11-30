

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.EvaluateStandard;

/**
* @Title: IAppBiDiStandardService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(app端评价标准service)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppBiDiStandardService {
	/**
	 * 根据评价标准id来获取评价对象
	 */
	EvaluateStandard showEvaluateStandard(String standardId);
	/**
	 * 根据标准id获取生效的id
	 */
	List<String> queryIsEffectStandard(List<String> standardIds);

}
