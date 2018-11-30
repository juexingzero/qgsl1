
/**
* @Title: EvaluateDetailImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(细则评价接口实现类)
* @author LiuBin
* @date 2018年8月8日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.EvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;


@Service
public class EvaluateDetailImpl implements IEvaluateDetailService {
	
	@Resource
	private EvaluateDetailMapper evaluateDetailMapper;

	@Override
	public void save(EvaluateDeatil evaluateDeatil) {
		// TODO Auto-generated method stub
		evaluateDetailMapper.save(evaluateDeatil);
	}

	@Override
	public void delete(String standard_id,String detail_id) {
		// TODO Auto-generated method stub
		evaluateDetailMapper.delete(standard_id,detail_id);
	}

	@Override
	public List<String> queryDetailNameByLevel2(String standard_id) {
		// TODO Auto-generated method stub
		return evaluateDetailMapper.queryDetailNameByLevel2(standard_id);
	}

	@Override
	public EvaluateDeatil queryEvaluateDeatilByKey(String detail_id, String standard_id) {
		// TODO Auto-generated method stub
		return evaluateDetailMapper.queryEvaluateDeatilByKey(detail_id, standard_id);
	}

	@Override
	public List<EvaluateDeatil> queryEvaluateDeatil(String standard_id) {
		// TODO Auto-generated method stub
		return evaluateDetailMapper.showEvaluateDeatil(standard_id, null, null);
	}

	@Override
	public void update(EvaluateDeatil evaluateDeatil) {
		// TODO Auto-generated method stub
		evaluateDetailMapper.update(evaluateDeatil);
	}

	@Override
	public Integer queryScoreCount(String standard_id, String p_detail_id) {
		// TODO Auto-generated method stub
		return evaluateDetailMapper.queryScoreCount(standard_id, p_detail_id);
	}

	@Override
	public List<EvaluateDeatil> queryEvaluateDeatilList(String standard_id, String p_detail_id) {
		// TODO Auto-generated method stub
		return evaluateDetailMapper.queryEvaluateDeatilList(standard_id, p_detail_id);
	}

}
