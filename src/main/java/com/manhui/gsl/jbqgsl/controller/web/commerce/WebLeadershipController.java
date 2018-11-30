package com.manhui.gsl.jbqgsl.controller.web.commerce;

import com.alibaba.fastjson.JSON;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShldbz;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import com.manhui.gsl.jbqgsl.service.web.commerce.ILeadershipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名称 WebLeadershipController.java
 * @类描述 领导班子
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月5日16:42:04
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年11月5日      创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-领导班子" )
@Controller
@RequestMapping( WebLeadershipController.ROOT_URL )
public class WebLeadershipController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_WEB + "/leadership";

    @Resource
    private ILeadershipService leadershipService;

    @ApiOperation( value = "进入总商会领导班子列表页面", notes = "进入总商会领导班子列表页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串")
    })
    @RequestMapping( value = "toAllLeadershipPage", method = RequestMethod.GET )
    public String toAllLeadershipPage(@RequestParam(required = true, value = "SHBMID") String SHBMID, HttpServletRequest request) {
        request.setAttribute("shbmid",SHBMID);
        return "/web/html/commerce/allLeadershipList";
    }

    @ApiOperation(value = "总商会领导班子列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串")
    })
    @RequestMapping(value = "queryAllLeadershipListTree", method = RequestMethod.POST)
    public Map<String, Object> queryAllLeadershipListTree(HttpServletRequest request,String SHBMID) {
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> data  = new HashMap<>();
        data.put("SHBMID",SHBMID);
        List<LeadershipResult> results = leadershipService.queryLeadershipList(data);
        if ( !results.isEmpty() ) {
            map.put( "data", results );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }


    @ApiOperation( value = "进入领导班子列表页面", notes = "进入领导班子列表页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串")
    })
    @RequestMapping( value = "toLeadershipPage", method = RequestMethod.GET )
    public String toLeadershipPage(@RequestParam(required = true, value = "SHBMID") String SHBMID, HttpServletRequest request) {
        request.setAttribute("shbmid",SHBMID);
        return "/web/html/commerce/leadershipList";
    }



    @ApiOperation(value = "领导班子列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串")
    })
    @RequestMapping(value = "queryLeadershipListTree", method = RequestMethod.POST)
    public Map<String,Object> queryLeadershipListTree(HttpServletRequest request,String SHBMID) {
        List<Map<String,Object>> results = leadershipService.queryLeadershipDetailList(SHBMID);
        Map<String,Object> data = new HashMap<>();
        if(results != null && results.size() > 0){
            if(results.get(0).get("xm") != null ||results.get(0).get("yddh") != null ){
                data.put("xm",results.get(0).get("xm"));
                data.put("yddh",results.get(0).get("yddh"));
            }else{
                data.put("xm","");
                data.put("yddh","");
            }
            if(results.get(1) != null){
                if(results.get(1).get("xm") != null || results.get(1).get("yddh") != null){
                    data.put("xm1",results.get(1).get("xm"));
                    data.put("yddh1",results.get(1).get("yddh"));
                }else{
                    data.put("xm1","");
                    data.put("yddh1","");
                }
            }else{
                data.put("xm1","");
                data.put("yddh1","");
            }
        }else{
            data.put("xm","");
            data.put("yddh","");
            data.put("xm1","");
            data.put("yddh1","");
        }
        return data;
    }


