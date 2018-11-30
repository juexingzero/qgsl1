package com.manhui.gsl.jbqgsl.dao.app.datareport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.controller.app.datareport.result.DataReportList;
import com.manhui.gsl.jbqgsl.model.datareport.DataEnterprise;

import java.util.List;
import java.util.Map;

/**
 * @类名称 DataRelationMapper.java
 * @类描述 <pre>数据上报-详细列表模块dao层，主要负责跟数据库的交互</pre>
 * @作者  LiuBin
 * @创建时间 2018年11月26日15:45:45
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	LiuBin 	2018年11月26日    创建
 *     ----------------------------------------------
 * </pre>
 */
@Mapper
public interface AppDataReportMapper {
	/**
	 * 数据上报--待上报列表
	 */
	List<DataReportList> queryToEvaluateDataReport(@Param("member_id")String member_id);
	/**
	 * 数据上报--已上报列表
	 */
	List<DataReportList> queryToAlreadyEvaluateDataReport(@Param("member_id")String member_id);
	/**
	 * 根据 id修改状态
	 */
	int updateMemberDataRelation(Map<String, Object> conditionMap);
	/**
	 * 保存数据
	 */
	int saveDataReportList(DataEnterprise dataEnterprise);
	/**
	 * 数据上传--根据表member_data_relation中的data_report_id(上报数据ID)查找
	 */
	DataEnterprise queryMemberDataEnterprise(@Param("id")String data_report_id);
	/**
	 * 数据上传--获取模板简介
	 */
	Map<String, Object> queryTemplateIntroduce(@Param("template_id")String template_id);
	/**
	 * 根据上报数据ID查看该会员是否已经填写
	 */
	String queryDataRelationState(@Param("id")String id);
}
