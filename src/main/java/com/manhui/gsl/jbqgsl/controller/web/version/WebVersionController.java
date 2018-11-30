package com.manhui.gsl.jbqgsl.controller.web.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppVersion;
import com.manhui.gsl.jbqgsl.service.app.IAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( tags = "后台-app版本控制" )
@Controller
@RequestMapping( WebVersionController.ROOT_URL )
public class WebVersionController extends BaseController{
	public static final String ROOT_URL = PARENT_URL_WEB + "/version";
	
	@Resource
	private IAppVersionService iAppVersionService;
	
    @ApiOperation(value = "选择阵营页面", notes = "选择阵营页面")
    @RequestMapping(value = "/toSelectChannalPage", method = RequestMethod.GET)
    public String toSelectChannalPage() {
        return "/web/html/appVersion/appVersionTab"; 
    }
    
    
    @ApiOperation(value = "进入列表", notes = "进入列表")
    @RequestMapping(value = "/toListPage", method = RequestMethod.GET)
    public String versionListPages(HttpServletRequest request,String channal) {
    	request.setAttribute("channal", channal);
        return "/web/html/appVersion/appVersionList"; 
    }
    
    
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    @RequestMapping(value = "/appVersionList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>versionList( String channal,HttpServletRequest request,Integer pageIndex, Integer pageSize ) {
    	String version = request.getParameter("version");
        Map<String, Object> map = new HashMap<>();
        List<AppVersion> appVersionList = iAppVersionService.queryVersionList( version,channal,pageIndex, pageSize);
        Integer appVersionTotal = iAppVersionService.queryVersionCount( version ,channal,pageIndex, pageSize);
        if ( !appVersionList.isEmpty() ) { 
            map.put( "data", appVersionList );
            map.put( "total", appVersionTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    
    @ApiOperation(value = "删除数据", notes = "删除数据")
	@RequestMapping(value = "/deleteVersion", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteVersion(String id){
		JsonResult result = new JsonResult();
		iAppVersionService.deleteVersion(id);
		return result;
	}
    
    
    @ApiOperation(value = "跳转到新增", notes = "跳转到新增")
	@RequestMapping(value = "/toAddPage", method = RequestMethod.GET)
    public String addVersionPage(){
    	return "/web/html/appVersion/appVersionSave"; 
	}
    
    
    
    @ApiOperation(value = "跳转到编辑", notes = "跳转到编辑")
	@RequestMapping(value = "/toEditPage", method = RequestMethod.GET)
    public String eidtVersionPage(String id,HttpServletRequest request){
    	request.setAttribute("id", id);
    	return "/web/html/appVersion/appVersionSave"; 
	}
    
    
    @ApiOperation(value = "新增/修改数据", notes = "新增/修改数据")
	@RequestMapping(value = "/versionSave", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult versionSave(String json){
		JSONObject parseObject = JSON.parseObject(json);
		AppVersion appVersion = parseObject.toJavaObject(AppVersion.class);
		//判断是否有id
		if(appVersion.getId() != null&&!"".equals(appVersion.getId())){
			//更新
			appVersion.setUpdate_time(DateUtil.getTime());
			iAppVersionService.editVersion(appVersion);
		}else{
			AppVersion av = iAppVersionService.queryVersion(appVersion);
			if(av !=null) {//由该版本
				return new JsonResult(new RuntimeException("该版本号已经存在,请重新输入版本号"));
			}
			//新增
			appVersion.setId(UUIDUtil.getUUID());
			appVersion.setCreate_time(DateUtil.getTime());
			appVersion.setUpdate_time(DateUtil.getTime());
			iAppVersionService.saveVersion(appVersion);
		}
		return  new JsonResult();
	}
    
    
    @ApiOperation(value = "查询详细数据", notes = "查询详细数据")
    @RequestMapping(value = "/appVersion", method = RequestMethod.POST)
    @ResponseBody
    public AppVersion appVersion( String id,HttpServletRequest request ) {

    	AppVersion av = new AppVersion();
    	if(id != null && !"".equals(id)){
    		av = iAppVersionService.queryAppVersionDetail(id);
    		av.setFile(av.getUrl());
    	}
        return av;
    }
    
    @ApiOperation(value = "检查版本号重复问题", notes = "检查版本号重复问题")
    @RequestMapping(value = "/checkVersion", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkVersion( String version,String channal ) {
    	AppVersion av = new AppVersion();
    	av.setChannal(channal);
    	av.setVersion(version);
    	AppVersion aVersion = iAppVersionService.queryVersion(av);
    	if(aVersion !=null) {
    		return new JsonResult(new RuntimeException("该版本已经存在,请重新输入版本号"));
    	}
    	return  new JsonResult();
    }
}
