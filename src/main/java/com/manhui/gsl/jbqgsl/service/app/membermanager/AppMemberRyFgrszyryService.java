package com.manhui.gsl.jbqgsl.service.app.membermanager;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;

import java.util.List;
import java.util.Map;

/**
 * 非公人士主要荣誉表
 */
public interface AppMemberRyFgrszyryService {
    /**
     * 保存会员 个人入会荣誉信息
     * @param ryryList
     * @param ryid
     * @return
     */
    JsonResult addIndividualMemberRyData(List<MemberRyFgrszyry> ryryList, String ryid,String joinId);

    /**
     * 根据关联id查询荣誉信息
     * @param paramid
     * @return
     */
    List<Map<String,Object>> appQueryMapByRyid(String paramid);

    /**
     * 根据id 修改数据
     * @param entity
     */
    int updateById(MemberRyFgrszyry entity);
}
