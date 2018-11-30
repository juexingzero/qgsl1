


package com.manhui.gsl.jbqgsl.dao.app.commerce;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
/**
* @Title: CommerceMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.commerce
* @Description: TODO(商会管理数据交互层)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppCommerceMapper {
	/**
	 * 查找一级商会
	 */
	List<Commerce> queryCommerceList();
	/**
	 * 查找二级商会
	 */
	List<Commerce> querySecondCommerceList(@Param("id")String id);

}
