package com.manhui.gsl.jbqgsl.controller.app.companyelegant;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.service.web.ICompanyElegantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppCompanyElegantController.java
 * @类描述 企业风采
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:23:21
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
@Api( tags = "企业风采" )
@Controller
@RequestMapping( AppCompanyElegantController.ROOT_URL )
public class AppCompanyElegantController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/companyElegant";
    @Resource
    private ICompanyElegantService   elegantService;

    @ApiOperation( value = "获取企业风采列表", notes = "获取企业风采列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "company_name", value = "企业名称", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "company_type", value = "企业分类(0：所有会员，1：普通会员，2：优秀会员)", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyElegantList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getCompanyElegantList(
            @RequestParam( value = "company_name", required = false ) String company_name,
            @RequestParam( value = "company_type", required = false ) String company_type ) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put( "company_name", company_name );
        if ( "2".equals( company_type ) ) {
            conditionMap.put( "company_type", company_type );
        }
        return new JsonResult(elegantService.getCompanyElegantListForApp( conditionMap ));
    }

    @ApiOperation( value = "获取企业详情", notes = "获取企业详情" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "elegant_id", value = "风采ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyElegantDetail", method = RequestMethod.GET )
    public String getCompanyElegantDetail(HttpServletRequest request,
            @RequestParam( value = "elegant_id", required = true ) String elegant_id) {
        CompanyElegantResult elegant = elegantService.getCompanyElegantDetailForApp( elegant_id );
        request.setAttribute("elegant",elegant);
        request.setAttribute("requestUrl",String.valueOf( request.getRequestURL() ).split( "app" )[0]);
        return "/web/html/companyElegant/app/elegantDetail";
    }
}
