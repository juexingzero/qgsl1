package com.manhui.gsl.jbqgsl.model;

import java.util.List;

import lombok.Data;

@Data
public class ForthwithEvaluate {
	private String forthwith_id;			//即时ID
	private String forthwith_number;		//即时编号(格式：JSyyyyMMdd00x)
	private String passive_id;				//被评价方ID
	private String evaluate_active_name;	//评价方名称
	private String active_id;				//评价方ID
	private String evaluate_passive_name;	//被评价方名称
	private String evaluate_time;			//评价时间
	private double real_score_sum;			//实得分数
	private String del_flag;				//删除标识(0：未删除，1：已删除，默认0)
	private String create_time;				//创建时间
	private String update_time;				//更新时间
	//不在表中
	private String is_answer;               	// 即时评价栏目-我的历史评价 是否回复
	private String answer_content;               	// 即时评价栏目-我的历史
	private String headImg;               	// 机构头像
	private String describe;               	// 机构描述
	private String html_describe;               	// 机构html描述
	private String standard_id;               	// 即时评价标准id
	private String institution_type;               	// 机构类型
}
