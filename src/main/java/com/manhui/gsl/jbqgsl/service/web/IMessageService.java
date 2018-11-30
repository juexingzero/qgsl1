package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;

/**
 * @类名称 IMessageService.java
 * @类描述 消息信息及流水
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:29:14
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
public interface IMessageService {
    List<MessageInfo> getMessageInfoList( MessageInfo info );

    Integer getMessageInfoTotal( MessageInfo info );

    MessageInfo getMessageInfo( String message_id );

    Integer saveMessageInfo( MessageInfo info );

    Integer deleteMessageInfo( String message_ids );

    List<MessageFlowing> getMessageFlowingList( MessageFlowing flowing );

    Integer getMessageFlowingTotal( MessageFlowing flowing );

    Integer saveMessageFlowing( HttpServletRequest request, String message_id, List<AppUser> appUserList );

    List<AppUser> getAppUserList( AppUser user );

    Integer getAppUserTotal( AppUser user );

    Map<String, Object> getMessageFlowingListForApp( Map<String, Object> conditionMap );

    Map<String, Object> updateMessageFlowingForApp( MessageFlowing flowing );
}
