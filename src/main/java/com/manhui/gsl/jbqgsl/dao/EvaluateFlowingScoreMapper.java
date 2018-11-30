package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScoreDetailsUtil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;

/**
 * @类名称 IndustryMapper.java
 * @类描述
 * 
 *      <pre>
 * 行业数据模块dao层，主要负责跟数据库的交互
 *      </pre>
 * 
 * @作者 Jiangxiaosong
 * @创建时间 2018年8月8日 下午3:33:52
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年8月8日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface EvaluateFlowingScoreMapper {
    /**
     * 新增数据
     * 
     * @param score
     * @return
     */
    String save( EvaluateFlowingScore score );

    /**
     * 根据id 修改数据
     * 
     * @param score
     * @return
     */
    int updateById( EvaluateFlowingScore score );

    /**
     * 根据评价方id分组查询 数据
     * 
     * @param topic_id
     * @param passive_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> getEvaluateFlowingScoreGroupList(
            @Param( "topic_id" ) String topic_id,
            @Param( "passive_id" ) String passive_id,
            @Param( "pageNo" ) Integer pageNo,
            @Param( "pageSize" ) Integer pageSize );

    /**
     * 根据主题id 和被评价方id 查询 评价流水合并总数
     * 
     * @param topic_id
     * @return
     */
    Integer getEvaluateFlowingScoreGroupTotal(
            @Param( "topic_id" ) String topic_id,
            @Param( "passive_id" ) String passive_id );

    /**
     * 根据id 查询数据
     * 
     * @param score_id
     * @return
     */
    EvaluateFlowingScore getevaluateFlowingScoreById( String score_id );

    /**
     * 获取评价流水意见
     * 
     * @param topic_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    EvaluateFlowingSuggest getEvaluateFlowingSuggest(
            @Param( "topic_id" ) String topic_id,
            @Param( "topic_standard_id" ) String topic_standard_id,
            @Param( "passive_id" ) String passive_id,
            @Param( "actice_id" ) String actice_id );

    /**
     * 获取标准打分详情
     *
     * @param topic_id
     * @param topic_standard_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    List<EvaluateFlowingScore> getStandradScoreDetail(
            @Param( "topic_id" ) String topic_id,
            @Param( "topic_standard_id" ) String topic_standard_id,
            @Param( "passive_id" ) String passive_id,
            @Param( "actice_id" ) String actice_id );

    /**
     * 获取标准打分详情，用于excel导出
     *
     * @param topic_id
     * @param topic_standard_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    List<Map<String, Object>> getStandradScoreDetailForExport(
            @Param( "topic_id" ) String topic_id,
            @Param( "topic_standard_id" ) String topic_standard_id,
            @Param( "passive_id" ) String passive_id,
            @Param( "actice_id" ) String actice_id );

    /**
     * 查询详情数据
     * 
     * @param score
     * @return
     */
    List<EvaluateFlowingScoreDetailsUtil> getEvaluateFlowingScoreDetails( EvaluateFlowingScore score );
}
