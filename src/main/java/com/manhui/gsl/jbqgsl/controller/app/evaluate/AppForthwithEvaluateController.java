package com.manhui.gsl.jbqgsl.controller.app.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.enums.Flag;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;
import com.manhui.gsl.jbqgsl.service.app.IAppEvaluateFlowingScoreService;
import com.manhui.gsl.jbqgsl.service.app.IAppEvaluateFlowingSuggestService;
import com.manhui.gsl.jbqgsl.service.app.IAppForthwithEvaluateService;
import com.manhui.gsl.jbqgsl.service.app.IAppInstitutionService;
import com.manhui.gsl.jbqgsl.service.app.IAppTopicStandardEvaluateService;
import com.manhui.gsl.jbqgsl.service.app.IAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Title: AppForthwithEvaluateController.java
* @Package com.manhui.gsl.jbqgsl.controller.app
* @Description: TODO(app端即时评价控制层)
* @author LiuBin
* @date 2018年8月16日
* @version V1.0
* @modify By: 
* @Copyright: 版权由满惠科技拥有  
*/
@Api(tags = "双向评价-即时评价")
@Controller
@RequestMapping(AppForthwithEvaluateController.ROOT_URL)
@ResponseBody
public class AppForthwithEvaluateController extends BaseController{
	public static final String ROOT_URL = PARENT_URL_APP + "/forthwithEvaluate";
	@Resource
	private IAppForthwithEvaluateService iAppForthwithEvaluateService;
	@Resource
	private IAppTopicStandardEvaluateService iAppTopicStandardEvaluateService;
	@Resource
	private IAppEvaluateFlowingScoreService iAppEvaluateFlowingScoreService;
	@Resource
	private IAppEvaluateFlowingSuggestService iAppEvaluateFlowingSuggestService;
	@Resource
	private IAppInstitutionService iAppInstitutionService;
	@Resource
	private IAppUserService iAppUserService;
	
