package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.Institution;

@Mapper
public interface AppInstitutionMapper {

	List<Institution> queryInstitutionsByIds(@Param("passiveIdsList") List<String> passiveIdsList, @Param("pageSize")Long pageSize, @Param("pageNo")Long pageNo);
	
	long getTotal(@Param("passiveIdsList")List<String> passiveIdsList);
	/**
	 * 即时评价 --根据已评价机构算出还违背评价机构总数
	 */
	long countTotal(@Param("passiveIdsList")List<String> passiveIdsList);
	/**
	 * 分页
	 */
	List<Institution> queryInstitutions(@Param("pageNo")long pageNo, @Param("pageSize")long pageSize);
	/**
	 * 查看机构类型
	 */
	String queryInstitutionType(@Param("institution_id")String institution_id);
	/**
	 * 即时搜索机构 根据条件
	 */
	List<Institution> queryInstitutionByLike(@Param("passiveIds")List<String> passiveIds, @Param("passive_name")String passive_name);
	/**
	 * 模糊查询已完成
	 */
	List<Institution> queryInstitutionYesByLike( @Param("passiveIds")List<String> passiveIds, @Param("passive_name")String passive_name);
	/**
	 * 模糊查询未完成
	 */
	List<Institution> queryInstitutionNoByLike( @Param("passiveInfoIds")List<String> passiveInfoIds, @Param("passive_name")String passive_name);
	/**
	 * 反向查找未被评价政府机构信息
	 */
	List<Institution> queryInstitutionByLike(List<String> passiveIds);

	long countTotal2();
	/**
	 * 反向查找
	 */
	List<Institution> queryInstitutions(List<String> passiveIdsList, long pageNo, long pageSize);

	List<Institution> queryInstitutions2(List<String> passiveIdsList, long pageNo, long pageSize);

	long getTotal2(@Param("institution_type")String institution_type);
	/**
	 * 查询所有的政府机构
	 */
	List<Institution> queryInstitutionsByIds2(@Param("pageSize")Long pageSize, @Param("pageNo")Long pageNo, @Param("institution_type")String institution_type);
	/**
	 * 模糊查询所有的政府单位
	 */
	List<Institution> queryInstitutionByLike(String passive_name);
	/**
	 * 根据机构id获取到被评方信息
	 */
	Institution queryInstitutionInfo(@Param("passive_id")String passive_id);
	/**
	 * 模糊查询 即时评价
	 */
	List<Institution> queryInstitutionByLike3(@Param("passive_name")String passive_name);
	/**
	 * 分类获取被评价机构总数
	 */
	long getTotal3(@Param("passiveIdsList")List<String> passiveIdsList, @Param("institution_type")String institution_type);
	/**
	 * 分类获取机构信息
	 */
	List<Institution> queryInstitutionsTypeGroupByIds(@Param("passiveIdsList")List<String> passiveIdsList,@Param("institution_type") String institution_type,@Param("pageNo")Long pageNo,@Param("pageSize")Long pageSize);
	/**
	 * 根据机构id查询企业总数
	 * @param institution_id 
	 */
	long getTotal4(@Param("passiveIdsList")List<String> passiveIdsList);
	/**
	 * 根据机构id查询企业总数
	 * @param institution_id 
	 */
	List<Institution> queryInstitutionsCompanyTypeByIds(@Param("passiveIdsList")List<String> passiveIdsList, @Param("pageNo")Long pageNo,@Param("pageSize")Long pageSize);

	Institution queryInstitutionInfo2(@Param("passive_id")String passive_id, @Param("institution_type")String institution_type);

	List<Institution> queryInstitutionsCompanyTypeByIds2(@Param("passiveIdsList")List<String> passiveIdsList, @Param("institution_type")String institution_type,@Param("industry_id")String industry_id,
			@Param("pageNo")Long pageNo,@Param("pageSize") Long pageSize);
	/**
	 * 双向评价 政府评价企业分类展示统计总数
	 */
	long getTotal5(@Param("passiveIdsList")List<String> passiveIdsList, @Param("institution_type")String institution_type, @Param("industry_id")String industry_id,@Param("flag")String flag,@Param("institution_id")String institution_id);
	/**
	 * 获取机构详情
	 */
	Institution getInstitution(@Param("institution_id")String institution_id);
	
}
