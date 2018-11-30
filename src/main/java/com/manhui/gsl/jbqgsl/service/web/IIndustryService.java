package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.model.Industry;

/**
 * @类名称 IIndustryService.java
 * @类描述 <pre>参评机构模块service层接口</pre>
 * @作者  kevin kwmo1005@163.com
 * @创建时间 2018年8月3日 下午4:26:03
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月3日                创建
 *     ----------------------------------------------
 * </pre>
 */
public interface IIndustryService {
	List<Industry> queryAllIndustry();
}
