package com.manhui.gsl.jbqgsl.service.web.datareport;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.datareport.DataTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @类名称 IDataReportService.java
 * @类描述 <pre>数据上报模块service层接口</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年11月20日22:09:34
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年11月20日    创建
 *     ----------------------------------------------
 * </pre>
 */
public interface IDataReportService {
    List<DataTemplate> queryDataTempleteList(Map<String,Object> data);
    Integer queryDataTemplateCount(String title);
    DataTemplate queryDataTemplateByTemplateId(String template_id);
    JsonResult saveDataTemplate(DataTemplate template);
    JsonResult editDataTemplate(DataTemplate template);
    JsonResult deleteDataTemplate(String template_id);
    JsonResult ChangeDataTemplateState();
    String exportDataReportResult(HttpServletResponse response, String template_id, String template_type);
    List<Map<String,Object>> queryDataReportList(Map<String,Object> data);
    Integer queryDataReportListCount(Map<String,Object> data);
    Map<String,Object> queryEnterpriseDataReportDetail(String data_report_id);
}
