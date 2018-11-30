package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.dao.IndustryMapper;
import com.manhui.gsl.jbqgsl.model.Industry;

/**
 * @类名称 IndustryServiceImpl.java
 * @类描述 <pre>行业信息模块service层接口实现，主要处理业务逻辑</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年8月8日14:46:51
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	 2018年8月8日                创建
 *     ----------------------------------------------
 * </pre>
 */
@Service
public class IndustryServiceImpl implements IIndustryService {
	private static final Logger logger = LoggerFactory.getLogger( InstitutionServiceImpl.class );

	 @Resource
	 private IndustryMapper industryMapper;
	
	@Override
	public List<Industry> queryAllIndustry() {
		// TODO Auto-generated method stub
		return industryMapper.queryAllIndustry();
	}

}
