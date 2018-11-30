package com.manhui.gsl.jbqgsl.service.app.membermanager;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyzw;

import java.util.List;
import java.util.Map;

/**
 * 公人事主要职务表
 */
public interface APPMemberRyFgrszyzwService {

    void save(MemberRyFgrszyzw entity);

    /**
     * 根据关联表id 查询数据
     * @param paramid
     * @return
     */
    List<Map<String,Object>> appQueryMapByParamid(String paramid);

    /**
     * 根据关联表paramid 修改数据
     * @param entity
     * @return
     */
    int updateByParamid(MemberRyFgrszyzw entity);
}
