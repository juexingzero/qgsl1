package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluateDetail;

/**
 * @类名称 IForthwithEvaluateService.java
 * @类描述 <pre>即时评价模块service层接口</pre>
 * @作者  Jiangxiaosong 
 * @创建时间 2018年8月10日11:10:22
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年8月10日                创建
 *     ----------------------------------------------
 * </pre>
 */
public interface IForthwithEvaluateService {
	
	/**
	 * 查询未评价列表
	 */
	List<ForthwithEvaluate> queryUnevaluateList(String evaluate_active_name,Integer pageIndex, Integer pageSize);
	
	/**
	 * 查询未评价总数
	 */
	Integer queryUnevaluateTotal(String evaluate_active_name,Integer pageIndex, Integer pageSize);
	
	/**
	 * 查询已评价列表
	 */
	List<ForthwithEvaluate> queryEvaluateList(String evaluate_active_name,Integer pageIndex, Integer pageSize);
	
	/**
	 * 查询已评价总数
	 */
	Integer queryEvaluateTotal(String evaluate_active_name,Integer pageIndex, Integer pageSize);
	
	/**
	 * 根据即时评价ID查询
	 */
	Map queryEvaluate(String forthwith_id,String passive_id,String active_id);
	
	/**
	 * 根据即时ID查询即时信息
	 */
	ForthwithEvaluate queryForthwithEvaluate(String forthwith_id);
	
	/**
	 * 查询即时评价细则数据
	 */
	List<EvaluateFlowingScore> queryScoresDetail(String forthwith_id,String passive_id,String active_id);
	
	/**
	 * 更新即时评价回复
	 */
	void updateSuggestAnswer(String suggest_id,String suggest_answer);
}
