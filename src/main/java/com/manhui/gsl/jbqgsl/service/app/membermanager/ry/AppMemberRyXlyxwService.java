package com.manhui.gsl.jbqgsl.service.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;

import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，学位学历管理
 */
public interface AppMemberRyXlyxwService {

	void save(MemberRyXlyxw entity);

    /**
     * 根据人员信息表 id 查询数据
     * @param rybid
     * @return
     */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

    int updateById(MemberRyXlyxw entity);

    /**
     * 根基人员基本信息表id ，修改数据
     * @param entity
     */
    void updateByRyid(MemberRyXlyxw entity);
}
