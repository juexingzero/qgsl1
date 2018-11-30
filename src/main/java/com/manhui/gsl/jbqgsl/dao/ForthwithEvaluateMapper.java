package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
 * @类名称 ForthwithEvaluateMapper.java
 * @类描述 <pre>即时回复模块dao层，主要负责跟数据库的交互</pre>
 * @作者  Jiangxiaosong 
 * @创建时间 2018年8月10日11:29:25
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
@Mapper
public interface ForthwithEvaluateMapper {

	/**
	 * 查询未回复的即时数据
	 */
	List<ForthwithEvaluate> queryUnevaluateList(Map<String, Object> conditions);
	/**
	 * 查询未回复的即时数据总数
	 */
	Integer queryUnevaluateTotal(Map<String, Object> conditions);
	/**
	 * 查询回复的即时数据
	 */
	List<ForthwithEvaluate> queryEvaluateList(Map<String, Object> conditions);
	/**
	 * 查询回复的即时数据总数
	 */
	Integer queryEvaluateTotal(Map<String, Object> conditions);
	/**
	 * 查询即时数据及即时数据详细
	 */
	Map queryEvaluate(@Param("forthwith_id")String forthwith_id,@Param("passive_id")String passive_id,@Param("active_id")String active_id);
	/**
	 * 查询即时数据
	 */
	ForthwithEvaluate queryForthwithEvaluate(String forthwith_id);
	
	/**
	 * 查询即时分数数据
	 */
	List<EvaluateFlowingScore> queryEvaluateFlowingScores(@Param("forthwith_id")String forthwith_id,@Param("passive_id")String passive_id,@Param("active_id")String active_id);
	
	/**
	 * 更新即时评价回复
	 */
	void updateSuggestAnswer(@Param("suggest_id")String suggest_id,@Param("suggest_answer")String suggest_answer);
}
