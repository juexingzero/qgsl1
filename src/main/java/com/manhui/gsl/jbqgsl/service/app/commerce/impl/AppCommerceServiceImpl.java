

package com.manhui.gsl.jbqgsl.service.app.commerce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
import com.manhui.gsl.jbqgsl.dao.app.commerce.AppCommerceMapper;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;


/**
* @Title: AppCommerceServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.app.commerce.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppCommerceServiceImpl implements IAppCommerceService {
	@Resource
	private AppCommerceMapper appCommerceMapper;

	@Override
	public List<Commerce> queryCommerceList() {
		//先查一级商会
		List<Commerce> commerce = appCommerceMapper.queryCommerceList();
		//二级商会
		if(commerce !=null && commerce.size()>0) {
			for (Commerce commerce2 : commerce) {
				List<Commerce> commerceList =appCommerceMapper.querySecondCommerceList(commerce2.getID());
				commerce2.setZChildCommerce(commerceList);
			}
		}
		
		return commerce;
	}
	
}
