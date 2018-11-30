package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.controller.app.companyelegant.CompanyElegantResult;
import com.manhui.gsl.jbqgsl.model.CompanyElegant;

/**
 * @类名称 CompanyElegantMapper.java
 * @类描述 企业风采
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:42:09
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface CompanyElegantMapper {
    List<CompanyElegant> getCompanyElegantList( Map<String, Object> conditionMap );

    List<CompanyElegantResult> getCompanyElegantListForApp( Map<String, Object> conditionMap );

    CompanyElegant getCompanyElegantDetail( @Param( "elegant_id" ) String elegant_id );

    CompanyElegantResult getCompanyElegantDetailForApp( @Param( "elegant_id" ) String elegant_id );

    Integer insertCompanyElegant( CompanyElegant elegant );

    Integer updateCompanyElegant( CompanyElegant elegant );

    Integer deleteCompanyElegant( @Param( "elegant_id" ) String elegant_id );
}
