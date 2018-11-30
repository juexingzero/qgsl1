package com.manhui.gsl.jbqgsl.dao.web.membermanager;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.controller.web.membermanager.MemberListResult;

/**
 * @类名称 MemberManagerMapper.java
 * @类描述 会员管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年11月7日 下午9:07:20
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
@Mapper
public interface MemberManagerMapper {
    //获取会员信息列表
    List<MemberListResult> getMemberList( Map<String, Object> conditionMap );

    //获取商会职务内容
    List<Map<String, Object>> getCommerceForMemberList( Map<String, Object> conditionMap );

    //插入会员审批记录
    Integer insertMemberApprove( Map<String, Object> conditionMap );

    //更新会员商会中间表中的状态
    Integer updateMemberJoinManager( Map<String, Object> conditionMap );

    //获取最新的审批记录通过会员ID
    Map<String, Object> getApproveInfoNewestByMemberId( @Param( "member_id" ) String member_id );

    /**
     * 修改审批未通过资料类别，根据memberId修改
     * @param paramMap
     * @return
     */
    int updateApprove_fail_infoByMemberId(Map<String,Object> paramMap);
}
