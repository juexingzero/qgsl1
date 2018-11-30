package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;

/**
 * @类名称 TopicEvaluateMapper.java
 * @类描述 消息信息及流水
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:41:38
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月23日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface MessageMapper {
    /**
     * 获取消息信息列表
     * 
     * @param info
     * @return
     */
    List<MessageInfo> getMessageInfoList( MessageInfo info );

    /**
     * 获取消息信息总数
     * 
     * @param info
     * @return
     */
    Integer getMessageInfoTotal( MessageInfo info );

    /**
     * 获取消息信息
     * 
     * @param message_id
     * @return
     */
    MessageInfo getMessageInfo( String message_id );

    /**
     * 插入消息信息
     * 
     * @param info
     * @return
     */
    Integer insertMessageInfo( MessageInfo info );

    /**
     * 更新消息信息
     * 
     * @param info
     * @return
     */
    Integer updateMessageInfo( MessageInfo info );

    /**
     * 删除消息信息
     * 
     * @param messageIdsMap
     * @return
     */
    Integer deleteMessageInfo( @Param( "message_ids" )String[] message_ids );

    /**
     * 获取消息流水列表
     * 
     * @param flowing
     * @return
     */
    List<MessageFlowing> getMessageFlowingList( MessageFlowing flowing );

    /**
     * 获取消息流水列表，用于删除消息信息检测
     * 
     * @param message_ids
     * @return
     */
    List<MessageFlowing> getMessageFlowingListForDelCheck( @Param( "message_ids" )String[] message_ids );

    /**
     * 获取消息流水列表，用于APP
     * 
     * @param info
     * @return
     */
    List<Map<String, Object>> getMessageFlowingListForApp( Map<String, Object> conditionMap );

    /**
     * 获取消息流水总数
     * 
     * @param info
     * @return
     */
    Integer getMessageFlowingTotal( MessageFlowing flowing );

    /**
     * 插入消息流水总数
     * 
     * @param info
     * @return
     */
    Integer insertMessageFlowing( MessageFlowing flowing );

    /**
     * 获取消息接收方的用户列表
     * 
     * @param user
     * @return
     */
    List<AppUser> getAppUserList( AppUser user );

    /**
     * 获取消息接收方的用户总数
     * 
     * @param user
     * @return
     */
    Integer getAppUserTotal( AppUser user );

    /**
     * 更新消息流水
     * 
     * @param user
     * @return
     */
    Integer updateMessageFlowing( MessageFlowing flowing );
}
