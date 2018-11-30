

package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;

/**
* @Title: AppTelephoneRecordMapper.java
* @Package com.manhui.gsl.jbqgsl.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年9月27日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface AppTelephoneRecordMapper {
	/**
	 * 保存联系对象
	 */
	int save(TelephoneRecord tr);
	/**
	 * 根据呼叫方 被呼叫方查询--判断是第一次还是n次
	 */
	TelephoneRecord query(TelephoneRecord tr);
	/**
	 * 修改联系次数以及时间
	 */
	void update(TelephoneRecord telephoneRecord);
	/**
	 * 最近联系人列表
	 */
	List<TelephoneRecordResult> queryLastTimeLinkManList(TelephoneRecord tr);

}
