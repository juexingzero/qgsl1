

package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
* @Title: UserSuggest.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(用户反馈实体类)
* @author LiuBin
* @date 2018年8月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class UserSuggest {
	//表标志
	private String id;
	//用户id
	private String user_id;
	//用户名称
	private String user_name;
	//用户联系电话
	private String user_phone;
	//反馈内容
	private String content;
	//创建时间
	private String create_time;
	//是否回复
	private String is_answer;
	//回复内容
	private String answer_content;
	//回复人id
	private String answer_man_id;
	//回复人姓名
	private String answer_man_name;
	//回复时间
	private String answer_time;
}
