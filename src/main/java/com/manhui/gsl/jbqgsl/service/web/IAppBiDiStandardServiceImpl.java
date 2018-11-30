

package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppBiDiStandardMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppEvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.service.app.IAppBiDiStandardService;

/**
* @Title: IAppBiDiStandardServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(评价标准service实现层)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class IAppBiDiStandardServiceImpl implements IAppBiDiStandardService {
	@Resource
	private AppBiDiStandardMapper appBiDiStandardMapper;
	@Resource
	private AppEvaluateDetailMapper appEvaluateDetailMapper;
	@Override
	public EvaluateStandard showEvaluateStandard(String standardId) {
		EvaluateStandard data = appBiDiStandardMapper.showEvaluateStandard(standardId);
		List<EvaluateDeatil> paramData = appEvaluateDetailMapper.showEvaluateDeatil(standardId, null, 1);
		for(EvaluateDeatil param : paramData){
			List<EvaluateDeatil> ed = appEvaluateDetailMapper.showEvaluateDeatil(null, param.getDetail_id(), 2);
			param.setEvaluateDetailChildList(ed);
		}
		data.setEvaluateDetailList(paramData);
		return data;
	}
	@Override
	public List<String> queryIsEffectStandard(List<String> standardIds) {
		
		return appBiDiStandardMapper.queryIsEffectStandard(standardIds);
	}
	
}
