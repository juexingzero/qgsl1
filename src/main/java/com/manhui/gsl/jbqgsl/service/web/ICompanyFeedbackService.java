package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.controller.app.companyfeedback.CompanyFeedbackResult;
import com.manhui.gsl.jbqgsl.model.CompanyFeedback;

/**
 * @类名称 ICompanyFeedbackService.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:18:21
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ICompanyFeedbackService {
    PageInfo<CompanyFeedback> getCompanyFeedbackList( Map<String, Object> conditionMap );

    List<CompanyFeedbackResult> getCompanyFeedbackListForApp( Map<String, Object> conditionMap );

    CompanyFeedback getCompanyFeedbackDetail( String feedback_id );

    CompanyFeedbackResult getCompanyFeedbackDetailForApp( String feedback_id );

    Integer saveCompanyFeedback( CompanyFeedback feedback );

    Integer deleteCompanyFeedback( String feedback_id );
}
