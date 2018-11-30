
package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;

/**
* @Title: AppEvaluateDetailMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppEvaluateDetailMapper {

	/**
	 * 根据标准ID和参数ID查询详细
	 */
	List<EvaluateDeatil> showEvaluateDeatil(@Param("standard_id")String standard_id, @Param("detail_id")String detail_id, @Param("detail_level")Integer detail_level);

}
