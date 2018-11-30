package com.manhui.gsl.jbqgsl.service.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyNbrybg;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyTxfs;

import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，内部人员办公管理
 */
public interface AppMemberRyNbrybgService {

	void save(MemberRyNbrybg entity);

    /**
     * 根据人员信息表 id 查询数据
     * @param rybid
     * @return
     */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

    /**
     *
     * @param entity
     */
    int updateById(MemberRyNbrybg entity);
}
