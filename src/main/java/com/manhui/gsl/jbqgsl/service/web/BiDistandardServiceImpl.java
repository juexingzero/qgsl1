
/**
* @Title: BiDistandardServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(后台-双向评价-标准库设置实现层)
* @author LiuBin
* @date 2018年8月7日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.dao.BiDiStandardMapper;
import com.manhui.gsl.jbqgsl.dao.EvaluateDetailMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateDeatil;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;


@Service
public class BiDistandardServiceImpl implements IBiDiStandardService {
	@Resource
	private BiDiStandardMapper biDiStandardMapper;
	
	@Resource
	private EvaluateDetailMapper evaluateDetailMapper;
	
    @Override
    public List<EvaluateStandard> queryEvaluateStandards( EvaluateStandard evaluStandard ) {
        return biDiStandardMapper.queryEvaluate(evaluStandard);
    }

    @Override
    public Integer queryevaluateStandardsCount( EvaluateStandard evaluStandard ) {
        return biDiStandardMapper.queryEvaluateCount(evaluStandard);
    }

	@Override
	public void save(EvaluateStandard evaluateStandard) {
		//检测保存的对象是否是即时评价
		String blonged = evaluateStandard.getStandard_belonged();
		if("2".equals(blonged)){
			//是即时评价
			EvaluateStandard es = biDiStandardMapper.queryEvaluateStandardByBelonged();
			if(es != null){
				es.setStandard_belonged("1");
				es.setUpdate_time(DateUtil.getTime());
				biDiStandardMapper.updateEvaluateStandards(es);
			}
		}
		biDiStandardMapper.saveEvaluateStandards(evaluateStandard);
	}

	@Override
	public List<Map<String, Object>> queryEvaluateStandardsByCondition(EvaluateStandard evaluateStandard) {
		return biDiStandardMapper.queryEvaluateByCondition(evaluateStandard);
	}

	@Override
	public JsonResult deleteEvaluateStandards(String standard_id) {
		// TODO Auto-generated method stub
		biDiStandardMapper.deleteEvaluateStandards(standard_id);
		return new JsonResult();
	}

	/**
	 * @方法名称 showEvaluateStandard
	 * @功能描述 显示并组装评价标准及详情数据
	 * @作者    kevin
	 * @创建时间 2018年8月16日 下午5:48:15
	 * @param standard_id
	 * @return
	 */
	@Override
	public EvaluateStandard showEvaluateStandard(String standard_id) {
		EvaluateStandard data = biDiStandardMapper.showEvaluateStandard(standard_id);
		List<EvaluateDeatil> paramData = evaluateDetailMapper.showEvaluateDeatil(standard_id, null, 1);
		for(EvaluateDeatil param : paramData){
			List<EvaluateDeatil> ed = evaluateDetailMapper.showEvaluateDeatil(null, param.getDetail_id(), 2);
			param.setEvaluateDetailChildList(ed);
		}
		data.setEvaluateDetailList(paramData);
		data.setWork_content("");
		data.setSuggest_initiate("");
		
		return data;
	}

	@Override
	public EvaluateStandard queryEvaluateStandard(String standard_id) {
		// TODO Auto-generated method stub
		return biDiStandardMapper.showEvaluateStandard(standard_id);
	}

	@Override
	public void update(EvaluateStandard evaluateStandard) {
		//检测保存的对象是否是即时评价
		String blonged = evaluateStandard.getStandard_belonged();
		if("2".equals(blonged)){
			//是即时评价
			EvaluateStandard es = biDiStandardMapper.queryEvaluateStandardByBelonged();
			if(es != null){
				es.setStandard_belonged("1");
				es.setUpdate_time(DateUtil.getTime());
				biDiStandardMapper.updateEvaluateStandards(es);
			}
		}
		biDiStandardMapper.updateEvaluateStandards(evaluateStandard);
	}

	@Override
	public void vaildStandard(String standard_id) {
		// TODO Auto-generated method stub
		biDiStandardMapper.vaildStandard(standard_id);
	}

	@Override
	public void invaildStandard(String standard_id) {
		// TODO Auto-generated method stub
		biDiStandardMapper.invaildStandard(standard_id);
	}

	@Override
	public EvaluateStandard queryEvaluateStandardByBelonged() {
		// TODO Auto-generated method stub
		return biDiStandardMapper.queryEvaluateStandardByBelonged();
	}

}
