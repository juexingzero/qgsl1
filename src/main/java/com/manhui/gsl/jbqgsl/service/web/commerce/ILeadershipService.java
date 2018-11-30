package com.manhui.gsl.jbqgsl.service.web.commerce;

import com.manhui.gsl.jbqgsl.controller.web.commerce.LeadershipResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShldbz;

import java.util.List;
import java.util.Map;

/**
 * @类名称 ILeadershipService.java
 * @类描述 领导班子
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月5日17:46:40
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年11月5日     创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ILeadershipService {
    List<LeadershipResult> queryLeadershipList(Map<String,Object> data);
    Integer queryLeadershipListSize(String SHBMID);
    List<Map<String,Object>> queryLeadershipDetailList(String SHBMID);
    List<Map<String,Object>> queryLeadership(Map<String,Object> data);
    List<Map<String,Object>> queryLeaderMemberInfo(Map<String,Object> data);
    Integer saveLeadership(MemberJgShldbz data);
    Integer editLeadership(MemberJgShldbz data);
    Integer deleteLeadership(String ID);
}
