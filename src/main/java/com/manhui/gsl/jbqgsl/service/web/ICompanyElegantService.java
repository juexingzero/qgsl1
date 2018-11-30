package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.controller.app.companyelegant.CompanyElegantResult;
import com.manhui.gsl.jbqgsl.model.CompanyElegant;

/**
 * @类名称 ISysParamService.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:30:22
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ICompanyElegantService {
    PageInfo<CompanyElegant> getCompanyElegantList( Map<String, Object> conditionMap );

    List<CompanyElegantResult> getCompanyElegantListForApp( Map<String, Object> conditionMap );

    CompanyElegant getCompanyElegantDetail( String elegant_id );

    CompanyElegantResult getCompanyElegantDetailForApp( String elegant_id );

    Integer saveCompanyElegant( CompanyElegant elegant );

    Integer deleteCompanyElegant( String elegant_id );
}
