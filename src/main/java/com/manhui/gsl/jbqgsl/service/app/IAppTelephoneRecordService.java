


package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;

/**
* @Title: AppTelephoneRecordService.java
* @Package com.manhui.gsl.jbqgsl.service
* @Description: TODO(AppTelephoneRecordService层)
* @author LiuBin
* @date 2018年9月27日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
public interface IAppTelephoneRecordService {
	/**
	 * 保存电话记录
	 */
	void save(TelephoneRecord tr);
	/**
	 * 最近联系人
	 */
	List<TelephoneRecordResult> queryLastTimeLinkManList(TelephoneRecord tr);


}
