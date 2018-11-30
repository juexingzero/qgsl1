
/**
* @Title: EvaluateStandard.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(评价标准实体类)
* @author LiuBin
* @date 2018年8月7日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.model;

import java.util.List;

import lombok.Data;

@Data
public class EvaluateStandard {
	//标准id
	private String standard_id;
	//标准名称
	private String standard_name;
	//标准类型(1：评政府，2：评企业，3:综合 默认1)
	private String standard_type;
	//标准所属(1：主题评价，2：即时评价，默认1)
	private String standard_belonged;
	//标准描述
	private String standard_describe;
	//标准计划分数
	private double standard_plan_score;
	//排序编号
	private Integer order_no;
	//是否生效(0：未生效，1：已生效，默认0)
	private String is_effect;
	//删除标识(0：未删除，1：已删除，默认0)
    private String del_flag;
	//创建时间
	private String create_time;
	//修改时间
	private String update_time;
	
	//不在类中
    private Integer pageNo;         //开始页数
    private Integer pageSize;       //查询总数
    private Double real_score;		//真实分数
    private String work_content; 	//办事内容
    private String suggest_initiate; //意见建议
    private String suggest_is_answer; //评价是否已经处理
    private String evaluate_time; //评价时间
    private List<EvaluateDeatil> evaluateDetailList;
}
