package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.Industry;

/**
 * @类名称 IndustryMapper.java
 * @类描述 <pre>行业数据模块dao层，主要负责跟数据库的交互</pre>
 * @作者  Jiangxiaosong 
 * @创建时间 2018年8月8日 下午3:33:52
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
@Mapper
public interface IndustryMapper {
	List<Industry> queryAllIndustry();
}
