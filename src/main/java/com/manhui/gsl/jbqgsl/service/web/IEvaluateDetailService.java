
/**
* @Title: IEvaluateDetail.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(评价标准细则接口)
* @author LiuBin
* @date 2018年8月8日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;

public interface IEvaluateDetailService {
	
	/**
	 * 保存方法
	 */
	void save(EvaluateDeatil evaluateDeatil);
	
	/**
	 * 更新方法
	 */
	void update(EvaluateDeatil evaluateDeatil);
	
	/**
	 * 删除方法
	 */
	void delete(String standard_id,String detail_id);
	
	/**
	 * 根据标准ID查询2级详细名称
	 */
	List<String> queryDetailNameByLevel2(String standard_id);
	
	/**
	 * 读取评价详细By联合主键
	 */
	EvaluateDeatil queryEvaluateDeatilByKey(String detail_id,String standard_id);
	
	/**
	 * 读取评价详细
	 */
	List<EvaluateDeatil> queryEvaluateDeatil(String standard_id);
	
	/**
	 * 查询分数是否超过
	 */
	Integer queryScoreCount(String standard_id,String p_detail_id);
	
	/**
	 * 获取2级细则列表
	 */
	List<EvaluateDeatil> queryEvaluateDeatilList(String standard_id,String p_detail_id);
}
