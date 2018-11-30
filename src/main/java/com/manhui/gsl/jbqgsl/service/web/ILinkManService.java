package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;

import com.manhui.gsl.jbqgsl.controller.app.linkman.LinkManResult;
import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;

public interface ILinkManService {
	/**
	 * @方法名称 getDeptAndPositionList
	 * @功能描述 获取部门及岗位列表，用于APP接口
	 * @作者 kevin
	 * @创建时间 2018年8月22日 下午2:41:48
	 * @return
	 */
	Map<String, Object> getDeptAndPositionList();

	/**
	 * @方法名称 getLinkManByPositionList
	 * @功能描述 获取岗位下对应的联系人列表，用于APP接口
	 * @作者 kevin
	 * @创建时间 2018年8月22日 下午4:32:05
	 * @param position_id
	 * @return
	 */
	Map<String, Object> getLinkManByPositionList(String position_id);

	/**
	 * 获取部门下面的岗位以及对应的的联系人列表，用于APP接口
	 */
	Map<String, Object> getPositionAndUser(String dept_id);

	/**
	 * 获取部门
	 */
	Map<String, Object> getDeptList();

	/**
	 * 根据人名 模糊查询
	 */
	List<LinkManResult> queryLinkManList(TelephoneRecord tr);
}
