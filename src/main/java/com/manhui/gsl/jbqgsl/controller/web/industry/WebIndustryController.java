package com.manhui.gsl.jbqgsl.controller.web.industry;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.Industry;
import com.manhui.gsl.jbqgsl.service.web.IIndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebIndustryController.java
 * @类描述 <pre>行业数据模块controller层，主要负责请求的接收及响应</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年8月8日11:35:11
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年8月8日                创建
 *     ----------------------------------------------
 * </pre>
 */
@Api( tags = "后台-行业信息" )
@Controller
@RequestMapping( WebIndustryController.ROOT_URL )
public class WebIndustryController extends BaseController {
	
	public static final String    ROOT_URL = PARENT_URL_WEB + "/industry";
	@Resource
	private IIndustryService iIndustryService;
	 
    @ApiOperation(value = "查询所有行业数据", notes = "查询所有行业数据")
    @RequestMapping(value = "queryAllIndustry")
    @ResponseBody
    public List<Industry> queryAllIndustry() {
    	List<Industry> data = iIndustryService.queryAllIndustry(); 
        return data;
    }
}
