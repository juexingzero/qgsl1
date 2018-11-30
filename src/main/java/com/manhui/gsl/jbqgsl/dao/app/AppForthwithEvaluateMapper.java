

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;

/**
* @Title: AppForthwithEvaluateMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(及时评价数据交互层)
* @author LiuBin
* @date 2018年8月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppForthwithEvaluateMapper {

	List<ForthwithEvaluate> getForthwithEvaluateIds(@Param("institution_id")String institution_id);

	String queryInstitutionType(@Param("institution_id")String institution_id);

	List<Institution> queryInstitution(@Param("passiveInstitutionIdList")List<String> passiveInstitutionIdList);

	String queryStandardId(@Param("standard_belonged")String standard_belonged);

	int save(ForthwithEvaluate fwe);

	List<ForthwithEvaluate> queryForthwithObj(@Param("institution_id")String institution_id,@Param("pageNo") long pageNo,@Param("pageSize") long pageSize);

	Long queryTotal(@Param("institution_id") String institution_id);

	String getMaxTopicNumber();
	/**
	 * 查询即时评价id
	 */
	String queryForthwithId(@Param("active_id")String active_id, @Param("passive_id")String passive_id);
	
	int queryCountForthwith(ForthwithEvaluate fwe);

	List<String> queryPassiveIds(@Param("active_id")String active_id);
	/**
	 * 评价方获取到即时评价的所有已评机构
	 */
	List<ForthwithEvaluate> queryForthwithList(@Param("institution_id")String institution_id, @Param("pageNo")Long pageNo, @Param("pageSize")Long pageSize);

	List<ForthwithEvaluate> queryForthwithList2(@Param("institution_id")String institution_id,  @Param("pageNo")Long pageNo, @Param("pageSize")Long pageSize,
			@Param("institution_type")String institution_type);

	Long queryTotal2(@Param("institution_id")String institution_id, @Param("institution_type")String institution_type);
	
}
