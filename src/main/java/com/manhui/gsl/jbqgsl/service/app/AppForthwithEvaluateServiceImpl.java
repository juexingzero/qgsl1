package com.manhui.gsl.jbqgsl.service.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.dao.EvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppBiDiStandardMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateFlowingSuggestMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppForthwithEvaluateMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppInstitutionMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;

/**
 * @Title: AppForthwithEvaluateServiceImpl.java
 * @Package com.manhui.gsl.jbqgsl.service.impl
 * @Description: TODO(即时评价模块service层接口实现层)
 * @author LiuBin
 * @date 2018年8月17日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Service
public class AppForthwithEvaluateServiceImpl implements IAppForthwithEvaluateService {
	@Resource
	private AppForthwithEvaluateMapper iAppForthwithEvaluateMapper;
	@Resource
	private AppEvaluateFlowingScoreMapper iAppEvaluateFlowingScoreMapper;
	@Resource
	private AppEvaluateFlowingSuggestMapper iAppEvaluateFlowingSuggestMapper;
	@Resource
	private AppBiDiStandardMapper iAppBiDiStandardMapper;
	@Resource
	private AppEvaluateDetailMapper iAppEvaluateDetailMapper;
	@Resource
	private AppInstitutionMapper  iAppInstitutionMapper;
	@Resource
	private AppEvaluateDetailMapper IAppEvaluateDetailMapper;

	/**
	 * 根据机构id来获取到即时评价详情
	 */
	@Override
	public List<ForthwithEvaluate> getForthwithEvaluateIds(String institution_id) {

		return iAppForthwithEvaluateMapper.getForthwithEvaluateIds(institution_id);
	}

	@Override
	public String queryInstitutionType(String institution_id) {

		return iAppForthwithEvaluateMapper.queryInstitutionType(institution_id);
	}

	@Override
	public List<Institution> queryInstitution(List<String> passiveInstitutionIdList) {

		return iAppForthwithEvaluateMapper.queryInstitution(passiveInstitutionIdList);
	}

	@Override
	public String queryStandardId(String standard_belonged) {
		return iAppForthwithEvaluateMapper.queryStandardId(standard_belonged);
	}
