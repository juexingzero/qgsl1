package com.manhui.gsl.jbqgsl.controller.app.linkman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;
import com.manhui.gsl.jbqgsl.service.app.IAppTelephoneRecordService;
import com.manhui.gsl.jbqgsl.service.web.ILinkManService;;

/**
 * @类名称 AppLinkManController.java
 * @类描述 组织机构及对应联系人
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月22日 下午2:21:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月22日                创建
 *     1.01     kevin    2018年9月6日                  注销接口：getLinkManByPositionList
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "圈圈-组织机构及对应联系人" )
@Controller
@RequestMapping( AppLinkManController.ROOT_URL )
public class AppLinkManController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/linkman";
    @Resource
    private ILinkManService    linkManService;
    @Resource
    private IAppTelephoneRecordService    appTelephoneService;

    @ApiOperation( value = "获取部门及其下面的岗位列表", notes = "获取部门及其下面的岗位列表" )
    @RequestMapping( value = "getDeptList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getLinkManList() {
        return new JsonResult( linkManService.getDeptList() );
    }
    
    @ApiOperation( value = "获取部门下面的岗位以及对应的的联系人列表", notes = "获取部门下面的岗位以及对应的的联系人列表" )
    @ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "dept_id", value = "部门id", required = true, dataType = "字符串")
	})
    @RequestMapping( value = "getPositionAndUser", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getPositionAndUser(String dept_id) {
    	return new JsonResult( linkManService.getPositionAndUser(dept_id) );
    }
  
    @ApiOperation( value = "获取部门及岗位列表旧版本", notes = "获取部门及岗位列表旧版本" )
    @RequestMapping( value = "getLinkManList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getDeptAndPositionList() {
    	return new JsonResult( linkManService.getDeptAndPositionList() );
    }
    @ApiOperation( value = "记录打电话历史", notes = "记录打电话历史" )
    @ApiImplicitParams({
  		@ApiImplicitParam(paramType = "query", name = "caller_id", value = "呼叫方id", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "caller_phone", value = "呼叫方电话", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "called_phone", value = "被呼叫方电话", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "called_id", value = "被呼叫方id", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "dept_id", value = "被呼叫方部门id", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "dept_name", value = "被呼叫方部门", required = true, dataType = "字符串"),
  		@ApiImplicitParam(paramType = "query", name = "position", value = "被呼叫方部门职务", required = true, dataType = "字符串"),
  	})
    @RequestMapping( value = "saveRecordCallPhone", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveRecordCallPhone(String caller_id,String caller_phone,String called_id,String called_name,String called_phone,String dept_id,String dept_name,String position) {
    	TelephoneRecord tr = new TelephoneRecord();
    	tr.setCaller_id(caller_id);
    	tr.setCaller_phone(caller_phone);
    	tr.setCalled_id(called_id);
    	tr.setCalled_phone(called_phone);
    	tr.setCalled_dept_id(dept_id);
    	tr.setCalled_dept_name(dept_name);
    	tr.setCalled_post(position);
    	tr.setCalled_name(called_name);
    	appTelephoneService.save(tr);
    	return new JsonResult();
    }
    @ApiOperation( value = "最近联系人列表", notes = "最近联系人列表" )
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "caller_id", value = "呼叫方id", required = true, dataType = "字符串")
    })
    @RequestMapping( value = "lastTimeLinkMan", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult lastTimeLinkMan(String caller_id) {
    	Map<String,Object> dataMap = new HashMap<>();
    	TelephoneRecord tr = new TelephoneRecord();
    	tr.setCaller_id(caller_id);
    	List<TelephoneRecordResult> TelephoneRecordResultList = appTelephoneService.queryLastTimeLinkManList(tr);
    	if(TelephoneRecordResultList !=null && !TelephoneRecordResultList.isEmpty()) {
    		dataMap.put("data", TelephoneRecordResultList);
    	}
    	return new JsonResult(dataMap);
    }
    @ApiOperation( value = "圈子- -姓名搜索", notes = "圈子- -姓名搜索" )
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "link_name", value = "模糊人名", required = true, dataType = "字符串")
    })
    @RequestMapping( value = "queryLinMan", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult queryLinMan(String link_name) {
    	Map<String,Object> dataMap = new HashMap<>();
    	TelephoneRecord tr = new TelephoneRecord();
    	tr.setCalled_name(link_name);
    	List<LinkManResult> TelephoneRecordResultList = linkManService.queryLinkManList(tr);
    	if(TelephoneRecordResultList !=null && !TelephoneRecordResultList.isEmpty()) {
    		dataMap.put("data", TelephoneRecordResultList);
    	}
    	return new JsonResult(dataMap);
    }

    
    
    /*
    @ApiOperation( value = "获取岗位下对应的联系人列表", notes = "获取岗位下对应的联系人列表" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "position_id", value = "岗位ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getLinkManByPositionList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getLinkManByPositionList(@RequestParam(value = "position_id", required = true)String position_id) {
        return new JsonResult( linkManService.getLinkManByPositionList(position_id) );
    }
    */
}
