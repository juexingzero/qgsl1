package com.manhui.gsl.jbqgsl.dao.web.commerce;

import com.manhui.gsl.jbqgsl.controller.web.commerce.LeadershipResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShldbz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @类名称 LeadershipMapper.java
 * @类描述 领导班子
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月5日17:56:20
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     Jiangxiaosong    2018年11月5日    创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface LeadershipMapper {
    /**
     * 获取列表数据
     * @param data
     * @return
     */
    List<LeadershipResult> queryLeadershipList(Map<String,Object> data);

    /**
     * 列表总数
     * @param SHBMID
     * @return
     */
    Integer queryLeadershipListSize(String SHBMID);

    /**
     * 获取一般商会列表数据
     * @param SHBMID
     * @return
     */
    List<Map<String,Object>> queryLeadershipDetailList(String SHBMID);

    /**
     * 获取详细数据
     * @param data
     * @return
     */
    List<Map<String,Object>> queryLeadership(Map<String,Object> data);

    /**
     * 添加人员获取数据
     * @param data
     * @return
     */
    List<Map<String,Object>> queryLeaderMemberInfo(Map<String,Object> data);

    /**
     * 检查人员是否选择同一职位
     * @param data
     * @return
     */
    Map<String,Object> queryLeaderCheck(MemberJgShldbz data);

    /**
     * 检查领导是否同一届同一人选择两次
     * @param data
     * @return
     */
    Map<String,Object> queryLeaderMemberCheck(MemberJgShldbz data);

    /**
     * 保存数据
     * @param data
     * @return
     */
    Integer saveLeadership(MemberJgShldbz data);

    /**
     * 编辑数据
     * @param data
     * @return
     */
    Integer editLeadership(MemberJgShldbz data);

    /**
     * 删除领导人
     * @param ID
     * @return
     */
    Integer deleteLeadership(String ID);
}
