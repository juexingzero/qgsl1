package com.manhui.gsl.jbqgsl.service.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;

/**
 * @类名称 MessageServiceImpl.java
 * @类描述 消息信息及流水
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:35:24
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月23日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class MessageServiceImpl implements IMessageService {
    private static final Logger logger = LoggerFactory.getLogger( MessageServiceImpl.class );
    @Resource
    private MessageMapper       messageMapper;

    @Override
    public List<MessageInfo> getMessageInfoList( MessageInfo info ) {
        logger.info( "----- 消息管理-获取消息信息列表 ==> start -----" );
        List<MessageInfo> infoList = messageMapper.getMessageInfoList( info );
        logger.info( "----- 消息管理-获取消息信息列表 ==> end -----" );
        return infoList;
    }

    @Override
    public Integer getMessageInfoTotal( MessageInfo info ) {
        logger.info( "----- 消息管理-获取消息信息总数 ==> start -----" );
        Integer total = messageMapper.getMessageInfoTotal( info );
        logger.info( "----- 消息管理-获取消息信息总数 ==> end -----" );
        return total;
    }

    @Override
    public MessageInfo getMessageInfo( String message_id ) {
        logger.info( "----- 消息管理-获取消息信息 ==> start -----" );
        MessageInfo info = messageMapper.getMessageInfo( message_id );
        logger.info( "----- 消息管理-获取消息信息 ==> end -----" );
        return info;
    }

    @Override
    public Integer saveMessageInfo( MessageInfo info ) {
        Integer flag = 0;
        String message_id = info.getMessage_id();
        if ( message_id != null && !"".equals( message_id ) ) {
            flag = this.updateMessageInfo( info );
        }
        else {
            info.setMessage_id( UUIDUtil.getUUID() );
            flag = this.insertMessageInfo( info );
        }
        return flag;
    }

    private Integer insertMessageInfo( MessageInfo info ) {
        logger.info( "----- 消息管理-插入消息信息 ==> start -----" );
        info.setCreate_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
        Integer flag = messageMapper.insertMessageInfo( info );
        logger.info( "----- 消息管理-插入消息信息 ==> end -----" );
        return flag;
    }

    private Integer updateMessageInfo( MessageInfo info ) {
        logger.info( "----- 消息管理-更新消息信息 ==> start -----" );
        info.setUpdate_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
        Integer flag = messageMapper.updateMessageInfo( info );
        logger.info( "----- 消息管理-更新消息信息 ==> end -----" );
        return flag;
    }

    @Override
    public Integer deleteMessageInfo( String message_ids ) {
        logger.info( "----- 消息管理-删除消息信息 ==> start -----" );
        Integer flag = 0;
        List<MessageFlowing> mfList = messageMapper.getMessageFlowingListForDelCheck( message_ids.split( "," ) );
        if ( mfList != null && mfList.size() > 0 ) {
            flag = 2;
        }
        else {
            flag = messageMapper.deleteMessageInfo( message_ids.split( "," ) );
        }
        logger.info( "----- 消息管理-删除消息信息 ==> end -----" );
        return flag;
    }

    @Override
    public List<MessageFlowing> getMessageFlowingList( MessageFlowing flowing ) {
        logger.info( "----- 消息管理-获取消息流水列表 ==> start -----" );
        List<MessageFlowing> flowingList = messageMapper.getMessageFlowingList( flowing );
        logger.info( "----- 消息管理-获取消息流水列表 ==> end -----" );
        return flowingList;
    }

    @Override
    public Integer getMessageFlowingTotal( MessageFlowing flowing ) {
        logger.info( "----- 消息管理-获取消息流水总数 ==> start -----" );
        Integer total = messageMapper.getMessageFlowingTotal( flowing );
        logger.info( "----- 消息管理-获取消息流水总数 ==> end -----" );
        return total;
    }

    @Override
    public Integer saveMessageFlowing( HttpServletRequest request, String message_id, List<AppUser> appUserList ) {
        logger.info( "----- 消息管理-保存发送消息流水 ==> start -----" );
        Integer total = 0;
        if ( appUserList != null && appUserList.size() > 0 ) {
            for ( AppUser appUser : appUserList ) {
                MessageFlowing flowing = new MessageFlowing();
                flowing.setMessage_id( message_id );
                flowing.setFlowing_id( UUIDUtil.getUUID() );
                flowing.setSend_id( AppUtil.getCookieByName( request, "user_id" ) );
                flowing.setSend_name( AppUtil.getCookieByName( request, "user_name" ) );
                flowing.setSend_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
                flowing.setReceive_id( appUser.getUser_id() );
                flowing.setReceive_name( appUser.getUser_name() );
                flowing.setIs_read( "0" );
                total = messageMapper.insertMessageFlowing( flowing );
            }
        }
        logger.info( "----- 消息管理-保存发送消息流水 ==> end -----" );
        return total;
    }

    @Override
    public List<AppUser> getAppUserList( AppUser user ) {
        logger.info( "----- 消息管理-获取消息接收方的用户列表 ==> start -----" );
        List<AppUser> flowingList = messageMapper.getAppUserList( user );
        logger.info( "----- 消息管理-获取消息接收方的用户列表 ==> end -----" );
        return flowingList;
    }

    @Override
    public Integer getAppUserTotal( AppUser user ) {
        logger.info( "----- 消息管理-获取消息接收方的用户总数 ==> start -----" );
        Integer total = messageMapper.getAppUserTotal( user );
        logger.info( "----- 消息管理-获取消息接收方的用户总数 ==> end -----" );
        return total;
    }

    @Override
    public Map<String, Object> getMessageFlowingListForApp( Map<String, Object> conditionMap ) {
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, Object>> flowingList = messageMapper.getMessageFlowingListForApp( conditionMap );
        if ( flowingList != null && flowingList.size() > 0 ) {
            for ( Map<String, Object> mfMap : flowingList ) {
                String message_type = mfMap.get( "message_type" ) + "";
                String type_text = "";
                if ( "1".equals( message_type ) ) {
                    type_text = "广播通知";
                }
                else if ( "2".equals( message_type ) ) {
                    type_text = "工作任务";
                }
                else if ( "3".equals( message_type ) ) {
                    type_text = "意见回复";
                }
                mfMap.put( "message_type", type_text );
            }
        }
        resp.put( "flowingList", flowingList );
        return resp;
    }

    @Override
    public Map<String, Object> updateMessageFlowingForApp( MessageFlowing flowing ) {
        Map<String, Object> resp = new HashMap<>();
        Integer flag = messageMapper.updateMessageFlowing( flowing );
        resp.put( "flag", flag );
        return resp;
    }
}
