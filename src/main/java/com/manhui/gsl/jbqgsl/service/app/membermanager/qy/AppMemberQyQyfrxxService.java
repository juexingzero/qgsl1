package com.manhui.gsl.jbqgsl.service.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyfrxxResult;

import java.util.List;
import java.util.Map;

/**
 * 企业会员，企业法人信息表
 */
public interface AppMemberQyQyfrxxService {
    /**
     * 保存企业法人信息
     * @param entity
     * @return
     */
    QyfrxxResult addLegalPersonData(QyfrxxResult entity);

    /**
     * 修改企业法人信息
     * @param entity
     * @return
     */
    QyfrxxResult updateLegalPersonData(QyfrxxResult entity);

    /**
     * 根据企业id 查询数据
     * @param qyid
     * @return
     */
    Map<String,Object> queryCompanyApplyData(String qyid);
}
