package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.controller.app.companyfeedback.CompanyFeedbackResult;
import com.manhui.gsl.jbqgsl.model.CompanyFeedback;

/**
 * @类名称 CompanyFeedbackMapper.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:23:57
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface CompanyFeedbackMapper {
    List<CompanyFeedback> getCompanyFeedbackList( Map<String, Object> conditionMap );

    List<CompanyFeedbackResult> getCompanyFeedbackListForApp( Map<String, Object> conditionMap );

    CompanyFeedback getCompanyFeedbackDetail( @Param( "feedback_id" ) String feedback_id );

    CompanyFeedbackResult getCompanyFeedbackDetailForApp( @Param( "feedback_id" ) String feedback_id );

    String getMaxFeedbackNumber();

    Integer insertCompanyFeedback( CompanyFeedback feedback );

    Integer updateCompanyFeedback( CompanyFeedback feedback );

    Integer deleteCompanyFeedback( @Param( "feedback_id" ) String feedback_id );
}
