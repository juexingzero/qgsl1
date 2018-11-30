
/**
* @Title: EvaluateDetailMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(细则评价dao层)
* @author LiuBin
* @date 2018年8月8日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;

@Mapper
public interface EvaluateDetailMapper {

	/**
	 * 保存方法
	 */
	void save(EvaluateDeatil evaluateDeatil);
	
	/**
	 * 删除方法
	 */
	void delete(@Param("standard_id")String standard_id,@Param("detail_id")String detail_id);
	
	/**
	 * 根据标准ID和参数ID查询详细
	 */
	List<EvaluateDeatil> showEvaluateDeatil(@Param("standard_id")String standard_id, @Param("detail_id")String detail_id, @Param("detail_level")Integer detail_level);
	
	/**
	 * 根据标准ID查询2级详细名称
	 */
	List<String> queryDetailNameByLevel2(String standard_id);
	
	/**
	 * 读取评价详细By联合主键
	 */
	EvaluateDeatil queryEvaluateDeatilByKey(@Param("detail_id")String detail_id,@Param("standard_id")String standard_id);
	
	/**
	 * 更新方法
	 */
	void update(EvaluateDeatil evaluateDeatil);
	
	/**
	 * 查询分数是否超过
	 */
	Integer queryScoreCount(@Param("standard_id")String standard_id,@Param("p_detail_id")String p_detail_id);
	
	/**
	 * 获取2级细则列表
	 */
	List<EvaluateDeatil> queryEvaluateDeatilList(@Param("standard_id")String standard_id,@Param("p_detail_id")String p_detail_id);
}
