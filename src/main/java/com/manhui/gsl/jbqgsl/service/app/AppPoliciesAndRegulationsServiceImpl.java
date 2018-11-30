

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppPoliciesAndRegulationsMapper;
import com.manhui.gsl.jbqgsl.model.Dept;

/**
* @Title: AppPoliciesAndRegulationsServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(政策法规service接口)
* @author LiuBin
* @date 2018年9月6日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppPoliciesAndRegulationsServiceImpl implements IAppPoliciesAndRegulationsService {
	@Resource
	private AppPoliciesAndRegulationsMapper mapper;

	@Override
	public List<Dept> queryOutDeptList() {
		
		return mapper.queryOutDeptList();
	}

	@Override
	public List<Dept> queryOutDepts(String dept_name) {
		return mapper.queryOutDepts(dept_name);
	}
}
