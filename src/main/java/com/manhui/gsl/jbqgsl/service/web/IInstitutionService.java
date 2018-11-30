package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.Institution;

/**
 * @类名称 IInstitutionService.java
 * @类描述 <pre>参评机构模块service层接口</pre>
 * @作者  kevin kwmo1005@163.com
 * @创建时间 2018年8月3日 下午4:26:03
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
public interface IInstitutionService {
	/**
	 * 查询机构列表
	 */
    List<Institution> queryInstitutionList( String institution_name ,String institution_type,String street_main_id,Integer pageIndex, Integer pageSize);

    /**
	 * 查询机构列表总数
	 */
    Integer queryInstitutionTotal( String institution_name ,String institution_type,String street_main_id,Integer pageIndex, Integer pageSize);
    
    /**
	 * 删除机构列表
	 */
    JsonResult deleteInstitution(String institution_id);
    
    /**
	 * 查询机构详细
	 */
    Institution queryInstitution(String institution_id);
    
    /**
	 * 更新机构数据
	 */
    JsonResult editInstitution(Institution institution);
    
    /**
	 * 保存机构数据
     * @param request 
	 */
    JsonResult saveInstitution(Institution institution, HttpServletRequest request);

    /**
     * 保存企业机构数据
     */
    JsonResult saveEnterpriseInstitution(Institution institution);
    
    /**
	 * 验证机构名称及手机唯一性
	 */
    List<Institution> queryInstitutionByNameAndPhone(String institution_name);
    
    /**
     * 查询企业列表
     */
    List<Institution> queryInstitutionByInstitutionMainId(String institution_id,String industry_id,String institution_name,Integer pageIndex, Integer pageSize);
    
    
    /**
     * 查询企业列表总数
     */
    Integer queryInstitutionCountByInstitutionMainId(String institution_id,String industry_id,String institution_name);


    /**
     * 查询街镇企业列表
     */
    List<Institution> queryStreetInstitutionByInstitutionMainId(String institution_id,String industry_id,String institution_name,Integer pageIndex, Integer pageSize);


    /**
     * 查询街镇企业列表总数
     */
    Integer queryStreetInstitutionCountByInstitutionMainId(String institution_id,String industry_id,String institution_name);
    
    
    /**
     * 清空企业关联部门的ID
     */
    void cleanInstitutionMainId(String institution_id);

    /**
     * 添加企业关联部门的ID
     */
    void saveInstitutionMainId(String institution_id,String main_institution);

    /**
     * 清空企业关联部门的ID
     */
    void cleanStreetMainId(String institution_id);

    /**
     * 添加企业关联部门的ID
     */
    void saveStreetMainId(String institution_id,String main_institution);

    /**
     * 获取企业会员数据
     */
    List<Map<String, Object>> queryEnterpriseMemberInfo(Map<String, Object> map);

    /**
     * 根据电话号码获取机构数据
     */
    Institution queryInstitutionByMoblie(String mobile);
}
