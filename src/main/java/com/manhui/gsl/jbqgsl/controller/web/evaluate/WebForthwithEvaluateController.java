package com.manhui.gsl.jbqgsl.controller.web.evaluate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluateDetail;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.service.web.IForthwithEvaluateService;
import com.manhui.gsl.jbqgsl.service.web.IInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebForthwithEvaluateController.java
 * @类描述 <pre>即时评价模块controller层，主要负责请求的接收及响应</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年8月10日11:09:44
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年8月10日                创建
 *     ----------------------------------------------
 * </pre>
 */
@Api( tags = "后台-即时评价" )
@Controller
@RequestMapping( WebForthwithEvaluateController.ROOT_URL )
public class WebForthwithEvaluateController extends BaseController {
	public static final String    ROOT_URL = PARENT_URL_WEB + "/forthwitheva";
	
	@Resource
    private IForthwithEvaluateService iForthwithEvaluateService;
	
	@ApiOperation(value = "进入即时评价切换页面", notes = "进入即时评价切换页面")
    @RequestMapping(value = "toForthwithTabPage", method = RequestMethod.GET)
    public String userListPage() {
        return "/web/html/forthwitheva/forthwithevaTabs"; 
    }
	
	
    @ApiOperation(value = "分别进入未评价列表", notes = "分别进入未评价列表")
    @RequestMapping(value = "toUnevaluateListPages", method = RequestMethod.GET)
    public String toUnevaluateListPages() {
        return "/web/html/forthwitheva/unevaluateList"; 
    }
    
    
    @ApiOperation(value = "分别进入已评价列表", notes = "分别进入已评价列表")
    @RequestMapping(value = "toEvaluateListPages", method = RequestMethod.GET)
    public String toEvaluateListPages() {
        return "/web/html/forthwitheva/evaluateList"; 
    }
    
    
    @ApiOperation(value = "查询未评价列表数据", notes = "查询未评价列表数据")
    @RequestMapping(value = "unevaluateList" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> unevaluateList( HttpServletRequest request,Integer pageIndex, Integer pageSize  ) {
    	String evaluate_active_name = request.getParameter("evaluate_active_name");
        Map<String, Object> map = new HashMap<>();
        pageIndex = pageIndex * pageSize;
        List<ForthwithEvaluate> unforthwithEvaluate = iForthwithEvaluateService.queryUnevaluateList( evaluate_active_name,pageIndex,pageSize );
        Integer unevaluateTotal = iForthwithEvaluateService.queryUnevaluateTotal( evaluate_active_name,pageIndex,pageSize );
        if ( !unforthwithEvaluate.isEmpty() ) {
            map.put( "data", unforthwithEvaluate );
            map.put( "total", unevaluateTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    
    @ApiOperation(value = "未评价查看", notes = "未评价查看")
    @RequestMapping(value = "toUnevaluatePage", method = RequestMethod.GET)
    public String toUnevaluatePage(String forthwith_id, HttpServletRequest request) {
    	//查询出标准ID
    	ForthwithEvaluate  fe = iForthwithEvaluateService.queryForthwithEvaluate(forthwith_id);
    	request.setAttribute("forthwith_id", forthwith_id);
    	request.setAttribute("passive_id", fe.getPassive_id());
    	request.setAttribute("active_id", fe.getActive_id());
        return "/web/html/forthwitheva/unForthwithevaDetail"; 
    }
    
    
    @ApiOperation(value = "评价查看详细信息", notes = "评价查看详细信息")
    @RequestMapping(value = "evaluateDetail", method = RequestMethod.POST)
    @ResponseBody
    public Map evaluateDetail(String forthwith_id,String passive_id,String active_id) {
    	//查询出标准ID
    	Map  fe = iForthwithEvaluateService.queryEvaluate(forthwith_id,passive_id,active_id);
        return fe; 
    }
    
    
    @ApiOperation(value = "评价标准细则关联", notes = "评价标准细则关联")
    @RequestMapping(value = "showFlowingScore", method = RequestMethod.POST)
    @ResponseBody
    public List<EvaluateFlowingScore> showFlowingScore(String forthwith_id,String passive_id,String active_id) {
    	//查询出标准ID
    	List<EvaluateFlowingScore>  datalist = iForthwithEvaluateService.queryScoresDetail(forthwith_id,passive_id,active_id);
        return datalist; 
    }
    
    
    @ApiOperation(value = "查询已评价列表数据", notes = "查询已评价列表数据")
    @RequestMapping(value = "evaluateList" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> evaluateList( HttpServletRequest request ,Integer pageIndex, Integer pageSize) {
    	String evaluate_active_name = request.getParameter("evaluate_active_name");
        Map<String, Object> map = new HashMap<>();
        pageIndex = pageIndex * pageSize;
        List<ForthwithEvaluate> forthwithEvaluateList = iForthwithEvaluateService.queryEvaluateList( evaluate_active_name,pageIndex,pageSize );
        Integer evaluateTotal = iForthwithEvaluateService.queryEvaluateTotal( evaluate_active_name,pageIndex,pageSize );
        if ( !forthwithEvaluateList.isEmpty() ) {
            map.put( "data", forthwithEvaluateList );
            map.put( "total", evaluateTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    
    @ApiOperation(value = "已评价查看", notes = "已评价查看")
    @RequestMapping(value = "toEvaluatePage", method = RequestMethod.GET)
    public String toEvaluatePage(String forthwith_id, HttpServletRequest request) {
    	//查询出标准ID
      	ForthwithEvaluate  fe = iForthwithEvaluateService.queryForthwithEvaluate(forthwith_id);
    	request.setAttribute("forthwith_id", forthwith_id);
    	request.setAttribute("passive_id", fe.getPassive_id());
    	request.setAttribute("active_id", fe.getActive_id());
        return "/web/html/forthwitheva/forthwithevaDetail";
    }
    
    
    @ApiOperation(value = "更新评价回复", notes = "更新评价回复")
    @RequestMapping(value = "updateAnswer" , method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateAnswer( String suggest_id,String suggest_answer ) {
    	iForthwithEvaluateService.updateSuggestAnswer(suggest_id, suggest_answer);
        return new JsonResult();
    }
	
}
