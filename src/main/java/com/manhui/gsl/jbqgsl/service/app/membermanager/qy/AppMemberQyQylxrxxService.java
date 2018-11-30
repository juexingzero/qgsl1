package com.manhui.gsl.jbqgsl.service.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQylxrxx;

import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业联系人信息表
 */
public interface AppMemberQyQylxrxxService {
    /**
     *
     * @param entity
     */
    void save(MemberQyQylxrxx entity);

    /**
     * 根据企业基本信息表id 查询数据
     * @param qyid
     * @return
     */
    List<Map<String,Object>> queryByQyid(String qyid);

    /**
     *
     * @param entity
     */
    void updateById(MemberQyQylxrxx entity);
}
