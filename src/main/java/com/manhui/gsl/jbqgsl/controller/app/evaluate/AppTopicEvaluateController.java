
package com.manhui.gsl.jbqgsl.controller.app.evaluate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.companyelegant.CompanyElegantResult;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;
import com.manhui.gsl.jbqgsl.service.app.IAppBiDiStandardService;
import com.manhui.gsl.jbqgsl.service.app.IAppEvaluateFlowingScoreService;
import com.manhui.gsl.jbqgsl.service.app.IAppEvaluateFlowingSuggestService;
import com.manhui.gsl.jbqgsl.service.app.IAppInstitutionService;
import com.manhui.gsl.jbqgsl.service.app.IAppTopicEvaluateService;
import com.manhui.gsl.jbqgsl.service.app.IAppTopicEvaluateStandardService;
import com.manhui.gsl.jbqgsl.service.app.IAppTopicStandardEvaluateService;
import com.manhui.gsl.jbqgsl.service.app.IAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.util.StringUtil;

/**
* @Title: AppTopicEvaluateController.java
* @Package com.manhui.gsl.jbqgsl.controller.app
* @Description: TODO(双向评价--主体评价)
* @author LiuBin
* @date 2018年8月16日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Api(tags = "双向评价--主题评价")
@Controller
@RequestMapping(AppTopicEvaluateController.ROOT_URL)
public class AppTopicEvaluateController extends BaseController{
	public static final String ROOT_URL = PARENT_URL_APP + "/topicEvaluate";
	
	@Resource
	private IAppTopicEvaluateService iAppTopicEvaluateService;
	@Resource
	private IAppEvaluateFlowingScoreService iAppEvaluateFlowingScoreService;
	@Resource
	private IAppEvaluateFlowingSuggestService iAppEvaluateFlowingSuggestService;
	@Resource
	private IAppInstitutionService iAppInstitutionService;
	@Resource
	private IAppBiDiStandardService iAppBiDiStandardService;
	@Resource
	private IAppTopicStandardEvaluateService iAppTopicStandardEvaluateService;
	@Resource
	private IAppTopicEvaluateStandardService iAppTopicEvaluateStandardService;
	@Resource
	private IAppUserService appUserService;
	
	@ApiOperation(value = "双向评价 --待评价与已完成,过期", notes = "双向评价 --待评价与已完成以及过期")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "is_evaluate", value = "是否完成(0:未完成 1:已完成)", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "evaluate_state", value = "评价状态(2:评价中 3:已过期)", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = true, dataType = "long"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面展示条数", required = true, dataType = "long")
		
	})
	@RequestMapping(value="queryToTopicEvaluate",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult query(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "is_evaluate", required = false)String is_evaluate,
			@RequestParam(value = "evaluate_state", required = true)String evaluate_state,
			@RequestParam(value = "pageNo", required = true) long pageNo,
			@RequestParam(value = "pageSize", required = true)long pageSize){
		//验证参数
		if(StringUtils.isEmpty(institution_id)) {
			return new JsonResult("请传入institution_id 机构id");
		}
		//根据机构id获取 主题评价状态 为评价中的主题id( 已完成 is_evaluate=1 其他为0 1:表示已完成 )
		List<String> topicEvaluateIdsList = iAppTopicEvaluateService.getTopicEvaluateIds(institution_id,is_evaluate);
		//根据topicEvaluateList集合中的主题id 获取主题下被评价机构
		if( null == topicEvaluateIdsList || topicEvaluateIdsList.size()==0 ) {
			return new JsonResult("您没有评价的主题内容");
		}
		//根据主题id获取标准id来判断该标准
		List<String> standardIds =iAppTopicEvaluateStandardService.queryStandardIds(topicEvaluateIdsList);
		PaginationVO<TopicEvaluate> paginationVO = new PaginationVO<>();
		//根据主题id来查找主题开始时间 结束时间 主题名称
		paginationVO = iAppTopicEvaluateService.queryTopicEvaluates(topicEvaluateIdsList,pageNo,pageSize,evaluate_state);
		 List<Institution> institutionList = new ArrayList<>();
		 List<AppUser> userList = new ArrayList<>();
		 List<EvaluateFlowingScore> evaluateFlowingScoreList = new ArrayList<>();
		
		 for (TopicEvaluate topicEvaluate : paginationVO.getDataList()) {
			//根据机构id 主题id获取被评价方的信息
			 institutionList = iAppTopicEvaluateService.queryPassiveInstitution(institution_id,topicEvaluate.getTopic_id());
			 regsetPassiveFlag(institutionList); 
			 topicEvaluate.setPassiveInstitutionNum(institutionList.size());
			 if(institutionList.size()==0) {
				 return new JsonResult("该主题下你没有评价的机构");
			 }
			 evaluateFlowingScoreList= iAppEvaluateFlowingScoreService.getEvaluateFlowingScore(institution_id,institutionList, topicEvaluate.getTopic_id());
			 topicEvaluate.setAlreadyEvaluateNum(evaluateFlowingScoreList.size());
			
		}
		//获取到机构id查找用户信息
		 for (Institution institution : institutionList) {
			AppUser user =  appUserService.queryUser(institution.getInstitution_id());
			userList.add(user);
		}
		 //遍历将用户表中机构头像设置到机构属性中
		for (int j = 0; j < userList.size(); j++) {
			AppUser appUser = userList.get(j);
			for (int i = 0; i < institutionList.size(); i++) {
				Institution institution = institutionList.get(i);
				if (appUser !=null && appUser.getInstitution_id().equals(institution.getInstitution_id())) {
					institution.setHeadImg(appUser.getHead_img());
					break;
				}
			}
		}
		
		Map<String,Object> map = new HashMap<>();
		
		//根据机构id判断机构类型 是政府? 企业? 
		String institutionType = iAppTopicEvaluateService.queryInstitutionType(institution_id);
		map.put("topicEvaluateList", paginationVO.getDataList());
		map.put("total", paginationVO.getTotal());
		map.put("institutionType", institutionType);
		return new JsonResult(map);
	
	}



	
	@ApiOperation(value = "展示未评价机购已评价机构", notes = "展示未评价机购已评价机构")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "机构类型", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "industry_id", value = "行业id", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "topic_id", value = "主题id", required = true, dataType = "字符串")
	})
	@RequestMapping(value="queryEvaluatePassiveInstitutionByIds2",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult showNoPassiveInstitutionByIds2(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "is_alreadyEvaluate", required = true)String is_alreadyEvaluate,//(0:未评价 1:已评价)
			@RequestParam(value = "institution_type", required = true)String institution_type,//机构类型(1：区级部门，2：街镇，3：企业，)
			@RequestParam(value = "industry_id", required = false)String industry_id,//行业id--针对的是企业(1:工业，2:建筑业，3：交通运输，4:物流业5:批发零售业6:住宿餐饮业7:金融业8:房地产业9:营利性服务业10:其他民营企业)
			@RequestParam(value = "pageNo", required = true)long pageNo,
			@RequestParam(value = "pageSize", required = true)long pageSize,
			@RequestParam(value = "topic_id", required = true) String topic_id) {
		//根据机构id 主题id获取被评价方的信息
		List<Institution> institutionList = iAppTopicEvaluateService.queryPassiveInstitution(institution_id,topic_id);
//		List<Institution> institutionList = iAppTopicEvaluateService.queryPassiveInstitution2(institution_id,topic_id,institution_type,industry_id);
		//判断被评机构删除标识
		regsetPassiveFlag(institutionList); 
		//机构集合
		List<String> passiveIdsList = new ArrayList<>();
		
		List<EvaluateFlowingScore> evaluateFlowingScoreList = iAppEvaluateFlowingScoreService.getEvaluateFlowingScore(institution_id,institutionList, topic_id);
		//流水表中为空,表示被评价机构一个也没有被评价
		if(is_alreadyEvaluate.equals("0")) {
			for (Institution institution : institutionList) {
				passiveIdsList.add(institution.getInstitution_id());
			}
			if(evaluateFlowingScoreList!=null &&evaluateFlowingScoreList.size()>0) {//流水表中已有部分机构被评价了
				for (int j = 0; j < evaluateFlowingScoreList.size(); j++) {
					EvaluateFlowingScore efs = evaluateFlowingScoreList.get(j);
					for (int i = 0; i < institutionList.size(); i++) {
						Institution institution = institutionList.get(i);
						if(efs.getPassive_id().equals(institution.getInstitution_id())) {
							passiveIdsList.remove(efs.getPassive_id());
							break;
						}
					}
				}
				
			}
			
			
		
			//已评价
		}else if(is_alreadyEvaluate.equals("1")) {
			if(evaluateFlowingScoreList!=null &&evaluateFlowingScoreList.size()>0) {//流水表中已有部分机构被评价了
				for (int j = 0; j < evaluateFlowingScoreList.size(); j++) {
					EvaluateFlowingScore efs = evaluateFlowingScoreList.get(j);
					for (int i = 0; i < institutionList.size(); i++) {
						Institution institution = institutionList.get(i);
						if(efs.getPassive_id().equals(institution.getInstitution_id())) {
							passiveIdsList.add(efs.getPassive_id());
							break;
						}
					}
				}
				
			}else {
				passiveIdsList =null; 
			}
		}
		
		PaginationVO<Institution> paginationVO = new PaginationVO<>(); 
		if(passiveIdsList!=null && passiveIdsList.size()>0) {
			//根据为被评价机构id查询该机构信息
			paginationVO =  iAppInstitutionService.queryInstitutionsByIds(passiveIdsList, pageNo, pageSize);
		} 
		return new JsonResult(paginationVO);
	}

	
	
	
	private void regsetPassiveFlag(List<Institution> institutionList) {
		Iterator<Institution> it = institutionList.iterator();
		for (int i = 0; i < institutionList.size(); i++) {
			Institution institution = it.next();
			if(!"0".equals(institution.getDel_flag())) {
				it.remove();
				i--;
			}
			
		}
	}
	

	@ApiOperation(value = "根据机构id以及主题id以及被评价机构id--展示评分页面", notes = "根据机构id,主题id以及被评价机构id展示评分页面")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_id", value = "被评价机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "topic_id", value = "主题id", required = true, dataType = "字符串")
	})
	@RequestMapping(value="showEvaluatePage",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult showEvaluatePage(
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "passive_id", required = true)String passive_id,
			@RequestParam(value = "topic_id", required = true) String topic_id) {
		//根据topic_id来获取到评价标准id
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("active_id", active_id);
		paramMap.put("passive_id", passive_id);
		paramMap.put("topic_id", topic_id);
		//根据主题id查找该主题下的标准评价id
		String standardId = iAppTopicStandardEvaluateService.queryEvaluateStandardId(topic_id);
		paramMap.put("standardId", standardId);
		TopicEvaluateStandard data = iAppTopicEvaluateStandardService.showTopicEvaluateStandard(paramMap);
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("data", data);
		return new JsonResult(dataMap);
	}
	
	@ApiOperation(value = "根据机构id以及主题id以及被评价机构id--保存评分结果", notes = "根据机构id,主题id以及被评价机构id保存评分结果")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "topic_id", value = "主题id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "active_name", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_id", value = "被评价机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_name", value = "被评价机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "flag", value = "标志 0:不了解 1:提交", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "evaluateFlowingScoreJson", value = "流水评分表json", required = true, dataType = "json"),
		@ApiImplicitParam(paramType = "query", name = "evaluateFlowingSuggestJson", value = "流水建议表json", required = false, dataType = "json")
	
	})
	@RequestMapping(value="saveEvaluatePage",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveEvaluatePage(
			@RequestParam(value = "topic_id", required = true) String topic_id,
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "passive_id", required = true)String passive_id,
			@RequestParam(value = "active_name", required = true)String active_name,
			@RequestParam(value = "work_content", required = false)String work_content,
			@RequestParam(value = "suggest_initiate", required = false)String suggest_initiate,
			@RequestParam(value = "flag", required = true)String flag,
			@RequestParam(value = "passive_name", required = true)String passive_name,
			@RequestParam(value = "evaluateFlowingScoreJson", required = true) String evaluateFlowingScoreJson,
			@RequestParam(value = "evaluateFlowingSuggestJson", required = true) String evaluateFlowingSuggestJson
			) {
		
        JSONObject jsonObject1 = JSON.parseObject( evaluateFlowingScoreJson );
        JSONObject jsonObject2 = JSON.parseObject( evaluateFlowingSuggestJson );
        String  evaluateDetail= jsonObject1.get( "evaluateDetailList" )+"";
        String evaluateFlowing = jsonObject2.get( "EvaluateFlowingSuggest" )+"";
        
        List<TopicEvaluateStandardDetail> tesdList = JSONObject.parseArray( evaluateDetail, TopicEvaluateStandardDetail.class );
		EvaluateFlowingSuggest evaluateFlowingSuggest = JSON.parseObject(evaluateFlowing,EvaluateFlowingSuggest.class);
		String  topic_standard_id = null;
		//判断评价方 被评价方 主题id 是否已经存在--存在删除分数 
		List<EvaluateFlowingScore> queryEvaluateFlowingScoreByIds = iAppEvaluateFlowingScoreService.queryEvaluateFlowingScoreByIds(active_id, passive_id, topic_id);
		if(queryEvaluateFlowingScoreByIds !=null && queryEvaluateFlowingScoreByIds.size()>0) {
			//删除分数
			iAppEvaluateFlowingScoreService.deleteEvaluateFlowingScore(active_id, passive_id, topic_id);
		}
//		删除意见
		EvaluateFlowingSuggest evaluateFS =  iAppEvaluateFlowingSuggestService.queryTopicEvaluateSuggestByIds(active_id, passive_id, topic_id);
		if(evaluateFS !=null) {
			iAppEvaluateFlowingSuggestService.deleteEvaluateFlowingSuggest(active_id, passive_id, topic_id);
		}
		//不了解-流水评分表
		if("0".equals(flag)) {
			if(tesdList != null && tesdList.size() > 0) {
				 EvaluateFlowingScore efs = null;
					for(TopicEvaluateStandardDetail tesd : tesdList) {
						 //取第一层
						 efs = new EvaluateFlowingScore();
						 //评分id
						 efs.setScore_id(UUIDUtil.getUUID());
						 //主题id
						 efs.setTopic_id(tesd.getTopic_id());
						 //主题标准id
						 efs.setTopic_standard_id(tesd.getStandard_id());
						 efs.setStandard_detail_level(tesd.getDetail_level());
						 efs.setStandard_p_detail_id(tesd.getP_detail_id());

						 //flag
						 efs.setFlag(flag);
						 //创建时间
						 efs.setCreate_time(DateUtil.getTime());
						 //评价方id
						 efs.setActice_id(active_id);
						 //被评方id
						 efs.setPassive_id(passive_id);
						 //评价方名称
						 efs.setActive_name(active_name);
						 //被评价方名称
						 efs.setPassive_name(passive_name);
						 //计划分数
						 efs.setPlan_score(tesd.getDetail_plan_score());
						 //真实分数
						 efs.setReal_score(0.0);
						 //主题标准详情id
						 efs.setTopic_standard_detail_id(tesd.getDetail_id());
						 //主题标准详情名称
						 efs.setTopic_standard_detail_name(tesd.getDetail_name());
						 topic_standard_id = efs.getTopic_standard_id();
							//取第二层
							if(tesd.getEvaluateDetailChildList() !=null && tesd.getEvaluateDetailChildList().size()>0) {
								EvaluateFlowingScore efs2 = null;
								for (TopicEvaluateStandardDetail ed2 : tesd.getEvaluateDetailChildList()) {
									 //取第er层
									 efs2 = new EvaluateFlowingScore();
									 //评分id
									 efs2.setScore_id(UUIDUtil.getUUID());
									 //主题id
									 efs2.setTopic_id(ed2.getTopic_id());
									 //主题标准id
									 efs2.setTopic_standard_id(ed2.getStandard_id());
									 //flag
									 efs2.setFlag(flag);
									 //创建时间
									 efs2.setCreate_time(DateUtil.getTime());
									 //评价方id
									 efs2.setActice_id(active_id);
									 //被评方id
									 efs2.setPassive_id(passive_id);
									 //评价方名称
									 efs2.setActive_name(active_name);
									 //被评价方名称
									 efs2.setPassive_name(passive_name);
									 efs2.setStandard_detail_level(ed2.getDetail_level());
									 efs2.setStandard_p_detail_id(ed2.getP_detail_id());

									 //计划分数
									 efs2.setPlan_score(ed2.getDetail_plan_score());
									 //真实分数
									 efs2.setReal_score(0.0);
									 //主题标准详情id 
									 efs2.setTopic_standard_detail_id(ed2.getDetail_id());
									 //主题标准详情名称
									 efs2.setTopic_standard_detail_name(ed2.getDetail_name());
									 //存放第二层
									 //保存之前先判断数据库中是否已经存在该记录
									//int num =iAppEvaluateFlowingSuggestService.query(efs2);
									int scoreNumSecond = iAppEvaluateFlowingScoreService.save(efs2);
								}
							}
							//存放第一层
						 int scoreNum = iAppEvaluateFlowingScoreService.save(efs);
					 }
			}
				//保存-流水建议表
			 evaluateFlowingSuggest.setSuggest_id(UUIDUtil.getUUID());
			 evaluateFlowingSuggest.setActice_id(active_id);
			 evaluateFlowingSuggest.setPassive_id(passive_id);
			 evaluateFlowingSuggest.setActive_name(active_name);
			 evaluateFlowingSuggest.setPassive_name(passive_name);
			 //发起意见
			 evaluateFlowingSuggest.setSuggest_initiate(suggest_initiate); 
			 //发起内容
			 evaluateFlowingSuggest.setWork_content(work_content);  
			 //意见是否回复 默认为0
			 evaluateFlowingSuggest.setSuggest_is_answer("0");
			 evaluateFlowingSuggest.setTopic_standard_id(topic_standard_id);  
			 evaluateFlowingSuggest.setTopic_id(topic_id);
			 int suggestNum = iAppEvaluateFlowingSuggestService.save(evaluateFlowingSuggest);
			 return new JsonResult("不了解评分保存完成");
		}else {//保存
			if(tesdList != null && tesdList.size() > 0) {
				
				 EvaluateFlowingScore efs = null;
					for(TopicEvaluateStandardDetail tesd : tesdList) {
						 efs = new EvaluateFlowingScore();
						 //评分id
						 efs.setScore_id(UUIDUtil.getUUID());
						 //主题id
						 efs.setTopic_id(tesd.getTopic_id());
						 //主题标准id
						 efs.setTopic_standard_id(tesd.getStandard_id());
						 //flag
						 efs.setFlag(flag);
						 //创建时间
						 efs.setCreate_time(DateUtil.getTime());
						 //评价方id
						 efs.setActice_id(active_id);
						 //被评方id
						 efs.setPassive_id(passive_id);
						 //评价方名称
						 efs.setActive_name(active_name);
						 //被评价方名称
						 efs.setPassive_name(passive_name);
						 //详情外id
						 efs.setP_detail_id(tesd.getP_detail_id());
						 //计划分数
						 efs.setPlan_score(tesd.getDetail_plan_score());
						 //真实分数
						 efs.setReal_score(tesd.getDetail_real_score());
						 //主题标准详情id
						 efs.setTopic_standard_detail_id(tesd.getDetail_id());
						 //主题标准详情名称
						 
						 efs.setTopic_standard_detail_name(tesd.getDetail_name());
						 efs.setStandard_detail_level(tesd.getDetail_level());
						 efs.setStandard_p_detail_id(tesd.getP_detail_id());
						 topic_standard_id = efs.getTopic_standard_id();
						 Double real_score = 0.0;
						//取第二层
							if(tesd.getEvaluateDetailChildList() !=null && tesd.getEvaluateDetailChildList().size()>0) {
								EvaluateFlowingScore efs2 = null;
								for (TopicEvaluateStandardDetail ed2 : tesd.getEvaluateDetailChildList()) {
									 //取第er层
									 efs2 = new EvaluateFlowingScore();
									 //评分id
									 efs2.setScore_id(UUIDUtil.getUUID());
									 //主题id
									 efs2.setTopic_id(ed2.getTopic_id());
									 //主题标准id
									 efs2.setTopic_standard_id(ed2.getStandard_id());
									 //flag
									 efs2.setFlag(flag);
									 //创建时间
									 efs2.setCreate_time(DateUtil.getTime());
									 //评价方id
									 efs2.setActice_id(active_id);
									 //被评方id
									 efs2.setPassive_id(passive_id);
									 //评价方名称
									 efs2.setActive_name(active_name);
									 //被评价方名称
									 efs2.setPassive_name(passive_name);
									 //计划分数
									 efs2.setPlan_score(ed2.getDetail_plan_score());
									 //真实分数
									 efs2.setReal_score(ed2.getDetail_real_score());
									 efs2.setStandard_detail_level(ed2.getDetail_level());
									 efs2.setStandard_p_detail_id(ed2.getP_detail_id());
									 real_score +=efs2.getReal_score();
									 //主题标准详情id
									 efs2.setTopic_standard_detail_id(ed2.getDetail_id());
									 //主题标准详情名称
									 efs2.setTopic_standard_detail_name(ed2.getDetail_name());
									int scoreNumSecond = iAppEvaluateFlowingScoreService.save(efs2);
								}
							}
							efs.setReal_score(real_score);
						 int scoreNum = iAppEvaluateFlowingScoreService.save(efs);
					 }
			}
			 
			//保存-流水建议表
			 evaluateFlowingSuggest.setSuggest_id(UUIDUtil.getUUID());
			 evaluateFlowingSuggest.setActive_name(active_name);
			 evaluateFlowingSuggest.setActice_id(active_id);
			 if(work_content != null ||"".equals(work_content)) {
				 evaluateFlowingSuggest.setWork_content(work_content);  
			 }
			 if(suggest_initiate !=null || "".equals(suggest_initiate)) {
				 evaluateFlowingSuggest.setSuggest_initiate(suggest_initiate); 
				 
			 }
			 evaluateFlowingSuggest.setActice_id(active_id);
			 evaluateFlowingSuggest.setPassive_id(passive_id);
			 evaluateFlowingSuggest.setSuggest_is_answer("0");
			 evaluateFlowingSuggest.setPassive_name(passive_name);
			 evaluateFlowingSuggest.setTopic_standard_id(topic_standard_id);  
			 evaluateFlowingSuggest.setTopic_id(topic_id); 
			 int suggestNum = iAppEvaluateFlowingSuggestService.save(evaluateFlowingSuggest);
			 return new JsonResult("评分保存完成");
	}
}
	
	@SuppressWarnings("unused")
	@ApiOperation(value = "统计", notes = "统计")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "institution_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "机构类型(1：区级部门，2：街镇，3：企业，)", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "industry_id", value = "行业id", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "flag", value = "标志(0:非主管企业 1:主管企业)", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "topic_id", value = "主题id", required = true, dataType = "字符串")
	})
	@RequestMapping(value="queryEvaluatePassiveInstitutionByIds",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult countPassiveNum(
			@RequestParam(value = "institution_id", required = true)String institution_id,
			@RequestParam(value = "is_alreadyEvaluate", required = true)String is_alreadyEvaluate,//(0:未评价 1:已评价)
			@RequestParam(value = "institution_type", required = false)String institution_type,//机构类型(1：区级部门，2：街镇，3：企业，)
			@RequestParam(value = "industry_id", required = false)String industry_id,//行业id--针对的是企业(1:工业，2:建筑业，3：交通运输，4:物流业5:批发零售业6:住宿餐饮业7:金融业8:房地产业9:营利性服务业10:其他民营企业)
			@RequestParam(value = "flag", required = false)String flag,
			@RequestParam(value = "pageNo", required = false)Long pageNo,
			@RequestParam(value = "pageSize", required = false)Long pageSize,
			@RequestParam(value = "topic_id", required = true) String topic_id) {
		//查看评价方的类型 1:区级 2:街道 3:企业
		String institutionType = iAppTopicEvaluateService.queryInstitutionType(institution_id);
		//所有的被评价方信息集合--已评价 未评价的
		List<Institution> institutionList = iAppTopicEvaluateService.queryPassiveInstitution(institution_id,topic_id);
		//判断被评机构删除标识
		regsetPassiveFlag(institutionList); 
		if(institutionList ==null ||institutionList.size()==0) {
			return new JsonResult();
		}
		//机构集合
		List<String> passiveIdsList = new ArrayList<>();
	    List<EvaluateFlowingScore> evaluateFlowingScoreList = iAppEvaluateFlowingScoreService.getEvaluateFlowingScore(institution_id,institutionList, topic_id);
		//流水表中为空,表示被评价机构一个也没有被评价
		if("0".equals(is_alreadyEvaluate)) {
			for (Institution institution : institutionList) {
				passiveIdsList.add(institution.getInstitution_id());
			}
			if(evaluateFlowingScoreList!=null &&evaluateFlowingScoreList.size()>0) {//流水表中已有部分机构被评价了
				for (int j = 0; j < evaluateFlowingScoreList.size(); j++) {
					EvaluateFlowingScore efs = evaluateFlowingScoreList.get(j);
					for (int i = 0; i < institutionList.size(); i++) {
						Institution institution = institutionList.get(i);
						if(efs.getPassive_id().equals(institution.getInstitution_id())) {
							passiveIdsList.remove(efs.getPassive_id());
							break;
						}
					}
				}
				
			}
			//已评价
		}else if("1".equals(is_alreadyEvaluate)) {
			if(evaluateFlowingScoreList!=null &&evaluateFlowingScoreList.size()>0) {//流水表中已有部分机构被评价了
				for (int j = 0; j < evaluateFlowingScoreList.size(); j++) {
					EvaluateFlowingScore efs = evaluateFlowingScoreList.get(j);
					for (int i = 0; i < institutionList.size(); i++) {
						Institution institution = institutionList.get(i);
						if(efs.getPassive_id().equals(institution.getInstitution_id())) {
							passiveIdsList.add(efs.getPassive_id());
							break;
						}
					}
				}
				
			}else {
				passiveIdsList =null; 
			}
		}
		
		PaginationVO<Institution> paginationVO = new PaginationVO<>(); 
	   
		if("3".equals(institutionType)) {//登录方为企业--企业评价政府[区级  街道]
			if(passiveIdsList!=null && passiveIdsList.size()>0) {
				//根据为被评价机构id查询该机构信息
				paginationVO =  iAppInstitutionService.queryInstitutionsTypeGroupByIds(passiveIdsList,institution_type,pageNo,pageSize);
			} else {//为空 
				paginationVO.setTotal(0L);
				paginationVO.setDataList(null);
			}
		}
		if("1".equals(institutionType) ||"2".equals(institutionType)) {//登录方为政府--政府评价企业[主管企业 其他企业]
			if(passiveIdsList!=null && passiveIdsList.size()>0) {
				//根据为被评价机构id查询 [主管企业 其他企业]两大类
//				paginationVO =  iAppInstitutionService.queryInstitutionsCompanyTypeByIds(passiveIdsList,institution_id,pageNo,pageSize);
				paginationVO =  iAppInstitutionService.queryInstitutionsCompanyTypeByIds2(passiveIdsList,institution_type,institution_id,industry_id,pageNo,pageSize,flag);
			} else {
				paginationVO.setTotal(0L);
				paginationVO.setDataList(null);
			}
		}
		//区级政府
		List<Institution> districtInstitutionList = new ArrayList<>();
		//街道部门
		List<Institution> streetInstitutionList = new ArrayList<>();
		//主管企业
		List<Institution> chargeInstitutionList = new ArrayList<>();
		//非主管企业
		List<Institution> noChargeInstitutionList = new ArrayList<>();
		//分类 区级 街道
		if(paginationVO.getDataList() !=null && paginationVO.getDataList().size()>0) {
			for (int i = 0; i < paginationVO.getDataList().size(); i++) {
				if("1".equals(paginationVO.getDataList().get(i).getInstitution_type())) {//区级
					districtInstitutionList.add(paginationVO.getDataList().get(i));
				}else if("2".equals(paginationVO.getDataList().get(i).getInstitution_type())) { //街道
					streetInstitutionList.add(paginationVO.getDataList().get(i));
				}else if("3".equals(paginationVO.getDataList().get(i).getInstitution_type()) && institution_id.equals(paginationVO.getDataList().get(i).getInstitution_main_id())) {//主管企业id
					chargeInstitutionList.add(paginationVO.getDataList().get(i));
				}else if("3".equals(paginationVO.getDataList().get(i).getInstitution_type()) && !institution_id.equals(paginationVO.getDataList().get(i).getInstitution_main_id())) {//非主管企业
					noChargeInstitutionList.add(paginationVO.getDataList().get(i));
				}
			}	
		}
		Map<String,Object> dataMap = new HashMap<>();
		//企业评价政府--返回结果
		if("3".equals(institutionType)) {
			if(null==institution_type){
				dataMap.put("passiveIdsNum", paginationVO.getTotal());//总被评机构数--未评 已评
				dataMap.put("districtNum", districtInstitutionList.size());//总被评机构数--区级(上面的参数 传"1":)
				dataMap.put("streetNum", streetInstitutionList.size());//总被评机构数--区级(上面的参数 传"1":)
			}else if("1".equals(institution_type)){//区级部门--企业为评价方
				dataMap.put("institutionList", districtInstitutionList);
				dataMap.put("total", paginationVO.getTotal());
			}else if("2".equals(institution_type)) {//街道--企业为评价方
				dataMap.put("institutionList", streetInstitutionList);
				dataMap.put("total",paginationVO.getTotal());
			}
			
		}
		//主管企业
		List<Institution>  chargeInstitutionList1 = new ArrayList<>();//工业
		List<Institution>  chargeInstitutionList2= new ArrayList<>();//建筑业
		List<Institution>  chargeInstitutionList3 = new ArrayList<>();//交通运输
		List<Institution>  chargeInstitutionList4 = new ArrayList<>();//物流业
		List<Institution>  chargeInstitutionList5 = new ArrayList<>();//批发零售业
		List<Institution>  chargeInstitutionList6 = new ArrayList<>();//住宿餐饮业
		List<Institution>  chargeInstitutionList7 = new ArrayList<>();//金融业
		List<Institution>  chargeInstitutionList8 = new ArrayList<>();//房地产业
		List<Institution>  chargeInstitutionList9 = new ArrayList<>();//营利性服务业
		List<Institution>  chargeInstitutionList10 = new ArrayList<>();//其他民营企业
		//非主管企业
		List<Institution>  noChargeInstitutionList1 = new ArrayList<>();//工业
		List<Institution>  noChargeInstitutionList2= new ArrayList<>();//建筑业
		List<Institution>  noChargeInstitutionList3 = new ArrayList<>();//交通运输
		List<Institution>  noChargeInstitutionList4 = new ArrayList<>();//物流业
		List<Institution>  noChargeInstitutionList5 = new ArrayList<>();//批发零售业
		List<Institution>  noChargeInstitutionList6 = new ArrayList<>();//住宿餐饮业
		List<Institution>  noChargeInstitutionList7 = new ArrayList<>();//金融业
		List<Institution>  noChargeInstitutionList8 = new ArrayList<>();//房地产业
		List<Institution>  noChargeInstitutionList9 = new ArrayList<>();//营利性服务业
		List<Institution>  noChargeInstitutionList10 = new ArrayList<>();//其他民营企业
		//主管企业填充
		for (Institution chargeInstitutions : chargeInstitutionList) {
			if("1".equals(chargeInstitutions.getIndustry_id())) { //工业
				chargeInstitutionList1.add(chargeInstitutions);
			}else if("2".equals(chargeInstitutions.getIndustry_id())) {//建筑业
				chargeInstitutionList2.add(chargeInstitutions);
			}else if("3".equals(chargeInstitutions.getIndustry_id())) {//交通运输
				chargeInstitutionList3.add(chargeInstitutions);
			}else if("4".equals(chargeInstitutions.getIndustry_id())) {//物流业
				chargeInstitutionList4.add(chargeInstitutions);
			}else if("5".equals(chargeInstitutions.getIndustry_id())) {//批发零售业
				chargeInstitutionList5.add(chargeInstitutions);
			}else if("6".equals(chargeInstitutions.getIndustry_id())) {//住宿餐饮业
				chargeInstitutionList6.add(chargeInstitutions);
			}else if("7".equals(chargeInstitutions.getIndustry_id())) {//金融业
				chargeInstitutionList7.add(chargeInstitutions);
			}else if("8".equals(chargeInstitutions.getIndustry_id())) {//房地产业
				chargeInstitutionList8.add(chargeInstitutions);
			}else if("9".equals(chargeInstitutions.getIndustry_id())) {//营利性服务业
				chargeInstitutionList9.add(chargeInstitutions);
			}else if("10".equals(chargeInstitutions.getIndustry_id())) {//其他民营企业
				chargeInstitutionList10.add(chargeInstitutions);
			}
		}
		//非主管企业填充
		for (Institution noChargeInstitution : noChargeInstitutionList) {
			if("1".equals(noChargeInstitution.getIndustry_id())) { //工业
				noChargeInstitutionList1.add(noChargeInstitution);
			}else if("2".equals(noChargeInstitution.getIndustry_id())) {//建筑业
				noChargeInstitutionList2.add(noChargeInstitution);
			}else if("3".equals(noChargeInstitution.getIndustry_id())) {//交通运输
				noChargeInstitutionList3.add(noChargeInstitution);
			}else if("4".equals(noChargeInstitution.getIndustry_id())) {//物流业
				noChargeInstitutionList4.add(noChargeInstitution);
			}else if("5".equals(noChargeInstitution.getIndustry_id())) {//批发零售业
				noChargeInstitutionList5.add(noChargeInstitution);
			}else if("6".equals(noChargeInstitution.getIndustry_id())) {//住宿餐饮业
				noChargeInstitutionList6.add(noChargeInstitution);
			}else if("7".equals(noChargeInstitution.getIndustry_id())) {//金融业
				noChargeInstitutionList7.add(noChargeInstitution);
			}else if("8".equals(noChargeInstitution.getIndustry_id())) {//房地产业
				noChargeInstitutionList8.add(noChargeInstitution);
			}else if("9".equals(noChargeInstitution.getIndustry_id())) {//营利性服务业
				noChargeInstitutionList9.add(noChargeInstitution);
			}else if("10".equals(noChargeInstitution.getIndustry_id())) {//其他民营企业
				noChargeInstitutionList10.add(noChargeInstitution);
			}
		}
		
		if(!"3".equals(institutionType) ) {//第一级 [显示主管企业 非主管企业总数]
			if(null==institution_type){
				dataMap.put("passiveIdsNum", paginationVO.getTotal());//总被评机构数--未评 已评
				dataMap.put("chargeNum", chargeInstitutionList.size());//主管企业数量--
				dataMap.put("noChargeNum", noChargeInstitutionList.size());//非主管企业数量
			}
		}
		//政府评价企业--返回结果--主管企业
		if(!"3".equals(institutionType) &&"1".equals(flag) ) {//1:查询主管企业
			if(null==institution_type){
				dataMap.put("passiveIdsNum", paginationVO.getTotal());//总被评机构数--未评 已评
				dataMap.put("chargeNum", chargeInstitutionList.size());//主管企业数量--
				dataMap.put("noChargeNum", noChargeInstitutionList.size());//非主管企业数量
			}else if(institution_type !=null && "3".equals(institution_type) &&industry_id==null) {
				dataMap.put("industryNum", chargeInstitutionList1.size()); //工业数量
				dataMap.put("buildingNum", chargeInstitutionList2.size()); //建筑业数量
				dataMap.put("trafficNum", chargeInstitutionList3.size());  //交通运输行业
				dataMap.put("logisticsNum", chargeInstitutionList4.size()); //物流行业
				dataMap.put("wholesaleAndRetailTradeNum", chargeInstitutionList5.size()); //批发零售业
				dataMap.put("accommodationCateringIndustryNum", chargeInstitutionList6.size()); //住宿餐饮业
				dataMap.put("financeNum", chargeInstitutionList7.size()); //金融业
				dataMap.put("realEstateNum", chargeInstitutionList8.size()); //房地产业数量
				dataMap.put("forProfitServiceIndustryNum", chargeInstitutionList9.size()); //营利性服务业
				dataMap.put("othersNum", chargeInstitutionList10.size());//其他民营企业数量
			}else if("1".equals(industry_id)){//工业
				dataMap.put("institutionList", chargeInstitutionList1);//主管企业--工业
				dataMap.put("total", paginationVO.getTotal());
			}else if("2".equals(industry_id)) {//建筑业
				dataMap.put("institutionList", chargeInstitutionList2);//主管企业--建筑业
				dataMap.put("total", paginationVO.getTotal());
			}else if("3".equals(industry_id)) {//交通运输
				dataMap.put("institutionList", chargeInstitutionList3);//主管企业--交通运输
				dataMap.put("total", paginationVO.getTotal());
			}else if("4".equals(industry_id)) {//物流业
				dataMap.put("institutionList", chargeInstitutionList4);//主管企业--物流业
				dataMap.put("total", paginationVO.getTotal());
			}else if("5".equals(industry_id)) {//批发零售业
				dataMap.put("institutionList", chargeInstitutionList5);//主管企业--批发零售业
				dataMap.put("total", paginationVO.getTotal());
			}else if("6".equals(industry_id)) {//住宿餐饮业
				dataMap.put("institutionList", chargeInstitutionList6);//主管企业--住宿餐饮业
				dataMap.put("total",paginationVO.getTotal());
			}else if("7".equals(industry_id)) {//金融业
				dataMap.put("institutionList", chargeInstitutionList7);//主管企业--金融业
				dataMap.put("total", paginationVO.getTotal());
			}else if("8".equals(industry_id)) {//房地产业
				dataMap.put("institutionList", chargeInstitutionList8);//主管企业--房地产业
				dataMap.put("total", paginationVO.getTotal());
			}else if("9".equals(industry_id)) {//营利性服务业
				dataMap.put("institutionList", chargeInstitutionList9);//主管企业--营利性服务业
				dataMap.put("total", paginationVO.getTotal());
			}else if("10".equals(industry_id)) {//其他民营企业
				dataMap.put("institutionList", chargeInstitutionList10);//主管企业--其他民营企业
				dataMap.put("total", paginationVO.getTotal());
			}
		}
		//政府评价企业--返回结果--非主管企业
		if(!"3".equals(institutionType) &&"0".equals(flag) ) {//1:查询非主管企业
			if(null==institution_type){
				dataMap.put("passiveIdsNum", paginationVO.getTotal());//总被评机构数--未评 已评
				dataMap.put("chargeNum", chargeInstitutionList.size());//主管企业数量--
				dataMap.put("noChargeNum", noChargeInstitutionList.size());//非主管企业数量
			}else if(institution_type !=null && "3".equals(institution_type) &&industry_id==null) {
				dataMap.put("industryNum", noChargeInstitutionList1.size()); //工业数量
				dataMap.put("buildingNum",  noChargeInstitutionList2.size()); //建筑业数量
				dataMap.put("trafficNum",  noChargeInstitutionList3.size());  //交通运输行业
				dataMap.put("logisticsNum",  noChargeInstitutionList4.size()); //物流行业
				dataMap.put("wholesaleAndRetailTradeNum",  noChargeInstitutionList5.size()); //批发零售业
				dataMap.put("accommodationCateringIndustryNum",  noChargeInstitutionList6.size()); //住宿餐饮业
				dataMap.put("financeNum",  noChargeInstitutionList7.size()); //金融业
				dataMap.put("realEstateNum",  noChargeInstitutionList8.size()); //房地产业数量
				dataMap.put("forProfitServiceIndustryNum",  noChargeInstitutionList9.size()); //营利性服务业
				dataMap.put("othersNum",  noChargeInstitutionList10.size());//其他民营企业数量
			}else if("1".equals(industry_id)){//工业
				dataMap.put("institutionList", noChargeInstitutionList1);//主管企业--工业
				dataMap.put("total", paginationVO.getTotal());
			}else if("2".equals(industry_id)) {//建筑业
				dataMap.put("institutionList", noChargeInstitutionList2);//主管企业--建筑业
				dataMap.put("total", paginationVO.getTotal());
			}else if("3".equals(industry_id)) {//交通运输
				dataMap.put("institutionList", noChargeInstitutionList3);//主管企业--交通运输
				dataMap.put("total", paginationVO.getTotal());
			}else if("4".equals(industry_id)) {//物流业
				dataMap.put("institutionList", noChargeInstitutionList4);//主管企业--物流业
				dataMap.put("total", paginationVO.getTotal());
			}else if("5".equals(industry_id)) {//批发零售业
				dataMap.put("institutionList", noChargeInstitutionList5);//主管企业--批发零售业
				dataMap.put("total", paginationVO.getTotal());
			}else if("6".equals(industry_id)) {//住宿餐饮业
				dataMap.put("institutionList", noChargeInstitutionList6);//主管企业--住宿餐饮业
				dataMap.put("total", paginationVO.getTotal());
			}else if("7".equals(industry_id)) {//金融业
				dataMap.put("institutionList", noChargeInstitutionList7);//主管企业--金融业
				dataMap.put("total", paginationVO.getTotal());
			}else if("8".equals(industry_id)) {//房地产业
				dataMap.put("institutionList", noChargeInstitutionList8);//主管企业--房地产业
				dataMap.put("total", paginationVO.getTotal());
			}else if("9".equals(industry_id)) {//营利性服务业
				dataMap.put("institutionList", noChargeInstitutionList9);//主管企业--营利性服务业
				dataMap.put("total", paginationVO.getTotal());
			}else if("10".equals(industry_id)) {//其他民营企业
				dataMap.put("institutionList", noChargeInstitutionList10);//主管企业--其他民营企业
				dataMap.put("total",paginationVO.getTotal());
			}
		}
		
		
		return new JsonResult(dataMap);
	}
	@ApiOperation(value = "双向评价搜索", notes = "双向评价搜索")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "topic_id", value = "主题id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "active_id", value = "机构id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "passive_name", value = "被评价机构", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "is_alreadyEvaluate", value = "0:未评价  1:已评价", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "institution_type", value = "机构类型(1：区级部门，2：街镇，3：企业，)", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "industry_id", value = "行业id", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "flag", value = "标志(0:非主管企业 1:主管企业)", required = false, dataType = "字符串")
	})
	@RequestMapping(value="queryByCondition",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult queryByCondition(
			@RequestParam(value = "topic_id", required = true) String topic_id,
			@RequestParam(value = "active_id", required = true)String active_id,
			@RequestParam(value = "passive_name", required = true)String passive_name,
			@RequestParam(value = "is_alreadyEvaluate", required = true)String is_alreadyEvaluate,
			@RequestParam(value = "institution_type", required = false)String institution_type,
			@RequestParam(value = "industry_id", required = false)String industry_id,
			@RequestParam(value = "flag", required = false)String flag) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("topic_id", topic_id);
		paramMap.put("active_id", active_id);
		paramMap.put("passive_name", passive_name);
		paramMap.put("is_alreadyEvaluate", is_alreadyEvaluate);
		if(institution_type !=null && !"".equals(institution_type)) {
			paramMap.put("institution_type", institution_type);
		}
		if(industry_id !=null && !"".equals(industry_id)) {
			paramMap.put("industry_id", industry_id);
		}
		if(flag !=null && !"".equals(flag)) {
			paramMap.put("flag", flag);
		}
		if(StringUtils.isEmpty(passive_name)) {
			return new JsonResult("请输入搜索内容");
		}
		//根据机构id 主题id is_alreadyEvaluate(0:未完成 1:已完成 )查询流水建议表中的被评价方id
//		List<Institution> passiveIds = iAppEvaluateFlowingSuggestService.queryPassiveInstitutions(paramMap);
		List<Institution> passiveIds = iAppEvaluateFlowingSuggestService.queryPassiveInstitutions2(paramMap);
		Map<String,Object> paramMap2 = new HashMap<>();
		if(passiveIds == null || passiveIds.size()==0) {
			paramMap2.put("total", 0);
		}else {
			paramMap2.put("total", passiveIds.size());
			paramMap2.put("InstitutionList", passiveIds);
		}
		
		return new JsonResult(paramMap2);
		
	} 
	
    @ApiOperation( value = "获取机构详情", notes = "获取机构详情" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "institution_id", value = "机构ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getInstitutionIntroduce", method = RequestMethod.GET )
    public String getCompanyElegantDetail(HttpServletRequest request,
            @RequestParam( value = "institution_id", required = true ) String institution_id) {
        Institution institution = iAppInstitutionService.getInstitutionIntroduce( institution_id );
        request.setAttribute("institution",institution);
        request.setAttribute("requestUrl",String.valueOf( request.getRequestURL() ).split( "app" )[0]);
        return "/web/html/institution/app/institutionIntroduce";
    }




	
	/**
	 * 验证参数
	 */
	private void checkParam(String institution_id, String topic_type, String evaluate_state, String is_evaluate) {
		if (StringUtils.isEmpty(institution_id)) {
			throw new RuntimeException("请先登录");
		}
		if(StringUtil.isEmpty(topic_type)) {
			throw new RuntimeException("请输入 2:年度评价,1:半年评价");
		}
		if(StringUtil.isEmpty(evaluate_state)) {
			throw new RuntimeException("请输入评价状态(0：待发布，1：未开始，2：评价中，3：已结束，4：已撤销，5：已过期，默认1)");
		}
		if(StringUtil.isEmpty(is_evaluate)) {
			throw new RuntimeException("请输入 完成评价(0：否，1：是，默认0)");
		}
	}
	
	
	
	
	
	
}
