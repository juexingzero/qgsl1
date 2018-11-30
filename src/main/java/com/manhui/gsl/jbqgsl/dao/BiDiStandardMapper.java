
/**
* @Title: BiDiStandardMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(后台-双向评价-标准库设置dao层)
* @author LiuBin
* @date 2018年8月7日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;

@Mapper
public interface BiDiStandardMapper {
	/**
	 * 查询评价
	 */
	List<EvaluateStandard> queryEvaluate(EvaluateStandard evaluStandard);
	/**
	 * 查询评价总数
	 */
	Integer queryEvaluateCount(EvaluateStandard evaluStandard);
	/**
	 * 根据标准分类 标准名称进行条件查询
	 */
	List<Map<String, Object>> queryEvaluateByCondition(EvaluateStandard evaluateStandard);
	
	
	/**
	 * 根据ID删除评价标准
	 */
	void deleteEvaluateStandards(String standard_id);
	
	/**
	 * 保存标准
	 */
	void saveEvaluateStandards(EvaluateStandard evaluateStandard);
	
	/**
	 * 根据ID查询评价标准
	 */
	EvaluateStandard showEvaluateStandard(String standard_id);
	
	/**
	 * 更新标准评价
	 */
	void updateEvaluateStandards(EvaluateStandard evaluateStandard);
	
	/**
	 * 标准生效
	 */
	void vaildStandard(@Param("standard_id")String standard_id);
	
	/**
	 * 标准生效
	 */
	void invaildStandard(@Param("standard_id")String standard_id);
	
	/**
	 * 检测是即时评价的标准评价
	 */
	EvaluateStandard queryEvaluateStandardByBelonged();
}
