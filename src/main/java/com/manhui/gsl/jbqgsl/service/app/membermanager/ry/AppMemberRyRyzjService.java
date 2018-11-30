package com.manhui.gsl.jbqgsl.service.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;

import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，人员证件管理
 */
public interface AppMemberRyRyzjService {

	void save(MemberRyRyzj entity);

    /**
     * 根据人员信息表 id 查询数据
     * @param rybid
     * @return
     */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

    int updateById(MemberRyRyzj entity);

    /**
     * 根据人员信息基本表id 修改数据
     * @param entity
     */
    void updateByRyid(MemberRyRyzj entity);

    /**
     * 保存个人入会证件信息
     * @param list
     */
    JsonResult addIndividualMemberZjData(List<MemberRyRyzj> list, String ryid,String joinId);
}
