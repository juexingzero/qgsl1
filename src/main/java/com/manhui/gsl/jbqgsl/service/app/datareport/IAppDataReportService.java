package com.manhui.gsl.jbqgsl.service.app.datareport;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.datareport.result.DataReportList;
import com.manhui.gsl.jbqgsl.model.datareport.DataEnterprise;

/**
 * @类名称 IDataReportService.java
 * @类描述 <pre>数据上报模块service层接口</pre>
 * @作者  LiuBin
 * @创建时间 2018年11月26日22:09:34
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	LiuBin 	 2018年11月20日    创建
 *     ----------------------------------------------
 * </pre>
 */
public interface IAppDataReportService {
	/**
	 * 待上报
	 */
	List<DataReportList> queryDataReportList(String user_id);
	/**
	 * 已上报
	 */
	List<DataReportList> getAlreadyDataReportList(String user_id);
	/**
	 * 数据上报--保存
	 */
	int saveDataReportList(DataEnterprise dataEnterprise);
	/**
	 * 数据上传--根据表member_data_relation中的data_report_id(上报数据ID)查找
	 */
	DataEnterprise queryMemberDataEnterprise(String data_report_id);
	/**
	 * 数据上传--获取模板简介
	 */
	Map<String, Object> dataTemplateIntroduce(String template_id);
	/**
	 * 根据上报数据ID查看该会员是否已经填写
	 */
	String queryDataRelationState(String id);
  
}
