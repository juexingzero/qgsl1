package com.manhui.gsl.jbqgsl.controller.web.companyelegant;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.CompanyElegant;
import com.manhui.gsl.jbqgsl.service.web.ICompanyElegantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 CommonSysParamController.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 下午15:02:21
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-企业风采" )
@Controller
@RequestMapping( WebCompanyElegantController.ROOT_URL )
public class WebCompanyElegantController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_WEB + "/companyElegant";
    @Resource
    private ICompanyElegantService   elegantService;

    @ApiOperation( value = "进入企业风采列表页面", notes = "进入企业风采列表页面" )
    @RequestMapping( value = "toElegantListPage", method = RequestMethod.GET )
    public String toElegantListPage() {
        return "/web/html/companyElegant/elegantList";
    }

    @ApiOperation( value = "进入企业新增页面", notes = "进入企业新增页面" )
    @RequestMapping( value = "toElegantAddPage", method = RequestMethod.GET )
    public String toElegantAddPage(HttpServletRequest request, String operation_flag, String elegant_id) {
        request.setAttribute("operation_flag", operation_flag);
        request.setAttribute("elegant_id", elegant_id);
        return "/web/html/companyElegant/elegantAdd";
    }

    @ApiOperation( value = "获取企业风采列表", notes = "获取企业风采列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "参数json(company_name、company_type)", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyElegantList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getCompanyElegantList(
            @RequestParam( value = "json", required = false ) String json,
            @RequestParam( value = "pageIndex", required = false ) String pageIndex,
            @RequestParam( value = "pageSize", required = false ) String pageSize ) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "company_name" ) + "" ) ) {
                conditionMap.put( "company_name", jsonObject.get( "company_name" ) + "" );
            }
            String company_type = jsonObject.get( "company_type" ) + "";
            if ( StringUtils.isNotEmpty( company_type ) && !"0".equals( company_type )) {
                conditionMap.put( "company_type", jsonObject.get( "company_type" ) + "" );
            }
        }
        conditionMap.put( "pageIndex", pageIndex );
        conditionMap.put( "pageSize", pageSize );
        PageInfo<CompanyElegant> pageInfo = elegantService.getCompanyElegantList( conditionMap );
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put( "data", pageInfo.getList() );
        resultMap.put( "total", pageInfo.getTotal() );
        return resultMap;
    }

    @ApiOperation( value = "保存企业信息", notes = "保存企业信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "json", value = "企业信息", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveCompanyElegant", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveCompanyElegant(
            @RequestParam( value = "json", required = true ) String json ) {
        CompanyElegant elegant = JSON.parseObject( json ).toJavaObject( CompanyElegant.class );
        return new JsonResult(elegantService.saveCompanyElegant( elegant ));
    }

    @ApiOperation( value = "获取企业信息", notes = "获取企业信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "elegant_id", value = "风采ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyElegantDetail", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getCompanyElegantDetail(
            @RequestParam( value = "elegant_id", required = true ) String elegant_id ) {
        return new JsonResult(elegantService.getCompanyElegantDetail( elegant_id ));
    }

    @ApiOperation( value = "删除企业信息", notes = "删除企业信息" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "elegant_id", value = "风采ID）", dataType = "字符串" )
    } )
    @RequestMapping( value = "deleteCompanyElegant", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult deleteCompanyElegant(
            @RequestParam( value = "elegant_id", required = true ) String elegant_id) {
        return new JsonResult(elegantService.deleteCompanyElegant( elegant_id ));
    }
}
