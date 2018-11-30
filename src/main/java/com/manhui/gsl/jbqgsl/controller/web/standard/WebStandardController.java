
/**
* @Title: WebBiDirectionalEvaluationStandardLibararyController.java
* @Package com.manhui.gsl.jbqgsl.controller.web
* @Description: TODO(双向评价标准库设置)
* @author LiuBin
* @date 2018年8月7日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.controller.web.standard;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.service.web.IBiDiStandardService;
import com.manhui.gsl.jbqgsl.service.web.IEvaluateDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( tags="后台-双向评价-标准库设置")
@Controller
@RequestMapping(WebStandardController.ROOT_URL)
public class WebStandardController extends BaseController{
	 public static final String    ROOT_URL = PARENT_URL_WEB + "/standradLibarary";
	 @Resource
	  private  IBiDiStandardService iBiDiStandardService;
	 @Resource
	 private IEvaluateDetailService iEvaluateDetailService;
	 
	   @ApiOperation(value = "进入双向评价-标准库设置", notes = "进入标准库设置页面")
	   @RequestMapping(value="toStandardLibraryList", method = RequestMethod.GET)
	   public String toStandardLibraryDesign() {
	        return "/web/html/BiDirectionalEvaluation/standardLibraryList";
	    }
	   
	   @ApiOperation(value = "进入双向评价-标准库添加页面流转", notes = "添加按钮页面流转")
	   @RequestMapping(value="toStandardLibraryAdd", method = RequestMethod.GET)
	   public String toStandardLibraryAdd() {
		   return "/web/html/BiDirectionalEvaluation/standardLibraryAdd";
	   }

	   @ApiOperation(value = "进入双向评价-标准库添加页面流转", notes = "添加按钮页面流转")
	   @RequestMapping(value="toStandardLibraryEdit", method = RequestMethod.GET)
	   public String toStandardLibraryEdit(String standard_id,HttpServletRequest request) {
		   request.setAttribute("standard_id", standard_id);
		   return "/web/html/BiDirectionalEvaluation/standardLibraryEdit";
	   }

	   @ApiOperation(value = "进入双向评价-列表展示", notes = "列表展示")
	   @RequestMapping(value = "listStandard" , method = RequestMethod.POST)
	   @ResponseBody
	    public Map<String, Object> listStandard( 
	        @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
	        @RequestParam(required = false, value = "pageSize") Integer pageSize ,EvaluateStandard evaluStandard) {
	        Map<String, Object> map = new HashMap<>();
	        if ( pageIndex != null && pageSize != null ) {
	            evaluStandard.setPageNo( pageIndex * pageSize );
	            evaluStandard.setPageSize( pageSize );
	        }
	        List<EvaluateStandard> evaluateStandardsList = iBiDiStandardService.queryEvaluateStandards( evaluStandard );
	        Integer total = iBiDiStandardService.queryevaluateStandardsCount( evaluStandard );
	        if ( !evaluateStandardsList.isEmpty() ) {
	            map.put( "data", evaluateStandardsList );
	            map.put( "total", total );
	        }
	        else {
	            map.put( "data", "" );
	        }
	        return map;
	    }
	 
	   @ApiOperation(value = "进入双向评价-添加评价标准", notes = "添加评价标准")
	   @RequestMapping(value = "save" , method = RequestMethod.POST)
	   @ResponseBody
	   public JsonResult addStandard(String json) {
		   System.err.println("---json:"+json);
		   //System.err.println("---treeJson:"+treeJson);
		   JSONObject parseObject = JSON.parseObject(json);
		   EvaluateStandard evaluateStandard = parseObject.toJavaObject(EvaluateStandard.class);
		   /*JSONArray parseArray = JSON.parseArray(treeJson);
		   List<EvaluateDeatil> evaluateDeatilList = parseArray.toJavaList(EvaluateDeatil.class);*/
		   String describe = "";
		   List<String> nameList = iEvaluateDetailService.queryDetailNameByLevel2(evaluateStandard.getStandard_id());
		   for(String data : nameList){
			   describe = describe + data + "、";
		   }
		   //1级保存
		   evaluateStandard.setStandard_id(UUIDUtil.getUUID());
		   evaluateStandard.setStandard_plan_score(0);
		   if(evaluateStandard.getStandard_belonged().isEmpty()){
			   evaluateStandard.setStandard_belonged("1");
		   }
		   //evaluateStandard.setStandard_describe(describe.substring(0,describe.length()-1));
		   evaluateStandard.setCreate_time(DateUtil.getTime());
		   evaluateStandard.setUpdate_time(DateUtil.getTime());
		   iBiDiStandardService.save(evaluateStandard);
		   
		   //2级，3级保存
		   /*for(EvaluateDeatil data : evaluateDeatilList){
			   data.setStandard_id(evaluateStandard.getStandard_id());
			   data.setDetail_id(UUIDUtil.getUUID());
			   data.setDetail_level(1);
			   data.setCreate_time(DateUtil.getTime());
			   data.setUpdate_time(DateUtil.getTime());
			   iEvaluateDetailService.save(data);
			   for(EvaluateDeatil detail : data.getEvaluateDetailChildList()){
				   detail.setDetail_id(UUIDUtil.getUUID());
				   detail.setStandard_id(evaluateStandard.getStandard_id());
				   detail.setP_detail_id(data.getDetail_id());
				   detail.setDetail_level(2);
				   detail.setCreate_time(DateUtil.getTime());
				   detail.setUpdate_time(DateUtil.getTime());
				   iEvaluateDetailService.save(detail);
			   }
		   }*/
		  
		   return new JsonResult();
	   }
	  
	   @ApiOperation(value = "条件查询评价标准", notes = "条件查询评价标准")
	   @RequestMapping(value = "queryStandardCondition", method = RequestMethod.POST )
	   @ResponseBody
	    public Map<String, Object> queryStandardCondition(EvaluateStandard evaluateStandard) {
	        Map<String, Object> map = new HashMap<>();
	        List<Map<String,Object>> evaluateStandardsConditionList= iBiDiStandardService.queryEvaluateStandardsByCondition(evaluateStandard);
	        if ( !evaluateStandardsConditionList.isEmpty() ) {
	            map.put( "data", evaluateStandardsConditionList );
	        }
	        else {
	            map.put( "data", "" );
	        }
	        return map;
	    }
	   
	   
	    @ApiOperation(value = "删除数据", notes = "删除数据")
		@RequestMapping(value = "deleteEvaluateStandards" , method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResult deleteInstitution(String standard_id){
			JsonResult result = new JsonResult();
			//判断是否启用
			EvaluateStandard es = iBiDiStandardService.queryEvaluateStandard(standard_id);
			if("1".equals(es.getIs_effect())){
				//已经启用，无法删除
				return result = new JsonResult("标准已经启用，请停用后删除!");
			}
			result = iBiDiStandardService.deleteEvaluateStandards(standard_id);
			return result;
		}
	    
	    
	    
	    @ApiOperation(value = "展示标准接口", notes = "展示标准接口")
		@RequestMapping(value = "showEvaluateStandards" , method = RequestMethod.POST)
	    @ResponseBody
	    public EvaluateStandard showEvaluateStandards(String standard_id){
	    	EvaluateStandard data = iBiDiStandardService.showEvaluateStandard(standard_id);
	    	return data;
	    }


	    @ApiOperation(value = "展示评价标准详细接口", notes = "展示评价标准详细接口")
		@RequestMapping(value = "evaluateStandardsDetail" , method = RequestMethod.POST)
	    @ResponseBody
	    public EvaluateStandard evaluateStandardsDetail(String standard_id){
	    	EvaluateStandard data = iBiDiStandardService.queryEvaluateStandard(standard_id);
	    	return data;
	    }
	    
	    
	    @ApiOperation(value = "跳转到编辑评价标准详细", notes = "跳转到编辑评价标准详细")
		@RequestMapping(value = "toStandardsDetailPage" , method = RequestMethod.GET)
	    public String toStandardsDetailPage(String p_detail_id,String standard_id,String detail_level,String detail_id,HttpServletRequest request){
	    	request.setAttribute("p_detail_id", p_detail_id);
	    	request.setAttribute("standard_id", standard_id);
	    	request.setAttribute("detail_level", detail_level);
	    	request.setAttribute("detail_id", detail_id);
	    	return "/web/html/BiDirectionalEvaluation/standardDetailEdit";
	    }
	    
	    @ApiOperation(value = "读取评价标准详细明细", notes = "读取评价标准详细明细")
		@RequestMapping(value = "loadStandardsDetail" , method = RequestMethod.POST)
	    @ResponseBody
	    public EvaluateDeatil loadStandardsDetail(String standard_id,String detail_id){
	    	EvaluateDeatil data = iEvaluateDetailService.queryEvaluateDeatilByKey(detail_id, standard_id);
	    	return data;
	    }
	    
	    
	    @ApiOperation(value = "进入双向评价-添加评价标准", notes = "添加评价标准")
		   @RequestMapping(value = "standardUpdate" , method = RequestMethod.POST)
		   @ResponseBody
		   public JsonResult standardUpdate(String json) {
			   System.err.println("---json:"+json);
			   JSONObject parseObject = JSON.parseObject(json);
			   EvaluateStandard evaluateStandard = parseObject.toJavaObject(EvaluateStandard.class);
			   
			   
			   //详细总分不超过100
			   double score = evaluateStandard.getStandard_plan_score();
			   if(score > 100){
				   return new JsonResult("总分已超过100分，保存失败！");
			   }
			   
			   String describe = "";
			   List<String> nameList = iEvaluateDetailService.queryDetailNameByLevel2(evaluateStandard.getStandard_id());
			   for(String data : nameList){
				   describe = describe + data + "、";
			   }
			   describe = describe.substring(0, describe.length()-1);

			   //判断是否是修改
			   String standard_id = evaluateStandard.getStandard_id();
			   if(!standard_id.isEmpty()){
				   //进行修改
				   evaluateStandard.setStandard_describe(describe);
				   iBiDiStandardService.update(evaluateStandard);
			   }else{
				   return new JsonResult("参数错误，保存失败！");
			   }

			   return new JsonResult();
		   }


	    @ApiOperation(value = "进入双向评价-添加评价标准详细修改", notes = "添加评价标准详细修改")
		   @RequestMapping(value = "update" , method = RequestMethod.POST)
		   @ResponseBody
		   public JsonResult editStandard(String json) {
			   System.err.println("---json:"+json);
			   //System.err.println("---treeJson:"+treeJson);
			   JSONObject parseObject = JSON.parseObject(json);
			   EvaluateDeatil evaluateStandardDeatil = parseObject.toJavaObject(EvaluateDeatil.class);
			   /*JSONArray parseArray = JSON.parseArray(treeJson);
			   List<EvaluateDeatil> evaluateDeatilList = parseArray.toJavaList(EvaluateDeatil.class);*/
			   
			   //判断当前标准细则分数是否有25分
			   /*	 if(evaluateStandardDeatil.getDetail_level() == 2){
				   Integer sumScore = iEvaluateDetailService.queryScoreCount(evaluateStandardDeatil.getStandard_id(), evaluateStandardDeatil.getP_detail_id());
				   if(sumScore != null && sumScore == 25){
					  return new JsonResult("分数已超过25，保存失败！");
				   }
			   }*/

			   //判断是否是修改
			   String id= evaluateStandardDeatil.getDetail_id();
			   if(id.isEmpty()){
				   //保存
				   evaluateStandardDeatil.setDetail_id(UUIDUtil.getUUID());
				   evaluateStandardDeatil.setCreate_time(DateUtil.getTime());
				   evaluateStandardDeatil.setUpdate_time(DateUtil.getTime());
				   iEvaluateDetailService.save(evaluateStandardDeatil);
			   }else{
				   //更新
				   evaluateStandardDeatil.setUpdate_time(DateUtil.getTime());
				   iEvaluateDetailService.update(evaluateStandardDeatil);
			   }
			  
			   //3级修改
			   //先删除2,3级数据，再添加2,3级数据
			   //iEvaluateDetailService.delete(evaluateStandard.getStandard_id());
			   /*for(EvaluateDeatil data : evaluateDeatilList){
				   data.setStandard_id(evaluateStandard.getStandard_id());
				   data.setDetail_id(UUIDUtil.getUUID());
				   data.setDetail_level(1);
				   data.setCreate_time(DateUtil.getTime());
				   data.setUpdate_time(DateUtil.getTime());
				   iEvaluateDetailService.save(data);
				   for(EvaluateDeatil detail : data.getEvaluateDetailChildList()){
					   detail.setDetail_id(UUIDUtil.getUUID());
					   detail.setStandard_id(evaluateStandard.getStandard_id());
					   detail.setP_detail_id(data.getDetail_id());
					   detail.setDetail_level(2);
					   detail.setCreate_time(DateUtil.getTime());
					   detail.setUpdate_time(DateUtil.getTime());
					   iEvaluateDetailService.save(detail);
				   }
			   }*/

			   return new JsonResult();
		   }
	    
	   @ApiOperation(value = "进入双向评价-删除评价明细", notes = "删除评价明细")
	   @RequestMapping(value = "deleteDetail" , method = RequestMethod.POST)
	   @ResponseBody
	   public JsonResult deleteDetail(String standard_id,String detail_id) {
		   //验证是否1级细则，1级细则验证下面是否有2级细则
		   EvaluateDeatil ed = iEvaluateDetailService.queryEvaluateDeatilByKey(detail_id, standard_id);
		   int level = ed.getDetail_level();
		   if(level == 1){
			   //1级细则，验证是否有2级，如没有，则可以删除，如有，则不删除
			   List<EvaluateDeatil> check = iEvaluateDetailService.queryEvaluateDeatilList(standard_id, ed.getDetail_id());
			   if(check != null && check.size() > 0){
				   //有2级无法删除
				   return new JsonResult("当前细则下有下级细则，无法删除！");
			   }else{
				   iEvaluateDetailService.delete(standard_id,detail_id);
			   }
		   }else{
			   iEvaluateDetailService.delete(standard_id,detail_id);
		   }
		   return new JsonResult();
	   }
	   
	   
	   @ApiOperation(value = "读取评价详细", notes = "读取评价详细")
		@RequestMapping(value = "loadDetail" , method = RequestMethod.POST)
	    @ResponseBody
	    public List<EvaluateDeatil> loadDetail(String standard_id){
	    	List<EvaluateDeatil> data = iEvaluateDetailService.queryEvaluateDeatil(standard_id);
	    	return data;
	    }
	   
	   
	   @ApiOperation(value = "生效评价标准", notes = "生效评价标准")
	   @RequestMapping(value = "checkEvaluateStandard" , method = RequestMethod.POST)
	   @ResponseBody
	   public JsonResult checkEvaluateStandard(String standard_id){
		   
		   EvaluateStandard es = iBiDiStandardService.queryEvaluateStandard(standard_id);
		   if(es.getStandard_plan_score() != 100){
			   return new JsonResult("标准预计总分未到100分，请继续编辑！");
		   }else if(es.getStandard_plan_score() > 100){
			   return new JsonResult("总分已超过100分，请重新更改！");
		   }
		   //对比1级与2级分数
		   List<EvaluateDeatil> datas = iEvaluateDetailService.queryEvaluateDeatil(standard_id);
		   //消息箱
		   List<String> msg = new ArrayList<>();
		   boolean levelFlag = false;
		   for(EvaluateDeatil data : datas){
			   if(data.getDetail_level() == 1){
				   //1级查当前1级的2级分数
				   Integer count = iEvaluateDetailService.queryScoreCount(standard_id, data.getDetail_id());
				   if(count != null){
					   if(data.getDetail_plan_score() != count){
						   String msgs = "启用生效失败,细则名:"+data.getDetail_name()+"下，分数总和不相等！请重新更改";
						   msg.add(msgs);
					   }
				   }else{
					   String msgs = "启用失败,细则名:"+data.getDetail_name()+"下，下级细则分数为0！请重新更改";
					   msg.add(msgs);
				   }
				   
				   levelFlag = true;
			   }
		   }
		   if(msg != null && msg.size() > 0){
			   //有错误产生
			   String msgs = "失败列表如下:";
			   for(String str : msg){
				   msgs = msgs + str + ",";
			   }
			   return new JsonResult(msgs);
		   }else if(!levelFlag){
			  //一次都没进入分数对比(没有2级)
			   return new JsonResult("没有1级细则，请添加1级细则！");
		   }else{
			   //做生效操作  
			   iBiDiStandardService.vaildStandard(standard_id);
		   }
		  
		   return new JsonResult();
	   }
	   
	   
	   
	   @ApiOperation(value = "生效评价标准", notes = "生效评价标准")
	   @RequestMapping(value = "invalidEvaluateStandard" , method = RequestMethod.POST)
	   @ResponseBody
	   public JsonResult invalidEvaluateStandard(String standard_id){
		   
		   iBiDiStandardService.invaildStandard(standard_id);
		   return new JsonResult("标准已停用！");
	   }
}
