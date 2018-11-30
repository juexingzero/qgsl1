package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;

public interface IAppInstitutionService {
	//根据被评价机构id获取机构信息
	PaginationVO<Institution> queryInstitutionsByIds(@Param("passiveIdsList")List<String> passiveIdsList,@Param("pageNo")long pageNo,@Param("pageSize")long pageSize);
	/**
	 * 查询所有的机构信息表 
	 */
	PaginationVO<Institution> queryInstitutions(long pageNo, long pageSize);
	/**
	 * 根据id查看机构类型 
	 */
	String queryInstitutionType(String institution_id);
	/**
	 * 即时评价 --查询未被评价的所有政府机构
	 */
	PaginationVO<Institution> queryInstitutions(List<String> passiveIdsList, long pageNo, long pageSize);
	/**
	 * 即时评价 --查询所有政府机构 
	 */
	PaginationVO<Institution> queryInstitutions2(Long pageNo, Long pageSize,String institution_type);
	/**
	 * 历史评价-获取到被评机构的描述
	 */
	Institution queryInstitutionInfo(String passive_id);
	/**
	 * 企业评价政府 按照政府的类型进行分类查找[1:区级 2:街道]
	 * @param pageSize 
	 * @param pageNo 
	 */
	PaginationVO<Institution> queryInstitutionsTypeGroupByIds(List<String> passiveIdsList, String institution_type, Long pageNo, Long pageSize);
	/**
	 * 根据被评价机构id查询被评价机构主管企业  其他企业
	 * @param institution_id 
	 * @param pageSize 
	 * @param pageNo 
	 */
	PaginationVO<Institution> queryInstitutionsCompanyTypeByIds(List<String> passiveIdsList, String institution_id, Long pageNo, Long pageSize);
	PaginationVO<Institution> queryInstitutionsCompanyTypeByIds2(List<String> passiveIdsList, String institution_type,
			String institution_id,String industry_id, Long pageNo, Long pageSize, String flag);
	/**
	 * 获取机构详情
	 */
	Institution getInstitutionIntroduce(String institution_id);
	

}
