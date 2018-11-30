package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import com.manhui.gsl.jbqgsl.model.TopicActiveInfo;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;

/**
 * @类名称 ITopicEvaluateService.java
 * @类描述 主题评价管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午2:31:17
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ITopicEvaluateService {
    List<TopicEvaluate> getTopicEvaluateList( TopicEvaluate topic, String user_type, String mobile_no );

    Integer getTopicEvaluateTotal( TopicEvaluate topic );

    List<Map<String, Object>> getPassiveListForCheck( String institution_id, String topic_type );

    Integer saveTopicEvaluate(
            TopicEvaluate topic,
            TopicEvaluateStandard standard,
            List<TopicPassiveInfo> passiveList,
            List<TopicActiveInfo> activeList );

    Integer updateTopicEvaluate( TopicEvaluate topic );

    Map<String, Object> getTopicEvaluateDetailData( String topic_id );

    Integer checkAndUpdateEvaluateState( String currentDate );
}
