package com.manhui.gsl.jbqgsl.service.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.EvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.dao.ForthwithEvaluateMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;

/**
 * @类名称 ForthwithEvaluateServiceImpl.java
 * @类描述 <pre>即时回复模块service层接口实现，主要处理业务逻辑</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年8月10日11:46:51
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
@Service
public class ForthwithEvaluateServiceImpl implements IForthwithEvaluateService {

	@Resource
	private ForthwithEvaluateMapper forthwithEvaluateMapper;
	
	@Resource
	private EvaluateDetailMapper evaluateDetailMapper;

	@Override
	public List<ForthwithEvaluate> queryUnevaluateList(String evaluate_active_name,Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> conditions = new HashMap<>();
        conditions.put( "evaluate_active_name", evaluate_active_name );
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
		return forthwithEvaluateMapper.queryUnevaluateList(conditions);
	}

	@Override
	public Integer queryUnevaluateTotal(String evaluate_active_name,Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> conditions = new HashMap<>();
		conditions.put( "evaluate_active_name", evaluate_active_name );
		return forthwithEvaluateMapper.queryUnevaluateTotal(conditions);
	}

	@Override
	public List<ForthwithEvaluate> queryEvaluateList(String evaluate_active_name,Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> conditions = new HashMap<>();
        conditions.put( "evaluate_active_name", evaluate_active_name );
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
		return forthwithEvaluateMapper.queryEvaluateList(conditions);
	}

	@Override
	public Integer queryEvaluateTotal(String evaluate_active_name,Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> conditions = new HashMap<>();
        conditions.put( "evaluate_active_name", evaluate_active_name );
		return forthwithEvaluateMapper.queryEvaluateTotal(conditions);
	}

	@Override
	public Map queryEvaluate(String forthwith_id,String passive_id,String active_id) {
		// TODO Auto-generated method stub
		return forthwithEvaluateMapper.queryEvaluate(forthwith_id,passive_id,active_id);
	}

	@Override
	public ForthwithEvaluate queryForthwithEvaluate(String forthwith_id) {
		// TODO Auto-generated method stub
		return forthwithEvaluateMapper.queryForthwithEvaluate(forthwith_id);
	}

	@Override
	public List<EvaluateFlowingScore> queryScoresDetail(String forthwith_id, String passive_id, String active_id) {
		List<EvaluateFlowingScore> newDataList = new ArrayList<>();
		// 首先查出细则分数总数据
		List<EvaluateFlowingScore> fems = forthwithEvaluateMapper.queryEvaluateFlowingScores(forthwith_id, passive_id, active_id);
		//先循环1级放入集合
		for(EvaluateFlowingScore efs : fems){
			List<EvaluateFlowingScore> newData = new ArrayList<>();
			EvaluateDeatil ed = evaluateDetailMapper.queryEvaluateDeatilByKey(efs.getForthwith_standard_detail_id(), efs.getForthwith_standard_id());
			if(ed != null && ed.getDetail_level() == 1){
				//1级细则 
				newDataList.add(efs);
			}
		}
		//循环2级放入新的集合
		List<EvaluateFlowingScore> newDataLists = new ArrayList<>();
		for(EvaluateFlowingScore efs : fems){
			List<EvaluateFlowingScore> newData = new ArrayList<>();
			EvaluateDeatil ed = evaluateDetailMapper.queryEvaluateDeatilByKey(efs.getForthwith_standard_detail_id(), efs.getForthwith_standard_id());
			if(ed != null && ed.getDetail_level() == 2){
				//2级细则
				newDataLists.add(efs);
			}
		}
		
		List<EvaluateFlowingScore> reult =new ArrayList<>();
		for(EvaluateFlowingScore dataone : newDataList){
			for(EvaluateFlowingScore datatwo : newDataLists){
				EvaluateDeatil ed1 = evaluateDetailMapper.queryEvaluateDeatilByKey(dataone.getForthwith_standard_detail_id(), dataone.getForthwith_standard_id());
				EvaluateDeatil ed2 = evaluateDetailMapper.queryEvaluateDeatilByKey(datatwo.getForthwith_standard_detail_id(), datatwo.getForthwith_standard_id());
				if(ed1.getDetail_id().equals(ed2.getP_detail_id())){
					datatwo.setP_detail_id(dataone.getForthwith_standard_detail_id());
					reult.add(datatwo);
				}
			} 
			//dataone.setEvaluateFlowingScoreList(chiled);
			dataone.setP_detail_id("-1");
			reult.add(dataone);
		}  
		return reult;
	}

	@Override
	public void updateSuggestAnswer(String suggest_id, String suggest_answer) {
		// TODO Auto-generated method stub
		forthwithEvaluateMapper.updateSuggestAnswer(suggest_id, suggest_answer);
	} 
}
