
/**
* @Title: PaginationVO.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年8月20日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.model;

import java.util.List;

public class PaginationVO<T> {
	private Long total;
	private List<T> dataList;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
