package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.Institution;

/**
 * @类名称 InstitutionMapper.java
 * @类描述 <pre>参评机构模块dao层，主要负责跟数据库的交互</pre>
 * @作者  kevin kwmo1005@163.com
 * @创建时间 2018年8月3日 下午6:33:52
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月3日                创建
 *     ----------------------------------------------
 * </pre>
 */
@Mapper
public interface InstitutionMapper {
    List<Institution> queryInstitutionList( Map<String, Object> conditions );
    Integer queryInstitutionTotal( Map<String, Object> conditions );
    
    Integer deleteInstitution(String institution_id);
    Institution queryInstitution(String institution_id);
    void editInstitution(Institution institution);
    void saveInstitution(Institution institution);
    List<Institution> queryInstitutionByNameAndPhone(@Param("institution_name")String institution_name);
    
    List<Institution> queryInstitutionByInstitutionMainId(Map<String, Object> conditions);
    Integer queryInstitutionCountByInstitutionMainId(Map<String, Object> conditions);
    List<Institution> queryStreetInstitutionByInstitutionMainId(Map<String, Object> conditions);
    Integer queryStreetInstitutionCountByInstitutionMainId(Map<String, Object> conditions);
    void cleanInstitutionMainId(@Param("institution_id")String institution_id);
    void saveInstitutionMainId(@Param("institution_id")String institution_id,@Param("main_institution")String main_institution);
    void cleanStreetMainId(@Param("institution_id")String institution_id);
    void saveStreetMainId(@Param("institution_id")String institution_id,@Param("main_institution")String main_institution);
    List<Map<String, Object>> queryEnterpriseMemberInfo(Map<String, Object> map);
    Institution queryInstitutionByMoblie(String mobile);
}
