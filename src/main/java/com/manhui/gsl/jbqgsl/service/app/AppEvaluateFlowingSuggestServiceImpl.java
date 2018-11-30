
/**
* @Title: AppEvaluateFlowingSuggestServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingSuggestMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppInstitutionMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicActiveInfoMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicEvaluateMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTopicPassiveInfoMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;


@Service
public class AppEvaluateFlowingSuggestServiceImpl implements IAppEvaluateFlowingSuggestService {
	@Resource
	private AppEvaluateFlowingSuggestMapper appEvaluateFlowingSuggestMapper;
	@Resource
	private AppTopicEvaluateMapper appTopicEvaluateMapper;
	@Resource
	private AppTopicActiveInfoMapper appTopicActiveInfoMapper;
	@Resource
	private AppInstitutionMapper appInstitutionMapper;
	@Resource
	private AppTopicPassiveInfoMapper appTopicPassiveInfoMapper;
	@Override
	public int save(EvaluateFlowingSuggest evaluateFlowingSuggest) {
		evaluateFlowingSuggest.setCreate_time(DateUtil.getTime());
		int  num = appEvaluateFlowingSuggestMapper.save(evaluateFlowingSuggest);
		/**
		 * 记录意见流水表中已经被评价数量
		 */
		List<Institution> queryPassiveInstitution = appTopicEvaluateMapper.queryPassiveInstitution(evaluateFlowingSuggest.getActice_id(),evaluateFlowingSuggest.getTopic_id());
		Iterator<Institution> it = queryPassiveInstitution.iterator();
		for (int i = 0; i < queryPassiveInstitution.size(); i++) {
			Institution institution = it.next();
			if(!"0".equals(institution.getDel_flag())) {
				it.remove();
				i--;
			}
			
		}
		/**
		 * 修改主题评价方信息表中完成状态
		 * 
		 */
		
		int countNum = appEvaluateFlowingSuggestMapper.querySuggestByIds(evaluateFlowingSuggest);
		if(countNum == queryPassiveInstitution.size()){
			String actice_id = evaluateFlowingSuggest.getActice_id();
			String topic_id = evaluateFlowingSuggest.getTopic_id(); 
			String evaluate_time = DateUtil.getTime();
			int stateNum = appTopicActiveInfoMapper.updateState(actice_id,topic_id,evaluate_time);
			
		}
	 
		return num;
	}

	@Override
	public String querySuggestIsAnswer(String passive_id) {
	
		return null;
	}

	@Override
	public List<EvaluateFlowingSuggest> querySuggest(String institution_id) {
		
		return appEvaluateFlowingSuggestMapper.querySuggest(institution_id);
	}
	/**
	 * 根据机构id 主题id is_alreadyEvaluate(0:未完成 1:已完成 )查询流水建议表中的被评价方id
	 */
	@Override
	public List<Institution> queryPassiveInstitutions(Map<String, Object> paramMap) {
		String topic_id = String.valueOf(paramMap.get("topic_id"));
		String active_id = String.valueOf(paramMap.get("active_id"));
		String passive_name = String.valueOf(paramMap.get("passive_name"));
		String is_alreadyEvaluate = String.valueOf(paramMap.get("is_alreadyEvaluate"));
		//流水意见表中查询该主题 该评价方下已评价的被评价方id
		List<String> passiveIds =  appEvaluateFlowingSuggestMapper.queryPassiveInstitutions(topic_id,active_id);
		List<Institution> institutionList = new ArrayList<>();
		//完成评价 就将查询到的被评价机构id返回
		if("1".equals(is_alreadyEvaluate)) {
			if(passiveIds == null || passiveIds.size()==0) {
				return institutionList; //返回个空集合--表示没有评价机构
			}
			institutionList = appInstitutionMapper.queryInstitutionYesByLike(passiveIds,passive_name);
		}
		//未评价 则取查询到的被评价机构id的反集
		if("0".equals(is_alreadyEvaluate)) {//未评价就根据主题id找被评价信息表中的机构id(被评价方信息)
			List<String> passiveInfoIds = appTopicPassiveInfoMapper.queryInstitutionsInfo(topic_id);
			
			institutionList = appInstitutionMapper.queryInstitutionNoByLike(passiveInfoIds,passive_name);
		}
		return institutionList;
	}
	/**
	 * 根据机构id 主题id is_alreadyEvaluate(0:未完成 1:已完成 )查询流水建议表中的被评价方信息
	 */
	@Override
	public EvaluateFlowingSuggest queryIsSuggest(ForthwithEvaluate forthwithEvaluate) {
		EvaluateFlowingSuggest flowingSuggest = appEvaluateFlowingSuggestMapper.query(forthwithEvaluate);
		return  flowingSuggest;
	}
	/**
	 * 根据机构id 主题id is_alreadyEvaluate(0:未完成 1:已完成 )查询流水建议表中的被评价方id
	 */
	@Override
	public List<Institution> queryPassiveInstitutions2(Map<String, Object> paramMap) {
		String topic_id = String.valueOf(paramMap.get("topic_id"));
		String active_id = String.valueOf(paramMap.get("active_id"));
		String passive_name = String.valueOf(paramMap.get("passive_name"));
		String is_alreadyEvaluate = String.valueOf(paramMap.get("is_alreadyEvaluate"));
		String institution_type = String.valueOf(paramMap.get("institution_type"));
		String flag = String.valueOf(paramMap.get("flag"));
		String industry_id = String.valueOf(paramMap.get("industry_id"));
		//流水意见表中查询该主题 该评价方下已评价的被评价方id
		List<String> passiveIds =  appEvaluateFlowingSuggestMapper.queryPassiveInstitutions(topic_id,active_id);
		List<Institution> institutionList = new ArrayList<>();
		//完成评价 就将查询到的被评价机构id返回
		if("1".equals(is_alreadyEvaluate)) {
			if(passiveIds == null || passiveIds.size()==0) {
				return institutionList; //返回个空集合--表示没有评价机构
			}
			institutionList = appInstitutionMapper.queryInstitutionYesByLike(passiveIds,passive_name);
		}
		
		//未评价 则取查询到的被评价机构id的反集
		if("0".equals(is_alreadyEvaluate)) {//未评价就根据主题id找被评价信息表中的机构id(被评价方信息)
			List<String> passiveInfoIds = appTopicPassiveInfoMapper.queryInstitutionsInfo(topic_id);
			
			institutionList = appInstitutionMapper.queryInstitutionNoByLike(passiveInfoIds,passive_name);
		}
		
		//搜索--条件筛选
		List<Institution> institutionList2 = new ArrayList<>();
		
		//企业评价政府--区分区级 街道机构<不为空时候>
		if(institution_type !=null && !"".equals(institution_type) &&institutionList !=null &&institutionList.size()>0) {
			if("1".equals(institution_type)) {//1：区级部门，(展示区级部门)
				for (Institution institution : institutionList) {
					if("1".equals(institution.getInstitution_type())) {
						institutionList2.add(institution);
					}
				}
			}
			if("2".equals(institution_type)) {//2：街镇，(展示街道)
				for (Institution institution : institutionList) {
					if("2".equals(institution.getInstitution_type())) {
						institutionList2.add(institution);
					}
				}
			}
		}
		//企业评价政府--区分区级 街道机构<为空时候>
		if(institution_type !=null && !"".equals(institution_type)&&(institutionList ==null ||institutionList.size()==0)) {
			if("1".equals(institution_type)) {//1：区级部门，(展示区级部门)
				institutionList2=null;
			}
			if("2".equals(institution_type)) {//2：街镇，(展示街道)
				institutionList2=null;
			}
		}
		//政府评价企业<企业不为空>
		if(institution_type !=null && !"".equals(institution_type)&&institutionList !=null &&institutionList.size()>0&&flag !=null ) {
			if("0".equals(flag)&& industry_id !=null &&!"".equals(industry_id)) {//查询其他企业下面的行业
				for (Institution institution : institutionList) {
					if(!active_id.equals(institution.getInstitution_main_id()) ) {//登陆的政府账号与企业的主管账号匹配相等表示为其主管企业
						if( industry_id.equals(institution.getIndustry_id())) {//进行行业匹配
							institutionList2.add(institution);
						}
					}
				}
				
			}
			if("1".equals(flag)&& industry_id !=null &&!"".equals(industry_id)) {//查询主管企业下面的行业
				for (Institution institution : institutionList) {
					if(active_id.equals(institution.getInstitution_main_id()) ) {//登陆的政府账号与企业的主管账号匹配相等表示为其主管企业
						if( industry_id.equals(institution.getIndustry_id())) {//进行行业匹配
							institutionList2.add(institution);
						}
					}
				}
				
			}
		}
		//政府评价企业<企业为空>
		if(institution_type !=null && !"".equals(institution_type)&&(institutionList ==null ||institutionList.size()==0)&&flag !=null ) {
			if("0".equals(flag)&& industry_id !=null &&!"".equals(industry_id)) {//查询其他企业下面的行业
				institutionList2=null;
			}
			if("1".equals(flag)&& industry_id !=null &&!"".equals(industry_id)) {//查询主管企业下面的行业
				institutionList2=null;
			}
		}
		return institutionList2;
	}
	/**
	 * 根据主题ID 评价方ID 被评价方ID 查找流水意见表
	 */
	@Override
	public EvaluateFlowingSuggest queryTopicEvaluateSuggestByIds(String active_id, String passive_id, String topic_id) {
		return appEvaluateFlowingSuggestMapper.queryTopicEvaluateSuggestByIds(active_id, passive_id, topic_id);
	}
	/**
	 * 根据主题ID 评价方ID 被评价方ID 删除流水意见表
	 */
	@Override
	public void deleteEvaluateFlowingSuggest(String active_id, String passive_id, String topic_id) {
		appEvaluateFlowingSuggestMapper.deleteEvaluateFlowingSuggest( active_id, passive_id, topic_id);
	}
	
}