/*    @ApiOperation( value = "进入领导班子编辑页面", notes = "进入领导班子编辑页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "ID", value = "领导班子ID", dataType = "字符串") })
    @RequestMapping( value = "toLeadershipEditPage", method = RequestMethod.GET )
    public String toLeadershipEditPage(@RequestParam(required = true, value = "ID") String ID, HttpServletRequest request) {
        request.setAttribute("ID",ID);
        request.setAttribute("flag","edit");
        return "/web/html/commerce/leadershipAdd";
    }*/


    @ApiOperation( value = "进入领导班子详细页面", notes = "进入领导班子详细页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "JIE", value = "届别", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "PX", value = "排序顺序", dataType = "字符串")
    })
    @RequestMapping( value = "toLeadershipDetailPage", method = RequestMethod.GET )
    public String toLeadershipDetailPage(@RequestParam(required = true, value = "SHBMID") String SHBMID,
                                         @RequestParam(required = true, value = "JIE") String JIE,
                                         @RequestParam(required = true, value = "PX") String PX,
                                         HttpServletRequest request) {
        request.setAttribute("shbmid",SHBMID);
        request.setAttribute("jie",JIE);
        request.setAttribute("px",PX);
        return "/web/html/commerce/leadershipDetail";
    }


    @ApiOperation(value = "领导班子详细列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "JIE", value = "届别", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "SHZWLX", value = "职务类型", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "XM", value = "领导姓名", dataType = "字符串")
    })
    @RequestMapping(value = "queryLeadershipDetail", method = RequestMethod.POST)
    public List<Map<String,Object>> queryLeadershipDetail(@RequestParam(required = true, value = "SHBMID") String SHBMID,
                                                     @RequestParam(required = true, value = "JIE") String JIE, String SHZWLX, String XM,
                                                     HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("SHBMID",SHBMID);
        map.put("JIE",JIE);
        map.put("SHZWLX",SHZWLX);
        map.put("XM",XM);
        List<Map<String,Object>> data = leadershipService.queryLeadership(map);
        return data;
    }


    @ApiOperation( value = "保存领导班子", notes = "保存领导班子" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "insert", required = false, name = "json", value = "领导表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveLeadership", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveLeadership(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShldbz data = JSON.parseObject( json ).toJavaObject( MemberJgShldbz.class );
        Integer flag = leadershipService.saveLeadership(data);
        if ( flag > 0 ) {
            result = new JsonResult();
        }
        else {
            result = new JsonResult( "提交有误，请重新尝试" );
        }
        return result;
    }


    /*@ApiOperation( value = "编辑领导班子", notes = "编辑领导班子" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "update", required = false, name = "json", value = "领导表单", dataType = "字符串" )
    } )
    @RequestMapping( value = "editLeadership", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult editLeadership(String json ) {
        JsonResult result = new JsonResult();
        MemberJgShldbz data = JSON.parseObject( json ).toJavaObject( MemberJgShldbz.class );
        Integer flag = leadershipService.editLeadership( data );
        if ( flag > 0 ) {
            result = new JsonResult();
        }
        else {
            result = new JsonResult( "提交有误，请重新尝试" );
        }
        return result;
    }*/


    @ApiOperation( value = "删除领导班子", notes = "删除领导班子" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "delete", required = false, name = "ID", value = "ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "deleteLeadership", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult deleteLeadership(@RequestParam(required = true, value = "ID") String ID) {
        JsonResult result = new JsonResult();
        Integer flag = leadershipService.deleteLeadership(ID);
        if ( flag > 0 ) {
            result = new JsonResult();
        }
        else {
            result = new JsonResult( "提交有误，请重新尝试" );
        }
        return result;
    }


    @ApiOperation( value = "进入领导班子新增页面", notes = "进入领导班子新增页面" )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "SHBMID", value = "商会ID", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "JIE", value = "届别", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "PX", value = "排序", dataType = "字符串")
    })
    @RequestMapping( value = "toLeadershipAddPage", method = RequestMethod.GET )
    public String toLeadershipAddPage(@RequestParam(required = true, value = "SHBMID")String SHBMID,
                                      @RequestParam(required = true, value = "JIE")String JIE,
                                      @RequestParam(required = true, value = "PX")String PX,
                                      HttpServletRequest request) {
        System.out.println(SHBMID + "-------------------" + JIE);
        request.setAttribute("shbmid",SHBMID);
        request.setAttribute("jie",JIE);
        request.setAttribute("px",PX);
        request.setAttribute("flag","add");
        return "/web/html/commerce/leadershipAdd";
    }


    @ApiOperation(value = "领导班子添加列表", notes = "数据获取")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "XM", value = "姓名", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "YDDH", value = "手机号码", dataType = "字符串")
    })
    @RequestMapping(value = "queryLeadershipAddList", method = RequestMethod.POST)
    public List<Map<String,Object>> queryLeadershipAddList(String XM, String YDDH,
                                                          HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("XM", XM);
        map.put("YDDH", YDDH);
        List<Map<String, Object>> data = leadershipService.queryLeaderMemberInfo(map);
        return data;
    }


    @ApiOperation( value = "保存领导班子及人员", notes = "保存领导班子" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "insert", required = false, name = "shbmid", value = "商会ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "insert", required = false, name = "jie", value = "届别", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "insert", required = false, name = "shzwlx", value = "职务类型", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "insert", required = false, name = "px", value = "排序", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "insert", required = false, name = "id", value = "人员ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveLeaderMember", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveLeaderMember(@RequestParam(required = true, value = "shbmid")String shbmid,
                                       @RequestParam(required = true, value = "jie")String jie,
                                       @RequestParam(required = true, value = "shzwlx")String shzwlx,
                                       @RequestParam(required = true, value = "px")String px,
                                       @RequestParam(required = true, value = "id")String id) {
        JsonResult result = new JsonResult();
        String[] iddata = id.split(",");
        Integer flag = 0;
        String message = "";
        for(String ids : iddata){
            MemberJgShldbz data = new MemberJgShldbz();
            data.setJIE(jie);
            data.setSHBMID(shbmid);
            data.setSHZWLX(shzwlx);
            data.setPX(px);
            data.setRYBM(ids);
            flag = leadershipService.saveLeadership(data);
            if(flag == 0){
                message = "该职位最多由一人担任，请重新选择！";
            }
            if(flag == 2){
                message = "该领导已经有其他职务，请重新选择！";
            }
        }
        return result = new JsonResult(message);
    }
}
