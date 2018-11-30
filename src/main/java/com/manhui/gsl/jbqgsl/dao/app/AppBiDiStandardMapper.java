
package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateStandard;

/**
* @Title: AppBiDiStandardMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(app端标准评价数据交互层)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppBiDiStandardMapper {

	EvaluateStandard showEvaluateStandard(String standardId);

	List<String> appBiDiStandardMapper(@Param("standardIds")List<String> standardIds);
	
	List<String> queryIsEffectStandard(@Param("standardIds")List<String> standardIds);

}
