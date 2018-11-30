

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
* @Title: AppTopicPassiveInfoMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(主题被评价方数据交互层)
* @author LiuBin
* @date 2018年8月27日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTopicPassiveInfoMapper {
	/**
	 * 根据主题id查找被评价方机构id
	 */
	List<String> queryInstitutionsInfo(String topic_id);

}
