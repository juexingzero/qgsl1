package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;

/**
 * @类名称 TopicEvaluateMapper.java
 * @类描述 主题评价结果
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月15日 下午2:07:27
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月15日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface TopicEvaluateResultMapper {
    /**
     * 获取主题评价列表
     *
     * @param topic
     * @return
     */
    List<TopicEvaluate> getTopicEvaluateResultList( TopicEvaluate topic );

    /**
     * 获取主题评价总数
     *
     * @param topic
     * @return
     */
    Integer getTopicEvaluateResultTotal( TopicEvaluate topic );

    /**
     * 获取跟被评价方相关的主题ID通过联系人电话
     * 
     * @param mobile_no
     * @return
     */
    String getPassivesByLinkmanPhone( @Param( "mobile_no" ) String mobile_no );

    /**
     * 根据主题id获取对应的被评价方名称集合
     *
     * @param topic_id
     * @return
     */
    String getPassivesByTopicId( String topic_id );

    /**
     * 根据主题id获取对应的评价方名称集合
     *
     * @param topic_id
     * @return
     */
    String getActivesByTopicId( String topic_id );

    /**
     * 根据 主题id 查询评价主题数据
     *
     * @param topic_id
     * @return
     */
    Map<String, Object> getTopicEvaluateById( String topic_id );

    /**
     * 根据评价主题id 查询被评价单位
     *
     * @param info
     * @return
     */
    List<TopicPassiveInfo> getTopicPassiveInfoListTopic_id( TopicPassiveInfo info );

    /**
     * 根据主题id、机构id 查询数据
     *
     * @param topic_id
     * @param institution_id
     * @return
     */
    TopicPassiveInfo getTopicPassiveInfoByTopicInstitutionId(
            @Param( "topic_id" ) String topic_id,
            @Param( "institution_id" ) String institution_id );

    /**
     * 根据主题id查询 被评价方总数
     *
     * @param info
     * @return
     */
    Integer getTopicPassiveInfoTotalByTopic_id( TopicPassiveInfo info );
}