	@ApiOperation(value = "即时评价 --展示待被评价机构", notes = "即时评价 --展示待被评价机构")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "被评方类型", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = false, dataType = "Long"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面展示条数", required = false, dataType = "Long")
	}) 
	@RequestMapping(value="queryForthWithEvaluate",method=RequestMethod.POST)
	public JsonResult query(
			@RequestParam(value = "institution_id", required = true) String institution_id,
			@RequestParam(value = "institution_type", required = false) String institution_type,
			@RequestParam(value = "pageNo", required = false) Long pageNo,
			@RequestParam(value = "pageSize", required = false)Long pageSize){
		
		String type = iAppInstitutionService.queryInstitutionType(institution_id);
		if(StringUtils.isEmpty(type) || !"3".equals(type)) {
			return new JsonResult();
		}
		PaginationVO<Institution> institutionList= iAppInstitutionService.queryInstitutions2(pageNo,pageSize,institution_type);
		//获取机构头像
		List<AppUser> userList = new ArrayList<>();
		if(institutionList.getTotal()>0 && institutionList.getDataList() !=null && institutionList.getDataList().size()>0) {
			for (Institution institution : institutionList.getDataList()) {
				AppUser  user= iAppUserService.queryUser(institution.getInstitution_id());
				userList.add(user);
			}
			//机构头像
			for (int i = 0; i < institutionList.getDataList().size(); i++) {
				Institution institution = institutionList.getDataList().get(i);
				for (int j = 0; j < userList.size(); j++) {
					AppUser user = userList.get(j);
					if(institution !=null && user !=null) {
						if(institution.getInstitution_id().equals(user.getInstitution_id())) {
							institution.setHeadImg(user.getHead_img());
						}
						
					}
				}
			
			}
		}
		
		
		//区级政府
		List<Institution> districtInstitutionList = new ArrayList<>();
		//街道部门
		List<Institution> streetInstitutionList = new ArrayList<>();
		if(institutionList.getDataList() !=null && institutionList.getDataList().size()>0) {
			for (int i = 0; i < institutionList.getDataList().size(); i++) {
				if("1".equals(institutionList.getDataList().get(i).getInstitution_type())) {//区级
					districtInstitutionList.add(institutionList.getDataList().get(i));
				}else if("2".equals(institutionList.getDataList().get(i).getInstitution_type())) { //街道
					streetInstitutionList.add(institutionList.getDataList().get(i));
				}
			}	
		}
		Map<String,Object> dataMap = new HashMap<>(); 
		if("3".equals(type)) {
			if(null==institution_type){
				dataMap.put("passiveIdsNum", institutionList.getTotal());//总被评机构数--未评 已评
				dataMap.put("districtNum", districtInstitutionList.size());//总被评机构数--区级(上面的参数 传"1":)
				dataMap.put("streetNum", streetInstitutionList.size());//总被评机构数--区级(上面的参数 传"1":)
			}else if("1".equals(institution_type)){//区级部门--企业为评价方(查看区级部门)
				dataMap.put("institutionList", districtInstitutionList);
				dataMap.put("districtNum", institutionList.getTotal());
			}else if("2".equals(institution_type)) {//街道--企业为评价方(查看街道)
				dataMap.put("streetInstitutionList", streetInstitutionList);
				dataMap.put("streetNum", institutionList.getTotal());
			}
			
		}
		return new JsonResult(dataMap);
	
	
	}

	@ApiOperation(value = "根据机构id被评价机构id--展示评分详情页面", notes = "根据机构id,被评价机构id展示展示评分详情页面")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_id", value = "被评价机构id", required = false, dataType = "字符串")
	})
	@RequestMapping(value="showEvaluatePage",method=RequestMethod.POST)
	public JsonResult showEvaluatePage(
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "passive_id", required = true)String passive_id
		) {
		//根据topic_id来获取到评价标准id
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("active_id", active_id);
		paramMap.put("passive_id", passive_id);
		
		//直接在标准评价表中找即时评价
		String standard_belonged ="2";
		String standard_id= iAppForthwithEvaluateService.queryStandardId(standard_belonged);
		if(StringUtils.isEmpty(standard_id)) {
			return new JsonResult("没有即时评价");
		}
		paramMap.put("standard_id", standard_id);
		
		EvaluateStandard data = iAppForthwithEvaluateService.showForthwithEvaluateStandard(paramMap);
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("data", data);
		return new JsonResult(dataMap);
	}
	
	@ApiOperation(value = "根据机构id以及即时id以及被评价机构id--保存评分结果", notes = "根据机构id,即时id以及被评价机构id保存评分结果")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_id", value = "被评价机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_name", value = "被评价机构名称", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "real_score_sum", value = "总分", required = true, dataType = "double"),
		@ApiImplicitParam(paramType = "query", name = "standard_id", value = "标准id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "flag", value = "标记", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "forthwithScoreJson", value = "即时分数json", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "forthwithSuggestJson", value = "即时建议内容json", required = true, dataType = "字符串")
	})
	@RequestMapping(value="saveEvaluatePage",method=RequestMethod.POST)
	public JsonResult saveEvaluatePage(
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "passive_id", required = true)String passive_id,
			@RequestParam(value = "active_name", required = true)String active_name,
			@RequestParam(value = "passive_name", required = true)String passive_name,
			@RequestParam(value = "standard_id", required = true)String standard_id,
			@RequestParam(value = "flag", required = true)String flag,
			@RequestParam(value = "real_score_sum", required = true)double real_score_sum,
			@RequestParam(value = "forthwithScoreJson", required = true)String forthwithScoreJson,
			@RequestParam(value = "forthwithSuggestJson", required = true) String forthwithSuggestJson) {
		//先判断active_id是不是企业机构不是就不让它评价
		String institutionType = iAppInstitutionService.queryInstitutionType(active_id);
		if(!"3".equals(institutionType)) {
			throw new RuntimeException("该机构不是企业不能进行即时评价"); 
		}
		ForthwithEvaluate fwe = new ForthwithEvaluate();
		fwe.setActive_id(active_id);
		fwe.setPassive_id(passive_id); 
		fwe.setEvaluate_active_name(active_name);
		fwe.setEvaluate_passive_name(passive_name);
		fwe.setReal_score_sum(real_score_sum ); 
		String forthwithId= UUIDUtil.getUUID();
		fwe.setForthwith_id(forthwithId);
		fwe.setDel_flag("0"); 
		fwe.setForthwith_number(this.createNewForthNumber());;
		
		//先向数据库forthwith_evaluate 中插入数据
		Map<String,Object> paramMap = new HashMap<>();
		int num =  iAppForthwithEvaluateService.save(fwe);
		if(num<0) {
			paramMap.put("state", Flag.FAIL);
			return new JsonResult("即时评价表保存失败");
		}
		
		int scoreNumSecond =0;
		int scoreNumFirst =0;
		int suggestNum =0;
		//直接在标准评价表中找即时评价
		 JSONObject jsonObject1 = JSON.parseObject( forthwithScoreJson );
		 String aa = jsonObject1.get( "evaluateDetailList" )+"";
		 JSONObject jsonObject2 = JSON.parseObject( forthwithSuggestJson );
		 String bb =jsonObject2.get("forthwithSuggestJson")+"";
		  List<EvaluateDeatil> edList = JSONObject.parseArray( aa, EvaluateDeatil.class );
		EvaluateFlowingSuggest evaluateFlowingSuggest = JSON.parseObject(bb,EvaluateFlowingSuggest.class);
			//保存-流水评分表
			if("0".equals(flag)) {
				if(edList != null && edList.size() > 0) {
					EvaluateFlowingScore efs = null;
					for(EvaluateDeatil ed : edList) {
						efs = new EvaluateFlowingScore();
						//分数id
						efs.setScore_id(UUIDUtil.getUUID());
						//即时id
						efs.setForthwith_id(forthwithId);
						//即时标准id
						efs.setForthwith_standard_id(standard_id); 
						//即时标准详情id
						efs.setForthwith_standard_detail_id(ed.getDetail_id());
						//即时标准详情名称
						efs.setForthwith_standard_detail_name(ed.getDetail_name());
						//即时标准详请分数
						efs.setPlan_score(ed.getDetail_plan_score());
						//即时评价实际分数
						efs.setReal_score(0.0);
						//创建时间
						efs.setCreate_time(DateUtil.getTime());
						//被评价机构id
						efs.setPassive_id(passive_id);
						//被评价机构名称
						efs.setPassive_name(passive_name);
						//评价方ID
						efs.setActice_id(active_id);
						//不了解标志
						efs.setFlag(flag);
						//评价方名称
						efs.setActive_name(active_name);
						//即时标准父详情id
						efs.setStandard_p_detail_id(ed.getP_detail_id());
						//即时标准详情详情级别
						efs.setStandard_detail_level(ed.getDetail_level());
						//即时评价分数
						efs.setPlan_score(ed.getDetail_plan_score());
						//即时评价真实分数
						efs.setReal_score(0.0);
						//取第二层
						if(ed.getEvaluateDetailChildList() !=null && ed.getEvaluateDetailChildList().size()>0) {
							EvaluateFlowingScore efs2 = null;
							for (EvaluateDeatil ed2 : ed.getEvaluateDetailChildList()) {
								System.err.println("第二层:"+ed2);
								efs2 = new EvaluateFlowingScore();
								//分数id
								efs2.setScore_id(UUIDUtil.getUUID());
								//即时id
								efs2.setForthwith_id(forthwithId);
								//即时标准id
								efs2.setForthwith_standard_id(standard_id); 
								//即时标准详情id
								efs2.setForthwith_standard_detail_id(ed2.getDetail_id());
								//即时标准详情名称
								efs2.setForthwith_standard_detail_name(ed2.getDetail_name());
								//计划分数
								efs2.setPlan_score(ed2.getDetail_plan_score());
								//实际分数
								efs2.setReal_score(ed2.getDetail_real_score());
								//即时标准详情id
								efs2.setForthwith_standard_detail_id(ed2.getDetail_id());
								//即时标准详情名称
								efs2.setForthwith_standard_detail_name(ed2.getDetail_name());
								//创建时间
								efs2.setCreate_time(DateUtil.getTime());
								//被评价机构id
								efs2.setPassive_id(passive_id);
								//被评价名称
								efs2.setPassive_name(passive_name);
								efs2.setFlag(flag);
								efs2.setActice_id(active_id);
								efs2.setActive_name(active_name);
								efs2.setPlan_score(ed2.getDetail_plan_score());
								efs2.setReal_score(0.0);
								efs2.setStandard_detail_level(ed2.getDetail_level());
								efs2.setStandard_p_detail_id(ed2.getP_detail_id());
								scoreNumSecond = iAppEvaluateFlowingScoreService.save(efs2);
							}
						}
						scoreNumFirst = iAppEvaluateFlowingScoreService.save(efs);
					}
				}
				//保存-流水建议表
				evaluateFlowingSuggest.setSuggest_id(UUIDUtil.getUUID());
				evaluateFlowingSuggest.setCreate_time(DateUtil.getTime());
				evaluateFlowingSuggest.setForthwith_id(forthwithId);  
				evaluateFlowingSuggest.setSuggest_is_answer("0"); //未回复
//			 evaluateFlowingSuggest.setTopic_id(topic_id);
				suggestNum = iAppEvaluateFlowingSuggestService.save(evaluateFlowingSuggest);
				return new JsonResult("保存成功");
			}else {
				if(edList != null && edList.size() > 0) {
					EvaluateFlowingScore efs = null;
					for (int i = 0; i < edList.size(); i++) {
						EvaluateDeatil ed = edList.get(i);
						//取第一层
						System.err.println("ed:"+ed);
						efs = new EvaluateFlowingScore();
						efs.setScore_id(UUIDUtil.getUUID());
						efs.setForthwith_id(forthwithId);
						efs.setForthwith_standard_id(standard_id); 
						efs.setForthwith_standard_detail_id(ed.getDetail_id());
						efs.setForthwith_standard_detail_name(ed.getDetail_name());
						efs.setPlan_score(ed.getDetail_plan_score());
						efs.setReal_score(ed.getDetail_real_score());
						efs.setCreate_time(DateUtil.getTime());
						efs.setPassive_id(passive_id);
						efs.setPassive_name(passive_name);
						efs.setFlag(flag);
						efs.setActice_id(active_id);
						efs.setActive_name(active_name);
						//即时标准详情id
						efs.setForthwith_standard_detail_id(ed.getDetail_id());
						//即时标准详情名称
						efs.setForthwith_standard_detail_name(ed.getDetail_name());
						efs.setPlan_score(ed.getDetail_plan_score());
						efs.setReal_score(ed.getDetail_real_score());
						//标准级别
						efs.setStandard_detail_level(ed.getDetail_level());
						//标准详情父级id
						efs.setStandard_p_detail_id(ed.getP_detail_id());
						Double real_score =0.0;
						//取第二层
						if(ed.getEvaluateDetailChildList() !=null && ed.getEvaluateDetailChildList().size()>0) {
							EvaluateFlowingScore efs2 = null;
							for (int j = 0; j < ed.getEvaluateDetailChildList().size(); j++) {
								EvaluateDeatil ed2 = ed.getEvaluateDetailChildList().get(j);
								efs2 = new EvaluateFlowingScore();
								efs2.setScore_id(UUIDUtil.getUUID());
								efs2.setForthwith_id(forthwithId);
								efs2.setForthwith_standard_id(standard_id); 
								efs2.setForthwith_standard_detail_id(ed2.getDetail_id());
								efs2.setForthwith_standard_detail_name(ed2.getDetail_name());
								efs2.setPlan_score(ed2.getDetail_plan_score());
								efs2.setReal_score(ed2.getDetail_real_score());
								efs2.setCreate_time(DateUtil.getTime());
								efs2.setPassive_id(passive_id);
								efs2.setPassive_name(passive_name);
								efs2.setFlag(flag);
								efs2.setActice_id(active_id);
								efs2.setActive_name(active_name);
								//即时标准详情id
								efs2.setForthwith_standard_detail_id(ed2.getDetail_id());
								//即时标准详情名称
								efs2.setForthwith_standard_detail_name(ed2.getDetail_name());
								efs2.setPlan_score(ed2.getDetail_plan_score());
								efs2.setReal_score(ed2.getDetail_real_score());
								real_score+=efs2.getReal_score();
								//标准级别
								efs2.setStandard_detail_level(ed2.getDetail_level());
								//标准详情父级id
								efs2.setStandard_p_detail_id(ed2.getP_detail_id());
								scoreNumSecond = iAppEvaluateFlowingScoreService.save(efs2);
							}
						}
						//存第一层
						efs.setReal_score(real_score);
						scoreNumFirst = iAppEvaluateFlowingScoreService.save(efs);
					}
				}
				//保存-流水建议表
				evaluateFlowingSuggest.setSuggest_id(UUIDUtil.getUUID());
				evaluateFlowingSuggest.setForthwith_id(forthwithId);  
				evaluateFlowingSuggest.setActice_id(active_id); 
				evaluateFlowingSuggest.setActive_name(active_name); 
				evaluateFlowingSuggest.setPassive_id(passive_id); 
				evaluateFlowingSuggest.setPassive_name(passive_name); 
				evaluateFlowingSuggest.setForthwith_standard_id(standard_id); 
				evaluateFlowingSuggest.setSuggest_is_answer("0");   
				evaluateFlowingSuggest.setCreate_time(DateUtil.getTime());
				suggestNum = iAppEvaluateFlowingSuggestService.save(evaluateFlowingSuggest);
			
				if(suggestNum>0 ||scoreNumFirst>0 ||scoreNumSecond>0) {
					paramMap.put("state", Flag.SUCCESS);
				}else {
					paramMap.put("state", Flag.FAIL);
				}
				return new JsonResult(paramMap);
				
			}
	}
	
	@ApiOperation(value = "历史-我的评价", notes = "历史-我的评价")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "机构类型", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = false, dataType = " Long"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面记录数", required = false, dataType = "Long")
	})
	@RequestMapping(value="showHistoryEvaluateInstitutions",method=RequestMethod.POST)
	public JsonResult showHistoryEvaluateInstitutions(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "institution_type", required = false)String institution_type,
			@RequestParam(value = "pageNo", required = false)Long pageNo,
			@RequestParam(value = "pageSize", required = false)Long pageSize){
		//根据机构id来获取即时评价流水表
		String type = iAppInstitutionService.queryInstitutionType(institution_id);
		PaginationVO<ForthwithEvaluate> forthwithObj = iAppForthwithEvaluateService.queryForthwithList2(institution_id, pageNo, pageSize,institution_type);
		List<ForthwithEvaluate> dataList = forthwithObj.getDataList();
		List<EvaluateFlowingSuggest> suggestList = new ArrayList<>();
		List<Institution> institutionList = new ArrayList<>();
		List<AppUser> userList = new ArrayList<>();
		//根据机构id 被评方id 即时评价id 获取流水意见表中查看是否已经回复
		for (ForthwithEvaluate forthwithEvaluate : dataList) {
			EvaluateFlowingSuggest evaluateFlowingSuggest = iAppEvaluateFlowingSuggestService.queryIsSuggest(forthwithEvaluate);
			suggestList.add(evaluateFlowingSuggest);
			Institution institution = iAppInstitutionService.queryInstitutionInfo(forthwithEvaluate.getPassive_id());
			institutionList.add(institution);
			AppUser  user= iAppUserService.queryUserInfo(forthwithEvaluate);
			userList.add(user);
		}
		//循环获取到是否答复,以及答复内容
		for (int i = 0; i < dataList.size(); i++) {
			ForthwithEvaluate forthwithEvaluate = dataList.get(i);
			for (int j = 0; j < suggestList.size(); j++) {
				EvaluateFlowingSuggest evaluateFlowingSuggest = suggestList.get(j);
				if(forthwithEvaluate !=null && evaluateFlowingSuggest !=null) {
					if(forthwithEvaluate.getForthwith_id().equals(evaluateFlowingSuggest.getForthwith_id())) {
						//是否回复
						forthwithEvaluate.setIs_answer(evaluateFlowingSuggest.getSuggest_is_answer());
						//回复内容
						forthwithEvaluate.setAnswer_content(evaluateFlowingSuggest.getSuggest_answer());
						//即时评价标准
						forthwithEvaluate.setStandard_id(evaluateFlowingSuggest.getForthwith_standard_id());
						break;
					}
				}
			}
		}
		//机构详情
		for (int i = 0; i < dataList.size(); i++) {
			ForthwithEvaluate forthwithEvaluate = dataList.get(i);
			for (int j = 0; j < institutionList.size(); j++) {
				Institution institution = institutionList.get(j);
				if(forthwithEvaluate !=null && institution !=null) {
					if(forthwithEvaluate.getPassive_id().equals(institution.getInstitution_id())) {
						forthwithEvaluate.setDescribe(institution.getInstitution_describe());
						forthwithEvaluate.setHtml_describe(institution.getHtml_describe());
						//即时评价机构类型
						forthwithEvaluate.setInstitution_type(institution.getInstitution_type());
					}
				}
			}
		} 
		//机构头像
		for (int i = 0; i < dataList.size(); i++) {
			ForthwithEvaluate forthwithEvaluate = dataList.get(i);
			for (int j = 0; j < userList.size(); j++) {
				AppUser user = userList.get(j);
				if(forthwithEvaluate !=null && user !=null) {
					if(forthwithEvaluate.getPassive_id().equals(user.getInstitution_id())) {
						forthwithEvaluate.setHeadImg(user.getHead_img());;
					}
				}
			}
		}
		//对该集合进行排序--按照评价时间进行倒序
		Collections.sort(dataList, new Comparator<ForthwithEvaluate>() {

			@Override
			public int compare(ForthwithEvaluate o1, ForthwithEvaluate o2) {
				long o11 = DateUtil.strToDate(o1.getEvaluate_time(), Constant.DATETIME_PATTERN).getTime();
				long o22 = DateUtil.strToDate(o2.getEvaluate_time(), Constant.DATETIME_PATTERN).getTime();
				if(o11>o22) {
					return -1;
				}
				if(o1.getEvaluate_time().equals(o2.getEvaluate_time())) {
					return 0;
				}
				return 1;
			}
		
		});  
		
		//区级政府
		List<ForthwithEvaluate> fObjList1 = new ArrayList<>();
		//街道部门
		List<ForthwithEvaluate> fObjList2 = new ArrayList<>();
		if(dataList !=null && dataList.size()>0) {
			for (int i = 0; i < dataList.size(); i++) {
				if("1".equals(dataList.get(i).getInstitution_type())) {//区级
					fObjList1.add(dataList.get(i));
				}else if("2".equals(dataList.get(i).getInstitution_type())) { //街道
					fObjList2.add(dataList.get(i));
				}
			}	
		}
		Map<String,Object> dataMap = new HashMap<>(); 
		if("3".equals(type)) {
			if(null==institution_type){
				dataMap.put("passiveIdsNum",forthwithObj.getTotal());//总被评机构数--未评 已评
				dataMap.put("districtNum", fObjList1.size());//总被评机构数--区级(上面的参数 传"1":)
				dataMap.put("streetNum", fObjList2.size());//总被评机构数--区级(上面的参数 传"1":)
			}else if("1".equals(institution_type)){//区级部门--企业为评价方(查看区级部门)
				dataMap.put("institutionList", fObjList1);
				dataMap.put("districtNum", fObjList1.size());
			}else if("2".equals(institution_type)) {//街道--企业为评价方(查看街道)
				dataMap.put("streetInstitutionList", fObjList2);
				dataMap.put("streetNum", fObjList2.size());
			}
		}
		return new JsonResult(dataMap);
	}
	
	@ApiOperation(value = "被评价", notes = "被评价")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "展示条数", required = true, dataType = "字符串")
		
	})
	@RequestMapping(value="showEvaluateScore",method=RequestMethod.POST)
	public JsonResult showEvaluateScore(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "pageNo", required = true)long pageNo,
			@RequestParam(value = "pageSize", required = true)long pageSize	){
		//获取到所有的评价机构
			PaginationVO<ForthwithEvaluate> pgList = iAppForthwithEvaluateService.queryForthwithObj(institution_id,pageNo,pageSize);
			List<ForthwithEvaluate> dataList = pgList.getDataList();
			List<AppUser> userList = new ArrayList<>();
			List<EvaluateFlowingSuggest> evaluateFlowingSuggestList = new ArrayList<>();
			//根据评价机构对象,查询用户表对应的数据
			for (ForthwithEvaluate forthwithEvaluate : dataList) {
				AppUser  user= iAppUserService.queryUserInfo(forthwithEvaluate);
				EvaluateFlowingSuggest evaluateSuggest = iAppEvaluateFlowingSuggestService.queryIsSuggest(forthwithEvaluate);
				userList.add(user);
				evaluateFlowingSuggestList.add(evaluateSuggest);
			}
			//机构头像--匹配赋值头像
			for (int i = 0; i < dataList.size(); i++) {
				ForthwithEvaluate forthwithEvaluate = dataList.get(i);
				for (int j = 0; j < userList.size(); j++) {
					AppUser user = userList.get(j);
					if(forthwithEvaluate.getForthwith_id().equals(user.getInstitution_id())) {
						forthwithEvaluate.setHeadImg(user.getHead_img());;
					}
				}
			
			
			}
			//流水表中的即使评价标准id
			for (int i = 0; i < dataList.size(); i++) {
				ForthwithEvaluate forthwithEvaluate = dataList.get(i);
				for (int j = 0; j < evaluateFlowingSuggestList.size(); j++) {
					EvaluateFlowingSuggest evaluateFlowingSuggest = evaluateFlowingSuggestList.get(j);
					if(forthwithEvaluate.getForthwith_id().equals(evaluateFlowingSuggest.getForthwith_id())) {
						forthwithEvaluate.setStandard_id(evaluateFlowingSuggest.getForthwith_standard_id());
					}
				}
				
				
			}
			return new JsonResult(pgList);
	}
	
	@ApiOperation(value = "历史 - 查看被评价详情(政府)", notes = "历史 - 查看被评价详情(政府)")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "评价方企业部门机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_id", value = "被评价方政府机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "standard_id", value = "评价标准id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "forthwith_id", value = "即时id", required = true, dataType = "字符串")
		
	})
	@RequestMapping(value="showDetailForthwithEvaluatePage",method=RequestMethod.POST)
	public JsonResult showDetailForthwithEvaluatePage(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "passive_id", required = true)String passive_id,
			@RequestParam(value = "standard_id", required = true)String standard_id,
			@RequestParam(value = "forthwith_id", required = true)String forthwith_id){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("passive_id", passive_id);
		paramMap.put("active_id", institution_id);
		paramMap.put("forthwith_id", forthwith_id);
		paramMap.put("standard_id", standard_id);
		//直接在标准评价表中找即时评价
//		String standard_belonged ="2";
//		String standard_id= iAppForthwithEvaluateService.queryStandardId(standard_belonged);
//		paramMap.put("standard_id", standard_id);

		EvaluateStandard data = iAppForthwithEvaluateService.showTopicEvaluateStandard(paramMap);
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("data", data);
		return new JsonResult(dataMap);
	}
	
	@ApiOperation(value = "即时评价搜索", notes = "即时评价搜索")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "机构行业(1:区级 2:街道)", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_name", value = "被评价机构名称", required = true, dataType = "字符串")
		

	})
	
	@RequestMapping(value="queryByCondition",method=RequestMethod.POST)
	public JsonResult queryByCondition(
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "institution_type", required = true)String institution_type,
			@RequestParam(value = "passive_name", required = true)String passive_name) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("active_id", active_id);
		paramMap.put("passive_name", passive_name);
		paramMap.put("institution_type", institution_type);
		if(StringUtils.isEmpty(passive_name)) {
			return new JsonResult("请输入搜索内容");
		}
		//根据机构id 政府部门名称所有的政府单位信息
		List<Institution> passiveIdsList = iAppForthwithEvaluateService.queryPassiveInstitutions(paramMap);
		Map<String,Object> paramMap2 = new HashMap<>();
		if(passiveIdsList==null || passiveIdsList.size()==0) {
			paramMap2.put("total", 0);
			return new JsonResult(paramMap2);
		}else {
			paramMap2.put("total", passiveIdsList.size());
			paramMap2.put("passiveIdsList", passiveIdsList);
			return new JsonResult(paramMap2);
			
		}
		
	} 
	/**
	 * 生成即时编号工具类
	 */
    private String createNewForthNumber() {
        String topic_number = "JS";
        String currentDate = DateUtil.getDateTime( "yyyyMMdd" );
        String max = iAppForthwithEvaluateService.getMaxTopicNumber();
        if ( max != null && !"".equals( max ) ) {
            String date = max.substring( 2, 10 );
            if ( currentDate.equals( date ) ) {
                String number = max.substring( 11, 13 );
                String newNumber = "";
                int num = Integer.valueOf( number ) + 1;
                if ( num < 10 ) {
                    newNumber = "00" + num;
                }
                else if ( num < 100 ) {
                    newNumber = "0" + num;
                }
                else {
                    newNumber = "" + num;
                }
                topic_number = topic_number + currentDate + newNumber;
            }
            else {
                topic_number = topic_number + currentDate + "001";
            }
        }
        else {
            topic_number = topic_number + currentDate + "001";
        }
        return topic_number;
    }
}
