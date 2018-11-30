package com.manhui.gsl.jbqgsl.controller.app.version;

import java.text.ParseException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppVersion;
import com.manhui.gsl.jbqgsl.service.app.IAppVersionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *	手机版本号管理
 */
@Controller
public class AppVersionController extends BaseController{

	public static final String ROOT_URL = PARENT_URL_APP + "/version";
	
	
	@Resource
	private IAppVersionService iAppVersionService;

	
	/**
     * 查询数据库中 版本号
     * @param
     * @return
     */
	@ApiOperation(value = "获取到数据库中最新版本", notes = "获取到数据库中最新版本")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "channal", value = "安卓2 苹果1", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "version", value = "版本号", required = true, dataType = "字符串")
	})
    @RequestMapping( value = ROOT_URL+"/queryVersion", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult queryAppVersion(String channal,String version) throws ParseException {
    		AppVersion av = iAppVersionService.queryVersion(channal);
    		if(av.getVersion().compareTo(version)>0) {
    			return new JsonResult(av);
    		}else {
    			return new JsonResult(new RuntimeException("你是最新版本"));
    		}
    }
	/**
	 * 下载最新
	 * @param
	 * @return
	 */
	@ApiOperation(value = "下载最新", notes = "下载最新")
	@RequestMapping( value = ROOT_URL+"/loadApp", method = RequestMethod.POST )
	@ResponseBody
	public JsonResult loadApp(String channel,int flag)  {
		AppVersion av = iAppVersionService.load(channel,flag);
		return new JsonResult(av);
	}
    
	
    
}
