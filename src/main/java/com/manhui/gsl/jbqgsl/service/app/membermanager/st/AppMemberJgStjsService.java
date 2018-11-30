package com.manhui.gsl.jbqgsl.service.app.membermanager.st;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjs;

import java.util.Map;

/**
 * 社团介绍信息表
 */
public interface AppMemberJgStjsService {
    /**
     * 保存或修改社团介绍信息
     * @param entity
     * @return
     */
    JsonResult addStjsData(MemberJgStjs entity);

    /**
     * 根据社团id 查询数据
     * @param stid
     * @return
     */
    Map<String,Object> queryCompanyApplyData(String stid);
}
