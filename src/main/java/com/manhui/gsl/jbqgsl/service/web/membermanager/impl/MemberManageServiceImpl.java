package com.manhui.gsl.jbqgsl.service.web.membermanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.web.membermanager.MemberListResult;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.dao.web.membermanager.MemberManagerMapper;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;

/**
 * @类名称 MemberManageServiceImpl.java
 * @类描述 会员管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年11月7日 下午9:11:09
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年11月7日               创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class MemberManageServiceImpl implements IMemberManagerService {
    private static final Logger logger = LoggerFactory.getLogger( MemberManageServiceImpl.class );
    @Resource
    private MemberManagerMapper memberManagerMapper;
    @Resource
    private MessageMapper       messageMapper;
    @Value( "${member_jbq_commerce_id}" )
    public String               member_jbq_commerce_id;

    /**
     * @方法名称 getMemberList
     * @功能描述 获取会员信息列表
     * @作者 kevin
     * @创建时间 2018年11月7日 上午11:33:28
     * @param conditionMap
     * @return
     */
    @Override
    public Map<String, Object> getMemberList( Map<String, Object> conditionMap ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info( "-----获取会员信息列表 ==> start -----" );
        PageHelper.startPage(
                Integer.valueOf( conditionMap.get( "pageIndex" ) + "" ) + 1,
                Integer.valueOf( conditionMap.get( "pageSize" ) + "" ) );
        List<MemberListResult> memberOldList = memberManagerMapper.getMemberList( conditionMap );
        List<MemberListResult> memberNewList = new ArrayList<>();
        String menu_type = conditionMap.get( "menu_type" ) + "";
        //会员档案列表只显示审批通过的数据
        if ( "1".equals( menu_type ) ) {
            for ( MemberListResult mlr : memberOldList ) {
                if ( "HYSP-02".equals( mlr.getApprove_status() ) ) {
                    memberNewList.add( mlr );
                }
            }
        }
        //会员审批列表只显示江北区总商会的数据
        else if ( "2".equals( menu_type ) ) {
            for ( MemberListResult mlr : memberOldList ) {
                if ( member_jbq_commerce_id.equals( mlr.getCommerce_id() ) ) {
                    memberNewList.add( mlr );
                }
            }
        }
        else {
            memberNewList = memberOldList;
        }
        PageInfo<MemberListResult> pageInfo = null;
        if ( memberNewList != null && memberNewList.size() > 0 ) {
            pageInfo = new PageInfo<MemberListResult>( memberNewList );
            resultMap.put( "data", pageInfo.getList() );
            resultMap.put( "total", pageInfo.getTotal() );
        }
        logger.info( "-----获取会员信息列表 ==> end -----" );
        return resultMap;
    }

    /**
     * @方法名称 getCommerceForMemberList
     * @功能描述 获取商会职务内容
     * @作者 kevin
     * @创建时间 2018年11月8日 下午8:11:58
     * @param conditionMap
     * @return
     */
    @Override
    public Map<String, Object> getCommerceForMemberList( Map<String, Object> conditionMap ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> commerceList = memberManagerMapper.getCommerceForMemberList( conditionMap );
        if ( commerceList != null && commerceList.size() > 0 ) {
            resultMap.put( "total", commerceList.size() );
        }
        resultMap.put( "data", commerceList );
        return resultMap;
    }

    /**
     * @方法名称 updateMember
     * @功能描述 更新会员信息
     * @作者 kevin
     * @创建时间 2018年11月7日 下午9:06:43
     * @param conditionMap
     * @return
     */
    @Override
    public Integer updateMember( Map<String, Object> conditionMap ) {
        logger.info( "-----更新会员信息 ==> start -----" );
        //更新会员商会中间表中的状态
        conditionMap.put( "update_time", DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        Integer flag = memberManagerMapper.updateMemberJoinManager( conditionMap );
        logger.info( "-----更新会员信息 ==> end -----" );
        return flag;
    }

    /**
     * @方法名称 approveMember
     * @功能描述 审批会员信息
     * @作者 kevin
     * @创建时间 2018年11月8日 上午10:44:01
     * @param conditionMap
     * @return
     */
    @Override
    public Integer approveMember( Map<String, Object> conditionMap ) {
        logger.info( "-----审批会员信息 ==> start -----" );
        //插入会员审批记录
        conditionMap.put( "approve_id", UUIDUtil.getUUID() );
        conditionMap.put( "approve_time", DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        Integer flag = memberManagerMapper.insertMemberApprove( conditionMap );
        //更新会员商会中间表中的状态
        conditionMap.put( "update_time", DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        flag = memberManagerMapper.updateMemberJoinManager( conditionMap );
        //向APP端发送消息
        String approve_status = conditionMap.get( "approve_status" ) + "";
        String approve_fail_reason = conditionMap.get( "approve_fail_reason" ) + "";
        String message_content = "";
        if ( "HYSP-02".equals( approve_status ) ) {
            message_content = "您好：现将告知您的入会申请的审批结果为“" + "审批通过" + "”，请知悉！";
        }
        else if ( "HYSP-03".equals( approve_status ) ) {
            message_content = "您好：现将告知您的入会申请的审批结果为“" + "审批拒绝" + "”，具体原因为“" + approve_fail_reason + "”，请知悉！";
        }
        MessageInfo info = new MessageInfo();
        info.setMessage_id( UUIDUtil.getUUID() );
        info.setMessage_type( "1" );
        info.setMessage_mode( "1" );
        info.setMessage_content( message_content );
        info.setCreator_id( conditionMap.get( "approver_id" ) + "" );
        info.setCreator_name( conditionMap.get( "approver_name" ) + "" );
        info.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        flag = messageMapper.insertMessageInfo( info );
        //发型消息
        MessageFlowing flowing = new MessageFlowing();
        flowing.setMessage_id( info.getMessage_id() );
        flowing.setFlowing_id( UUIDUtil.getUUID() );
        flowing.setSend_id( info.getCreator_id() );
        flowing.setSend_name( info.getCreator_name() );
        flowing.setSend_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        flowing.setReceive_id( conditionMap.get( "app_user_id" ) + "" );
        flowing.setReceive_name( conditionMap.get( "app_user_name" ) + "" );
        flowing.setIs_read( "0" );
        flag = messageMapper.insertMessageFlowing( flowing );
        logger.info( "-----审批会员信息 ==> end -----" );
        return flag;
    }

    /**
     * 修改审批未通过资料类别，根据memberId修改
     * 
     * @param paramMap
     * @return
     */
    @Override
    public Integer updateApprove_fail_infoByMemberId( Map<String, Object> paramMap ) {
        int count = memberManagerMapper.updateApprove_fail_infoByMemberId( paramMap );
        return count;
    }
}
