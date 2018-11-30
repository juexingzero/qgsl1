package com.manhui.gsl.jbqgsl.controller.app.commerce;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppCompanyElegantController.java
 * @类描述 商会管理
 * @作者 LiuBIn kwmo1005@163.com
 * @创建时间 2018年11月1日 上午9:23:21
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
@Api( tags = "商会管理" )
@Controller
@RequestMapping( AppCommerceController.ROOT_URL )
public class AppCommerceController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/commerce";
    @Resource
    private IAppCommerceService   commerceService;

    @ApiOperation( value = "商会", notes = "商会" )
    @RequestMapping( value = "getCommerceList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getCommerceList(){
    	List<Commerce> commerce = commerceService.queryCommerceList();
        return new JsonResult(commerce);
    }
}
