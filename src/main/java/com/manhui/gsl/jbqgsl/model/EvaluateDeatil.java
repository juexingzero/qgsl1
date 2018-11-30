
/**
* @Title: EvaluateDeatil.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(细则实体表)
* @author LiuBin
* @date 2018年8月8日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.model;

import java.util.List;

import lombok.Data;

@Data
public class EvaluateDeatil {
	//详情ID
	private String detail_id;
	//标准ID
	private String standard_id;
	//详情名称
	private String detail_name;
	//详情计划分数
	private double detail_plan_score;
	//详情级别
	private int detail_level;
	//上级详情ID
	private String p_detail_id;
	//排序编号
	private int order_no;
	//创建时间
	private String create_time;
	//修改时间
	private String update_time;
	//2级细则
	private double                          detail_real_score;      //详情实得分数
	private List<EvaluateDeatil> evaluateDetailChildList;
	 //keywords
//    private String 							suggest_initiate;	//意见发起
//    private String 							work_content;	//办事内容
}
