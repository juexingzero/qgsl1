
/**
* @Title: IBiDiStandardService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(后台-双向评价-标准库设置接口 )
* @author LiuBin
* @date 2018年8月7日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;

public interface IBiDiStandardService {
	/**
	 * 查询双向评价
	 */
	List<EvaluateStandard> queryEvaluateStandards(EvaluateStandard evaluStandard);
	/**
	 * 计算总页数
	 */
	Integer queryevaluateStandardsCount(EvaluateStandard evaluStandard);
	/**
	 * 保存
	 */
	void save(EvaluateStandard evaluateStandard);
	/**
	 * 根据 标准分类  标准名称进行条件查询
	 */
	List<Map<String, Object>> queryEvaluateStandardsByCondition(EvaluateStandard evaluateStandard);
	
	/**
	 * 根据ID删除评价标准
	 * @param standard_id
	 * @return
	 */
	JsonResult deleteEvaluateStandards(String standard_id);
	
	/**
	 * 根据ID查询评价标准，详细
	 */
	EvaluateStandard showEvaluateStandard(String standard_id);
	
	/**
	 * 根据ID查询评价标准
	 */
	EvaluateStandard queryEvaluateStandard(String standard_id);
	
	/**
	 * 修改评价标准
	 */
	void update(EvaluateStandard evaluateStandard);
	
	/**
	 * 标准生效
	 */
	void vaildStandard(String standard_id);
	
	
	/**
	 * 标准失效
	 */
	void invaildStandard(String standard_id);
	
	/**
	 * 检测是即时评价的标准评价
	 */
	EvaluateStandard queryEvaluateStandardByBelonged();
	
}
