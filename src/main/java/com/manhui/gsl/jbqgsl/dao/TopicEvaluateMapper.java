package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.model.TopicActiveInfo;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;

/**
 * @类名称 TopicEvaluateMapper.java
 * @类描述 主题评价管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午2:30:27
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface TopicEvaluateMapper {
    /**
     * 获取主题评价列表
     * 
     * @param topic_id
     * @return
     */
    List<TopicEvaluate> getTopicEvaluateList( TopicEvaluate topic );

    /**
     * 获取主题评价总数
     * 
     * @param topic
     * @return
     */
    Integer getTopicEvaluateTotal( TopicEvaluate topic );

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
     * 根据主题id获取对应的评价方名称
     * 
     * @param passive
     * @return
     */
    List<Map<String, Object>> getPassiveListForCheck( Map<String, Object> paramMap );

    /**
     * 获取当前最大的主题编号
     * 
     * @return
     */
    String getMaxTopicNumber();

    /**
     * 插入主题评价内容
     * 
     * @param topic
     * @return
     */
    Integer insertTopicEvaluate( TopicEvaluate topic );

    /**
     * 插入主题评价标准
     * 
     * @param standard
     * @return
     */
    Integer insertStandard( TopicEvaluateStandard standard );

    /**
     * 插入主题评价标准明细
     * 
     * @param detail
     * @return
     */
    Integer insertStandardDetail( TopicEvaluateStandardDetail detail );

    /**
     * 插入主题被评价方信息
     * 
     * @param passiveInfo
     * @return
     */
    Integer insertTopicPassiveInfo( TopicPassiveInfo passiveInfo );

    /**
     * 插入主题评价方信息
     * 
     * @param activeInfo
     * @return
     */
    Integer insertTopicActiveInfo( TopicActiveInfo activeInfo );

    /**
     * 修改主题评价内容
     * 
     * @param topic
     * @return
     */
    Integer updateTopicEvaluate( TopicEvaluate topic );

    /**
     * 删除主题评价标准
     * 
     * @param topic
     * @return
     */
    Integer deleteStandard( String topic_id );

    /**
     * 删除主题评价标准详情
     * 
     * @param topic
     * @return
     */
    Integer deleteStandardDetail( String topic_id );

    /**
     * 删除被评价方
     * 
     * @param topic
     * @return
     */
    Integer deletePassive( String topic_id );

    /**
     * 修改主题评价内容
     * 
     * @param topic
     * @return
     */
    Integer updateTopicPassiveInfo(
            @Param( "topic_id" ) String topic_id,
            @Param( "institution_id" ) String institution_id,
            @Param( "real_score_avg" ) double real_score_avg );

    /**
     * 删除评价方
     * 
     * @param topic
     * @return
     */
    Integer deleteActive( String topic_id );

    /**
     * 获取主题评价信息
     * 
     * @param topic_id
     * @return
     */
    TopicEvaluate getTopicEvaluate( String topic_id );

    /**
     * 获取主题评价标准信息
     * 
     * @param topic_id
     * @return
     */
    TopicEvaluateStandard getTopicEvaluateStandard( String topic_id );

    /**
     * 获取主题评价标准信息详情列表，根据级别
     * 
     * @param topic_id
     * @param standard_id
     * @param detail_id
     * @param detail_level
     * @return
     */
    List<TopicEvaluateStandardDetail> getTopicEvaluateStandardDetail(
            @Param( "topic_id" ) String topic_id,
            @Param( "standard_id" ) String standard_id,
            @Param( "detail_id" ) String detail_id,
            @Param( "detail_level" ) Integer detail_level );

    /**
     * 获取被评价方列表，通过主题ID
     * 
     * @param topic_id
     * @return
     */
    List<Map<String, Object>> getTopicPassiveList( String topic_id );

    /**
     * 获取评价方列表，通过主题ID
     * 
     * @param topic_id
     * @return
     */
    List<Map<String, Object>> getTopicActiveList( String topic_id );

    /**
     * 获取用于发送消息的评价方列表，通过主题ID
     * 
     * @param topic_id
     * @return
     */
    List<Map<String, Object>> getTopicActiveListForSendMessage( String topic_id );

    /**
     * 检测该评价方是否是主管部门
     * 
     * @param topic_id
     * @param institution_id
     * @return
     */
    List<TopicActiveInfo> isInstitutionMain(
            @Param( "topic_id" ) String topic_id,
            @Param( "institution_id" ) String institution_id );
}
