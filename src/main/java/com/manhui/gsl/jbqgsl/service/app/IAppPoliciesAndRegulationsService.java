

package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.Dept;

/**
* @Title: IAppPoliciesAndRegulationsService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO("政策法规service接口")
* @author LiuBin
* @date 2018年9月6日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppPoliciesAndRegulationsService {

	List<Dept> queryOutDeptList();

	List<Dept> queryOutDepts(String dept_name);

}
