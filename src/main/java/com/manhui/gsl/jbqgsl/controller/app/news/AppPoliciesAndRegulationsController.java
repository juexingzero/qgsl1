

package com.manhui.gsl.jbqgsl.controller.app.news;

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
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.service.app.IAppPoliciesAndRegulationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Title: AppCompanyStyleController.java
* @Package com.manhui.gsl.jbqgsl.controller.app
* @Description: TODO(app端首页--企业风采)
* @author LiuBin
* @date 2018年8月31日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Api(tags = "首页--政策法规")
@Controller
@RequestMapping(AppPoliciesAndRegulationsController.ROOT_URL)
@ResponseBody
public class AppPoliciesAndRegulationsController extends BaseController{
	public static final String ROOT_URL = PARENT_URL_APP + "/policiesAndRegulations";
	
	
	@Resource
	private IAppPoliciesAndRegulationsService iAppPoliciesAndRegulationsService;

	
	
	@ApiOperation( value = "展示江北区部门", notes = "展示江北区部门" )
	@RequestMapping( value = "/queryOutDeptList", method = RequestMethod.POST )
	public JsonResult queryOutDeptList() {
		List<Dept> deptList = iAppPoliciesAndRegulationsService.queryOutDeptList();
		Map<String,Object> dataMap = new HashMap<>();
		if(deptList !=null && deptList.size()>0) {
			dataMap.put("deptList",deptList);
		}
		return new JsonResult(dataMap);
	}
	@ApiOperation( value = "模糊查询江北区部门", notes = "模糊查询江北区部门" )
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "dept_name", value = "部门名称", required = true, dataType = "字符串")
		
	})
	@RequestMapping( value = "/queryOutDept", method = RequestMethod.POST )
	public JsonResult queryOutDept(
			@RequestParam(value = "dept_name", required = true) String dept_name) {
		List<Dept> deptList = iAppPoliciesAndRegulationsService.queryOutDepts(dept_name);
		Map<String,Object> dataMap = new HashMap<>();
		if(deptList !=null && deptList.size()>0) {
			dataMap.put("deptList",deptList);
		}
		return new JsonResult(dataMap);
	}
}
