


package com.manhui.gsl.jbqgsl.model.topicevaluate;

import lombok.Data;

/**
* @Title: TelephoneRecord.java
* @Package com.manhui.gsl.jbqgsl.model.topicevaluate
* @Description: TODO(圈子--电话记录实体类)
* @author LiuBin
* @date 2018年9月27日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class TelephoneRecord {
	private String id;			//id
	
	private String caller_id;		//呼叫方id
	
	private String caller_name;		//呼叫方姓名
	
	private String caller_post;			//呼叫方职务
	
	private String caller_dept_name;			//呼叫方部门(呼叫方公司)
	
	private String caller_dept_id;		//呼叫方部门id(机构ID)
	
	private String caller_phone;		//呼叫方电话
	
	private String called_id;	//被呼叫方id
	
	private String called_name;	//被呼叫方姓名
	
	private String called_post;	//被呼叫方职务
	
	private String called_dept_name;	//被呼叫方部门
	
	private String called_dept_id;	//被呼叫方部门id
	
	private String called_phone;	//被呼叫方电话
	
	private Integer num;	//联系次数
	
	private String link_time;	//开始时间
	
	
}
