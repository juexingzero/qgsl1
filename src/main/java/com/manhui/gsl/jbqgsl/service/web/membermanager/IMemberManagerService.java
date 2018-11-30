package com.manhui.gsl.jbqgsl.service.web.membermanager;

import java.util.Map;

/**
 * @类名称 IMemberManagerService.java
 * @类描述 会员管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年11月7日 下午9:10:01
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年11月7日             
 *     ----------------------------------------------
 *       </pre>
 */
public interface IMemberManagerService {
    //获取会员信息列表
    Map<String, Object> getMemberList( Map<String, Object> conditionMap );

    //获取商会职务内容
    Map<String, Object> getCommerceForMemberList( Map<String, Object> conditionMap );

    //更新会员信息
    Integer updateMember( Map<String, Object> conditionMap );

    //审批会员信息
    Integer approveMember( Map<String, Object> conditionMap );

    /**
     * 修改审批未通过资料类别，根据memberId修改
     * @param paramMap
     * @return
     */
    Integer updateApprove_fail_infoByMemberId(Map<String,Object> paramMap);
}
