package com.manhui.gsl.jbqgsl.service.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyrzqk;

import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业认证情况表
 */
public interface AppMemberQyQyrzqkService {

    /**
     *
     * @param entity
     */
    void save(MemberQyQyrzqk entity);

    /**
     * 根企业基本信息表id 查询数据
     * @param qyid
     * @return
     */
    List<Map<String,Object>> queryByQyid(String qyid);

    int updateById(MemberQyQyrzqk entity);
}
