
package com.manhui.gsl.jbqgsl.controller.app.linkman;

import lombok.Data;

/**
 * @Title: TelephoneRecord.java
 * @Package com.manhui.gsl.jbqgsl.controller.app.linkman
 * @Description: TODO(圈子--电话记录)
 * @author LiuBin
 * @date 2018年9月27日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class TelephoneRecordResult {
	private String id; // id

	private String caller_id; // 呼叫方id

	private String called_id; // 被呼叫方id

	private String called_name; // 被呼叫方姓名

	private String called_post; // 被呼叫方职务

	private String called_dept_name; // 被呼叫方部门

	private String called_dept_id; // 被呼叫方部门id

	private String called_phone; // 被呼叫方电话
	
	private String head_img; // 头像
	
	private String user_sex; // 性别

}
