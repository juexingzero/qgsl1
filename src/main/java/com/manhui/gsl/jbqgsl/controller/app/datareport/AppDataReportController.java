package com.manhui.gsl.jbqgsl.controller.app.datareport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.NumberFromateUtil;
import com.manhui.gsl.jbqgsl.common.util.ParamCheckUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.datareport.result.DataReportList;
import com.manhui.gsl.jbqgsl.model.datareport.DataEnterprise;
import com.manhui.gsl.jbqgsl.service.app.datareport.IAppDataReportService;
import com.manhui.gsl.jbqgsl.service.app.memberapproval.IAppMemberApprovalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppDataReportController.java
 * @类描述 数据上报
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
@Api(tags = "数据上报")
@Controller
@RequestMapping(AppDataReportController.ROOT_URL)
public class AppDataReportController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/dataReport";
	@Resource
	private IAppDataReportService iAppDataReportService;
	@Resource
	private IAppMemberApprovalService iAppMemberApprovalService;
	
	@ApiOperation(value = "数据上报--待处理", notes = "数据上报--待处理")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串") })
	@RequestMapping(value = "getDataReportList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getDataReportList(@RequestParam(value = "user_id", required = true) String user_id) {
		List<DataReportList> dataList = iAppDataReportService.queryDataReportList(user_id);
		return new JsonResult(dataList);
	}
	
	@ApiOperation(value = "数据上报--历史记录", notes = "数据上报--历史记录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串") })
	@RequestMapping(value = "getAlreadyDataReportList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getAlreadyDataReportList(@RequestParam(value = "user_id", required = true) String user_id) {
		List<DataReportList> dataList = iAppDataReportService.getAlreadyDataReportList(user_id);
		return new JsonResult(dataList);
	}
	
	@ApiOperation(value = "数据上报--待上报--信息填写保存", notes = "数据上报--待上报--信息填写保存")
	@RequestMapping(value = "saveDataReport", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveDataReport(DataEnterprise dataEnterprise) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		//数据验证--主营业务1
		if(ParamCheckUtil.isEmpty(dataEnterprise.getZyywly1())||ParamCheckUtil.isEmpty(dataEnterprise.getZyszebl1())) {
			dataMap.put("state", "0");
			dataMap.put("message", "主营业务1,以及占营收总额比例必填不能为空");
		}
	//验证总营业额占比为1
//		String zyye1 = NumberFromateUtil.encrypt2(dataEnterprise.getZyszebl1());
//		String zyye2 = NumberFromateUtil.encrypt2(dataEnterprise.getZyszebl2());
//		String zyye3 = NumberFromateUtil.encrypt2(dataEnterprise.getZyszebl3());
//		
//		double parseDouble1 = Double.parseDouble(zyye1);
//		double parseDouble2 = Double.parseDouble(zyye2);
//		double parseDouble3 = Double.parseDouble(zyye3);
//		if(parseDouble1+parseDouble2+parseDouble3 !=1) {
//			dataMap.put("state", "0");
//			dataMap.put("message", "占营收总额比例（%）：只允许填写正小数（如0.2）或正百分比数（20%），最多两位小数，三项主营业务员领域之和必须=1（100%）");
//			return new JsonResult(dataMap);
//		}
		//验证该发起的模板下该企业是否已经填写
		String state = iAppDataReportService.queryDataRelationState(dataEnterprise.getId());
		if("1".equals(state)) {
			dataMap.put("state", "0");
			dataMap.put("message", "您已经填写过了");
			return new JsonResult(dataMap);
		}
		int num = iAppDataReportService.saveDataReportList(dataEnterprise);
		
		if(num==1) {
			dataMap.put("state", "1");
			dataMap.put("message", "保存成功");
		}else {
			dataMap.put("state", "0");
			dataMap.put("message", "保存失败");
		}
		return new JsonResult(dataMap);
	}
	
	@ApiOperation(value = "数据上报--查看历史详情", notes = "数据上报--查看历史详情")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "data_report_id", value = "模板-数据id", required = true, dataType = "字符串")
	})
	@RequestMapping(value = "showDataReportDetail", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult showDataReportDetail(String data_report_id) {
		DataEnterprise enterprise = iAppDataReportService.queryMemberDataEnterprise(data_report_id);
		return new JsonResult(enterprise);
	}
	@ApiOperation(value = "数据上报--介绍", notes = "数据上报--介绍")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", required = true, name = "template_id", value = "模板ID", dataType = "字符串") })
	@RequestMapping(value = "dataTemplateIntroduce", method = RequestMethod.GET)
	public String dataTemplateIntroduce(HttpServletRequest request,@RequestParam(value = "template_id", required = true)String template_id) {
		Map<String,Object> dataMap = iAppDataReportService.dataTemplateIntroduce(template_id);
		request.setAttribute("requestUrl",String.valueOf( request.getRequestURL() ).split( "app" )[0]);
		request.setAttribute("dataMap", dataMap);
		return "/web/html/datareport/app/templeteIntroduce";
	}
	
	
}
