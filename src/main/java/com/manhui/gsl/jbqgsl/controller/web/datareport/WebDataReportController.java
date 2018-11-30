package com.manhui.gsl.jbqgsl.controller.web.datareport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.web.institution.WebInstitutionController;
import com.manhui.gsl.jbqgsl.model.datareport.DataTemplate;
import com.manhui.gsl.jbqgsl.service.web.datareport.IDataReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.naming.event.ObjectChangeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名称 WebDataReportController.java
 * @类描述 <pre>数据上报模块controller层，主要负责请求的接收及响应</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年11月20日22:03:22
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00   Jiangxiaosong 	 2018年11月20日     创建
 *     ----------------------------------------------
 * </pre>
 */
@Api( tags = "后台-数据上报" )
@Controller
@RequestMapping( WebDataReportController.ROOT_URL )
public class WebDataReportController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/datareport";

    @Resource
    private IDataReportService dataReportService;

    @ApiOperation(value = "进入数据模板列表页面", notes = "进入模板列表页面")
    @RequestMapping(value = "toDataTemplateListPage", method = RequestMethod.GET)
    public String toDataTemplateListPage() {
        return "/web/html/datareport/dataTemplateList";
    }


    @ApiOperation(value = "进入数据模板新增页面", notes = "进入模板新增页面")
    @RequestMapping(value = "toAddDataTemplatePage", method = RequestMethod.GET)
    public String toAddDataTemplatePage(HttpServletRequest request) {
        request.setAttribute("flag","add");
        return "/web/html/datareport/templateSave";
    }


    @ApiOperation(value = "进入数据模板编辑页面", notes = "进入模板编辑页面")
    @RequestMapping(value = "toEditDataTemplatePage", method = RequestMethod.GET)
    public String toEditDataTemplatePage(String template_id,HttpServletRequest request) {
        request.setAttribute("flag","edit");
        request.setAttribute("template_id",template_id);
        return "/web/html/datareport/templateSave";
    }


    @ApiOperation(value = "进入企业数据上报详细列表页面", notes = "进入详细列表页面")
    @RequestMapping(value = "toEnterpriseDataReportListPage", method = RequestMethod.GET)
    public String toEnterpriseDataReportListPage(String template_id,String template_title,HttpServletRequest request) {
        request.setAttribute("template_id",template_id);
        request.setAttribute("title",template_title);
        return "/web/html/datareport/enterpriseDataReportList";
    }


    @ApiOperation(value = "进入企业数据上报详细页面", notes = "进入详细页面")
    @RequestMapping(value = "toEnterpriseDataReportPage", method = RequestMethod.GET)
    public String toEnterpriseDataReportPage(String data_report_id,HttpServletRequest request) {
        request.setAttribute("data_report_id",data_report_id);
        return "/web/html/datareport/enterpriseDataReportDetail";
    }



    @ApiOperation(value = "数据上报信息列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",  name = "title", value = "模板标题", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query",  name = "pageIndex", value = "模板标题", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query",  name = "pageSize", value = "模板标题", dataType = "字符串")
    })
    @RequestMapping(value = "queryDataTemplateList", method = RequestMethod.POST)
    public Map<String, Object> queryDataTemplateList(HttpServletRequest request, String title,Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        data.put("title",title);
        data.put("pageNo",pageIndex * pageSize);
        data.put("pageSize",pageSize);
        List<DataTemplate> results = dataReportService.queryDataTempleteList(data);
        Integer total = dataReportService.queryDataTemplateCount(title);
        if ( !results.isEmpty() ) {
            map.put( "data", results );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }


    @ApiOperation(value = "数据上报模板详细数据", notes = "查询模板详细数据")
    @RequestMapping(value = "dataTemplateDetail", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",  name = "template_id", value = "模板ID", dataType = "字符串"),
    })
    @ResponseBody
    public Map<String,Object> dataTemplateDetail( String template_id ) {
        Map<String, Object> result = new HashMap<>();
        DataTemplate data = dataReportService.queryDataTemplateByTemplateId( template_id );
        result.put("data",data);
        return result;
    }


    @ApiOperation(value = "新增模板数据", notes = "新增数据")
    @RequestMapping(value = "dataTemplateSave", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult dataTemplateSave(String json,HttpServletRequest request){
        JsonResult result = new JsonResult();
        JSONObject parseObject = JSON.parseObject(json);
        DataTemplate template = parseObject.toJavaObject(DataTemplate.class);
        template.setTemplate_id(UUIDUtil.getUUID());
        template.setUpdate_time(DateUtil.getTime());
        template.setState("0");
        template.setCreate_time(DateUtil.getTime());
        result = dataReportService.saveDataTemplate(template);
        return result = new JsonResult();
    }


    @ApiOperation(value = "编辑模板数据", notes = "编辑数据")
    @RequestMapping(value = "dataTemplateEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult dataTemplateEdit(String json,HttpServletRequest request){
        JsonResult result = new JsonResult();
        JSONObject parseObject = JSON.parseObject(json);
        DataTemplate template = parseObject.toJavaObject(DataTemplate.class);
        template.setUpdate_time(DateUtil.getTime());
        result = dataReportService.editDataTemplate(template);
        return result = new JsonResult();
    }


    @ApiOperation(value = "删除数据", notes = "删除数据")
    @RequestMapping(value = "deleteDataTemplate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteDataTemplate(String template_id){
        JsonResult result = new JsonResult();
        result = dataReportService.deleteDataTemplate(template_id);
        return result;
    }


    @ApiOperation( value = "定时任务：检测并更新模板状态", notes = "定时任务：检测并更新模板状态" )
    @RequestMapping( value = "checkTemplateState", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult checkTemplateState() {
        return dataReportService.ChangeDataTemplateState();
    }


    /**
     * 导出数据上报结果
     * @return
     */
    @RequestMapping( value = "exportDataReportResult", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> exportDataReportResult(HttpServletResponse response, String template_id,String template_type) {
        System.out.println("模板id："+template_id);
        System.out.println("模板类型："+template_type);
        Map<String, Object> map = new HashMap<>();
        String exportResult = dataReportService.exportDataReportResult(response, template_id, template_type);
        if(!"".equals( exportResult )){
            map.put( "code", 200 );
            map.put( "data", exportResult );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "导出有误，请重新尝试" );
        }
        return map;
    }


    @ApiOperation(value = "数据上报详细列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",  name = "QYMC", value = "企业名称", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query",  name = "template_id", value = "数据上报模板ID", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query",  name = "pageIndex", value = "模板标题", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query",  name = "pageSize", value = "模板标题", dataType = "字符串")
    })
    @RequestMapping(value = "queryEnterpriseDataTemplateList", method = RequestMethod.POST)
    public Map<String, Object> queryEnterpriseDataTemplateList(HttpServletRequest request, String QYMC,String template_id,Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        data.put("qymc",QYMC);
        data.put("template_id",template_id);
        data.put("pageNo",pageIndex * pageSize);
        data.put("pageSize",pageSize);
        List<Map<String,Object>> results = dataReportService.queryDataReportList(data);
        Integer total = dataReportService.queryDataReportListCount(data);
        if ( !results.isEmpty() ) {
            map.put( "data", results );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }


    @ApiOperation(value = "数据上报详细数据", notes = "查询详细数据")
    @RequestMapping(value = "queryEnterpriseDataReportDetail", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",  name = "data_report_id", value = "数据上报内容ID", dataType = "字符串"),
    })
    @ResponseBody
    public Map<String,Object> queryEnterpriseDataReportDetail( String data_report_id ) {
        Map<String,Object> result = new HashMap<>();
        result = dataReportService.queryEnterpriseDataReportDetail( data_report_id );
        return result;
    }
}
