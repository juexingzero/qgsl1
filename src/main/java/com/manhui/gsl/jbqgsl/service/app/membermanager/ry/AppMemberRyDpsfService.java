package com.manhui.gsl.jbqgsl.service.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyDpsf;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;

import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，党派身份管理
 */
public interface AppMemberRyDpsfService {

	void save(MemberRyDpsf entity);

    /**
     * 根据人员信息表 id 查询数据
     * @param rybid
     * @return
     */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

    int updateById(MemberRyDpsf entity);

    /**
     * 根据人员基本信息表id 修改数据
     * @param entity
     */
    void updateByRyid(MemberRyDpsf entity);
}