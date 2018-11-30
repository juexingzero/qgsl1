package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.manhui.gsl.jbqgsl.model.*;

/**
 * @类名称 ITopicEvaluateService.java
 * @类描述 主题评价结果
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月15日 下午2:03:17
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月15日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ITopicEvaluateResultService {
    List<TopicEvaluate> getTopicEvaluateResultList( TopicEvaluate topic, String user_type, String mobile_no );

    Integer getTopicEvaluateResultTotal( TopicEvaluate topic );

    /**
     * 查看被评价组织详情
     * 
     * @param topic_id
     * @return
     */
    Map<String, Object> getTopicEvaluateOrgDetails( String topic_id );

    /**
     * 根据主题id 查询数据
     * 
     * @param info
     * @return
     */
    List<TopicPassiveInfo> getTopicPassiveInfoListTopic_id( TopicPassiveInfo info, String user_type, String mobile_no );

    /**
     * 根据主题id、机构id 查询数据
     * 
     * @param topic_id
     * @param institution_id
     * @return
     */
    TopicPassiveInfo getTopicPassiveInfoByTopicInstitutionId(String topic_id, String institution_id);

    /**
     * 根据主题id查询 被评价方总数
     * 
     * @param info
     * @return
     */
    Integer getTopicPassiveInfoTotalByTopic_id( TopicPassiveInfo info );

    /**
     * 根据主题id 和被评价方id 查询 评价流水合并列表
     * 
     * @param topic_id
     * @return
     */
    List<Map<String, Object>> getEvaluateFlowingScoreGroupList( String topic_id, String passive_id, Integer pageNo, Integer pageSize, String user_type );

    /**
     * 根据主题id 和被评价方id 查询 评价流水合并总数
     * 
     * @param topic_id
     * @return
     */
    Integer getEvaluateFlowingScoreGroupTotal( String topic_id, String passive_id );

    /**
     * 查询 评价分数详情
     * 
     * @param score
     * @return
     */
    List<EvaluateFlowingScoreDetailsUtil> getEvaluateFlowingScoreDetails( EvaluateFlowingScore score );

    /**
     * 获取评价流水意见
     * 
     * @param topic_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    EvaluateFlowingSuggest getEvaluateFlowingSuggest( String topic_id, String topic_standard_id, String passive_id, String actice_id );

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
            String topic_id,
            String topic_standard_id,
            String passive_id,
            String actice_id );

    /**
     * 导出主题评价结果
     * 
     * @param topic_ids
     * @return
     */
    String exportTopicEvaluateResult( HttpServletResponse response, String topic_ids, String user_type, String mobile_no );
}
