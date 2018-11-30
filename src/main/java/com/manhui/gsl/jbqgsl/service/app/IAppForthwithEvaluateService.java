

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;

/**
* @Title: IAppForthwithEvaluateService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(即时评价模块service层接口)
* @author LiuBin
* @date 2018年8月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public interface IAppForthwithEvaluateService {
	
	List<ForthwithEvaluate> getForthwithEvaluateIds(String institution_id);

	String queryInstitutionType(String institution_id);

	List<Institution> queryInstitution(List<String> passiveInstitutionIdList);

	String queryStandardId(String standard_belonged);
	
	/**
	 * 即时评价流水以及建议
	 */
	EvaluateStandard showTopicEvaluateStandard(Map<String, Object> paramMap);
	/**
	 * 根据被评价机构id查询到该账户下面所有的即时id
	 */
	List<EvaluateFlowingScore> queryForthwithFlowingScore(String passive_id);
	/**
	 * 保存即时评价
	 */
	int save(ForthwithEvaluate fwe);
	/**
	 * 根据机构id获取到即时评价数据
	 * @param pageSize 
	 * @param pageNo 
	 */
	PaginationVO<ForthwithEvaluate> queryForthwithObj(String institution_id, long pageNo, long pageSize);
	/**
	 * 获取最大的编号
	 */
	String getMaxTopicNumber();
	/**
	 * 根据评价方被评价方获取到即时id
	 */
	String queryForthwithId(Map<String, Object> paramMap);
	/**
	 * 计算即时ID数量
	 */
	int queryCountForthwith(ForthwithEvaluate fwe);
	/**
	 * 根据机构id 政府部门名称查询未被评价的政府部门机构信息
	 */
	List<Institution> queryPassiveInstitutions(Map<String, Object> paramMap);
	/**
	 * 根据评价方机构id获取到被评价放的id
	 */
	List<String> queryPassiveIds(String institution_id);
	/**
	 * 展示即时评价打分意见结构
	 */
	EvaluateStandard showForthwithEvaluateStandard(Map<String, Object> paramMap);
	/**
	 * 根据机构id获取到即时评价历史机构
	 */
	PaginationVO<ForthwithEvaluate> queryForthwithList(String institution_id, Long pageNo, Long pageSize);

	PaginationVO<ForthwithEvaluate> queryForthwithList2(String institution_id, Long pageNo, Long pageSize,
			String institution_type);


	

}
