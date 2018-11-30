
package com.manhui.gsl.jbqgsl.dao.app.shldbz;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
* @Title: CommerceMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.commerce
* @Description: TODO(APP端商会班子)
* @author LiuBin
* @date 2018年11月28日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppLeadershipMapper {
	/**
	 * 审批通过后--将姓名 以及职务保存到商会班子表中
	 */
	void saveLeaderShipInfo(Map<String, Object> dataMap);
	

}
