package com.manhui.gsl.jbqgsl.common.util;

import java.io.Serializable;

/**封装具体的分页信息*/
public class PageObject implements Serializable{
	private static final long serialVersionUID = -8753809986545361268L;
	/**当前页*/
	private int pageCurrent=1;
	/**每页最多能显示的记录数*/
	private int pageSize=10;
	/**总记录数*/
	private int rowCount;
	/**总页数*/
	private int pageCount;
	/**上一页的最后一条记录位置
	 * 对应:limit startIndex,pageSize;
	 */
	private int startIndex;
	public int getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageCount() {
		int pages=rowCount/pageSize;
		if(rowCount%pageSize!=0){
			pages+=1;
		}
		return pages;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartIndex() {
		return (pageCurrent-1)*pageSize;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}