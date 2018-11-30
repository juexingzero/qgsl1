

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.Dept;

/**
* @Title: AppPoliciesAndRegulationsMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(政策法规数据交互层)
* @author LiuBin
* @date 2018年9月6日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppPoliciesAndRegulationsMapper {
	/**
	 * 查询江北区所有的2级部门
	 */
	List<Dept> queryOutDeptList();
	/**
	 * 模糊查询部门
	 */
	List<Dept> queryOutDepts(@Param("dept_name")String dept_name);

}
