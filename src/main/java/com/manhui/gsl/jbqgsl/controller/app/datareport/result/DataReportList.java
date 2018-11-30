
package com.manhui.gsl.jbqgsl.controller.app.datareport.result;

import lombok.Data;

/**
* @ClassName: DataReportList
* @Description: TODO(数据上报List)
* @author LiuBin
* @date 2018年11月20日
*/
@Data
public class DataReportList {
	private String id;//模板企业关系表表ID
	private String qy_id;//就是会员ID
	private String data_report_id;//上报数据ID
	private String template_id;//模板ID
	private String template_title;//模板标题
	private String start_time;//开始时间
	private String end_time;//结束时间
	private String QYMC;//企业名称
	private String TYSHXYDM;//统一社会信用代码
	private String HYFL;//行业分类
	private String report_state;//0:表示未上报,1:表示已经上报
	
}