/**
 * 查询展示即时评价流水以及建议
 */
	@Override
	public EvaluateStandard showTopicEvaluateStandard(Map<String, Object> paramMap) {
		String standard_id = String.valueOf(paramMap.get("standard_id"));
		String active_id = String.valueOf(paramMap.get("active_id"));
		String passive_id = String.valueOf(paramMap.get("passive_id"));
		String forthwith_id = String.valueOf(paramMap.get("forthwith_id"));
		
		//评价标准
		double totalRealScore =0;
		EvaluateStandard data = iAppBiDiStandardMapper.showEvaluateStandard(standard_id);
		//评价详情第一层
		List<EvaluateDeatil> paramData = iAppEvaluateDetailMapper.showEvaluateDeatil(standard_id, null, 1);
		for (EvaluateDeatil param : paramData) {
			List<EvaluateFlowingScore> qfs = iAppEvaluateFlowingScoreMapper.queryEvaluateFlowingScore2(forthwith_id, standard_id, param.getDetail_id(), active_id, passive_id);
			Double total = 0.0;
			for (EvaluateFlowingScore ed : qfs) {
				if(param.getDetail_id().equals(ed.getForthwith_standard_detail_id())) {
				//第一级总分	
				total+=ed.getReal_score();
					//该评价中总分
					totalRealScore+=ed.getReal_score();
				}
				param.setDetail_real_score(total);
				param.setCreate_time(ed.getCreate_time());
			}
			//即时评价第一层下的具体细则
			List<EvaluateDeatil> ed = iAppEvaluateDetailMapper.showEvaluateDeatil(null, param.getDetail_id(), 2);
			for (EvaluateDeatil ed2 : ed) {
				List<EvaluateFlowingScore> qfs2 = iAppEvaluateFlowingScoreMapper.queryEvaluateFlowingScore2(forthwith_id, standard_id, ed2.getDetail_id(), active_id, passive_id);
				Double total2 = 0.0;
				for (EvaluateFlowingScore t2 : qfs2) {
					if(ed2.getDetail_id().equals(t2.getForthwith_standard_detail_id())) {
						total=t2.getReal_score();
						}
					ed2.setDetail_real_score(total);
				}
			}
			param.setEvaluateDetailChildList(ed);
	
		}
		//将评价意见数据放进去
		EvaluateFlowingSuggest efs =  iAppEvaluateFlowingSuggestMapper.queryContentAndSuggestByIds2(paramMap);
		if(efs !=null) {
			data.setSuggest_is_answer(efs.getSuggest_answer());
			data.setWork_content(efs.getWork_content());
			data.setSuggest_initiate(efs.getSuggest_initiate());
		}else {
			//data.setSuggest_is_answer(efs.getSuggest_answer());
			data.setWork_content("");
			data.setSuggest_initiate("");
		}
		data.setReal_score(totalRealScore);
		data.setEvaluateDetailList(paramData);
		return data;
	}
	/**
	 * 根据被评价机构id获取到即时id以及评价方id
	 */
	@Override
	public List<EvaluateFlowingScore> queryForthwithFlowingScore(String passive_id) {
		
		return iAppEvaluateFlowingScoreMapper.queryForthwithIdByPassiveId(passive_id);
	}

	@Override
	public int save(ForthwithEvaluate fwe) {
		fwe.setEvaluate_time(DateUtil.getTime());
		fwe.setCreate_time(DateUtil.getTime());
		return iAppForthwithEvaluateMapper.save(fwe);
	}

	@Override
	public PaginationVO<ForthwithEvaluate> queryForthwithObj(String institution_id,long pageNo, long pageSize) {
		PaginationVO<ForthwithEvaluate> pg = new PaginationVO<>();
		Long total = iAppForthwithEvaluateMapper.queryTotal(institution_id);
		if(total<0) {
			throw new RuntimeException("您没有即时评价历史");
		}
		pg.setTotal(total);
		pageNo =(pageNo-1)*pageSize;
		pg.setDataList(iAppForthwithEvaluateMapper.queryForthwithObj(institution_id,pageNo,pageSize));
		return pg;
	}

	@Override
	public String getMaxTopicNumber() {
		
		return iAppForthwithEvaluateMapper.getMaxTopicNumber();
	}

	@Override
	public String queryForthwithId(Map<String, Object> paramMap) {
		String active_id = String.valueOf(paramMap.get("active_id"));
		String passive_id = String.valueOf(paramMap.get("passive_id"));
		return iAppForthwithEvaluateMapper.queryForthwithId(active_id,passive_id);
	}

	@Override
	public int queryCountForthwith(ForthwithEvaluate fwe) {
		return iAppForthwithEvaluateMapper.queryCountForthwith(fwe);
	}

	@Override
	public List<Institution> queryPassiveInstitutions(Map<String, Object> paramMap) {
		String active_id = String.valueOf(paramMap.get("active_id"));
		String passive_name = String.valueOf(paramMap.get("passive_name"));
		String institution_type = String.valueOf(paramMap.get("institution_type"));
//		List<String> passiveIds = iAppForthwithEvaluateMapper.queryPassiveIds(active_id);
		List<Institution> institutionList = iAppInstitutionMapper.queryInstitutionByLike3(passive_name);
		List<Institution> institutionList2 = new ArrayList<>();
		if(institutionList !=null && institutionList.size()>0) {
			for (Institution institution : institutionList) {
				if(institution_type.equals(institution.getInstitution_type())) {//区级以及街道一起
					institutionList2.add(institution);
				}
			}
		}
		return institutionList2;
	}
	/**
	 * 根据评价方查询已经被评价的机构
	 */
	@Override
	public List<String> queryPassiveIds(String institution_id) {
		String active_id = institution_id;
		return iAppForthwithEvaluateMapper.queryPassiveIds(active_id);
	}
	/**
	 * 即时评价展打分详情页面
	 */
	@Override
	public EvaluateStandard showForthwithEvaluateStandard(Map<String, Object> paramMap) {
		String standard_id = String.valueOf(paramMap.get("standard_id"));
		EvaluateStandard data = iAppBiDiStandardMapper.showEvaluateStandard(standard_id);
		List<EvaluateDeatil> paramData = IAppEvaluateDetailMapper.showEvaluateDeatil(standard_id, null, 1);
		for(EvaluateDeatil param : paramData){
			List<EvaluateDeatil> ed = IAppEvaluateDetailMapper.showEvaluateDeatil(null, param.getDetail_id(), 2);
			param.setEvaluateDetailChildList(ed);
		}
		data.setEvaluateDetailList(paramData);
		data.setWork_content("");
		data.setSuggest_initiate("");
		
		return data;
	} 

	@Override
	public PaginationVO<ForthwithEvaluate> queryForthwithList(String institution_id, Long pageNo, Long pageSize) {
		PaginationVO<ForthwithEvaluate> pg = new PaginationVO<>();
		Long total = iAppForthwithEvaluateMapper.queryTotal(institution_id);
		if(total<0) {
			throw new RuntimeException("您没有即时评价历史");
		}
		pg.setTotal(total);
		if(pageNo !=null) {
			pageNo =(pageNo-1)*pageSize;
		}
		pg.setDataList(iAppForthwithEvaluateMapper.queryForthwithList(institution_id,pageNo,pageSize));
		return pg;
	}

	@Override
	public PaginationVO<ForthwithEvaluate> queryForthwithList2(String institution_id, Long pageNo, Long pageSize,
			String institution_type) {
		PaginationVO<ForthwithEvaluate> pg = new PaginationVO<>();
		Long total = iAppForthwithEvaluateMapper.queryTotal(institution_id);
		if(total<0) {
			throw new RuntimeException("您没有即时评价历史");
		}
		pg.setTotal(total);
		if(pageNo !=null) {
			pageNo =(pageNo-1)*pageSize;
		}
		pg.setDataList(iAppForthwithEvaluateMapper.queryForthwithList2(institution_id,pageNo,pageSize,institution_type));
		return pg;
	}



	
}
