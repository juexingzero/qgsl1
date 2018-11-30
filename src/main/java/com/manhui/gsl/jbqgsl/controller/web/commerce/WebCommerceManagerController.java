package com.manhui.gsl.jbqgsl.controller.web.commerce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.commerce.CommerceCategory;
import com.manhui.gsl.jbqgsl.service.web.commerce.ICommerceManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebCommerceCategoryController.java
 * @类描述 商会管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:38:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-商会管理" )
@Controller
@RequestMapping( WebCommerceManagerController.ROOT_URL )
public class WebCommerceManagerController extends BaseController {
    public static final String       ROOT_URL = PARENT_URL_WEB + "/commerce";
    @Resource
    private ICommerceManagerService commerceManagerService;

    @Resource
    private AppMemberJoinManagerService appMemberJoinManagerService;

    @ApiOperation( value = "进入商会管理列表页面", notes = "进入商会管理列表页面" )
    @RequestMapping( value = "toCommerceListPage", method = RequestMethod.GET )
    public String toCommerceListPage() {
        return "/web/html/commerce/commerceList";
    }


    @ApiOperation( value = "进入商会类别新增页面", notes = "进入商会类别新增页面" )
    @RequestMapping( value = "toCategoryAddPage", method = RequestMethod.GET )
    public String toCategoryAddPage( HttpServletRequest request) {
        request.setAttribute("flag","add");
        return "/web/html/commerce/categoryAdd";
    }


    @ApiOperation( value = "进入商会类别编辑页面", notes = "进入商会类别新增页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "ID", value = "商会类别ID", dataType = "字符串") })
    @RequestMapping( value = "toCategoryEditPage", method = RequestMethod.GET )
    public String toCategoryEditPage(@RequestParam(required = true, value = "ID") String ID, HttpServletRequest request) {
        request.setAttribute("ID",ID);
        request.setAttribute("flag","edit");
        return "/web/html/commerce/categoryAdd";
    }

    @ApiOperation( value = "进入商会新增页面", notes = "进入商会新增页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "LYID", value = "商会类别ID", dataType = "字符串") })
    @RequestMapping( value = "toCommerceAddPage", method = RequestMethod.GET )
    public String toCommerceAddPage(@RequestParam(required = true, value = "LYID") String LYID, HttpServletRequest request) {
        request.setAttribute("lyid",LYID);
        request.setAttribute("flag","add");
        return "/web/html/commerce/commerceAdd";
    }

    @ApiOperation( value = "进入商会编辑页面", notes = "进入商会编辑页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "ID", value = "商会ID", dataType = "字符串") })
    @RequestMapping( value = "toCommerceEditPage", method = RequestMethod.GET )
    public String toCommerceEditPage(@RequestParam(required = true, value = "ID") String ID, HttpServletRequest request) {
        request.setAttribute("sid",ID);
        request.setAttribute("flag","edit");
        return "/web/html/commerce/commerceAdd";
    }


    @ApiOperation(value = "商会详细", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "ID", value = "商会ID", dataType = "字符串") })
    @RequestMapping(value = "queryCommerceDetail", method = RequestMethod.POST)
    public Map<String, Object> queryCommerceDetail(@RequestParam(required = true, value = "ID") String ID,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        MemberJgShxx data = commerceManagerService.queryCommerceDetail(ID);
        if ( data != null ) {
            map.put( "data", data );
        }  else {
            map.put( "data", "" );
        }
        return map;
    }


    @ApiOperation(value = "商会管理列表", notes = "数据获取")
    @ResponseBody
    @RequestMapping(value = "queryCommerceListTree", method = RequestMethod.POST)
    public List<MemberJgShxx> queryListTree(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<MemberJgShxx> data = commerceManagerService.queryCommerceList();
        return data;
    }


    @ApiOperation( value = "保存商会类别", notes = "保存商会类别" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "insert", required = false, name = "json", value = "商会类别表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveCommerceCategory", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveCommerceCategory(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShxx category = JSON.parseObject( json ).toJavaObject( MemberJgShxx.class );
        List<MemberJgShxx> check = commerceManagerService.checkCommerceName(category.getSHMC());
        if(check != null && check.size() > 0){
            result = new JsonResult( "商会类别名称已存在！" );
        }else{
            Integer flag = commerceManagerService.saveCommerceCategory( category );
            if ( flag > 0 ) {
                result = new JsonResult();
            }
            else {
                result = new JsonResult( "提交有误，请重新尝试" );
            }
        }
        return result;
    }


    @ApiOperation( value = "编辑商会类别", notes = "编辑商会类别" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "update", required = false, name = "json", value = "商会类别表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "editCommerceCategory", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult editCommerceCategory(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShxx category = JSON.parseObject( json ).toJavaObject( MemberJgShxx.class );
        Integer flag = commerceManagerService.editCommerceCategory( category );
        if ( flag > 0 ) {
            result = new JsonResult();
        }
        else {
            result = new JsonResult( "提交有误，请重新尝试" );
        }
        return result;
    }


    @ApiOperation( value = "删除商会类别", notes = "删除商会类别" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "delete", required = false, name = "ID", value = "商会类别ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "deleteCommerceCategory", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult deleteCommerceCategory(@RequestParam(required = true, value = "ID") String ID) {
        JsonResult result = new JsonResult();
        //判断是否有下属商会
        List<MemberJgShxx> data = commerceManagerService.queryCommerceData(ID);
        if(data != null && data.size() > 0){
            return result = new JsonResult("存在下属商会，不能删除");
        }
        commerceManagerService.deleteCommerce(ID);
        return result;
    }


    @ApiOperation( value = "保存商会", notes = "保存商会" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "insert", required = false, name = "json", value = "商会表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveCommerce", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveCommerce(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShxx shxx = JSON.parseObject( json ).toJavaObject( MemberJgShxx.class );
        List<MemberJgShxx> check = commerceManagerService.checkCommerceName(shxx.getSHMC());
        if(check != null && check.size() > 0){
            result = new JsonResult( "商会名称已存在！" );
        }else{
            Integer flag = commerceManagerService.saveCommerce( shxx );
            if ( flag > 0 ) {
                result = new JsonResult();
            }
            else {
                result = new JsonResult( "提交有误，请重新尝试" );
            }
        }
        return result;
    }


    @ApiOperation( value = "编辑商会", notes = "编辑商会" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "update", required = false, name = "json", value = "商会类别表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "editCommerce", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult editCommerce(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShxx category = JSON.parseObject( json ).toJavaObject( MemberJgShxx.class );
        Integer flag = commerceManagerService.editCommerce( category );
        if ( flag > 0 ) {
            result = new JsonResult();
        }
        else {
            result = new JsonResult( "提交有误，请重新尝试" );
        }
        return result;
    }


    @ApiOperation( value = "删除商会", notes = "删除商会" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "delete", required = false, name = "ID", value = "商会ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "deleteCommerce", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult deleteCommerce(@RequestParam(required = true, value = "ID") String ID) {
        JsonResult result = new JsonResult();
        //判断是否关联了此商会
        List<String> IDS = new ArrayList<>();
        IDS.add(ID);
        MemberJoinManager mjm = new MemberJoinManager();
        mjm.setJoinObjIdList(IDS);
        List<MemberJoinManager> data = appMemberJoinManagerService.queryByjoinObjIdList(mjm);
        if(data != null && data.size() > 0){
            return result = new JsonResult("存在下属商会，不能删除");
        }
        commerceManagerService.deleteCommerce(ID);
        return result;
    }

}
