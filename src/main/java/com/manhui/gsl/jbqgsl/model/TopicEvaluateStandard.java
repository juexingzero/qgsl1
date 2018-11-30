package com.manhui.gsl.jbqgsl.model;

import java.util.List;
import lombok.Data;

/**
 * @类名称 TopicEvaluateStandard.java
 * @类描述 主题评价标准
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月15日 下午8:12:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月15日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table topic_evaluate_standard
 */
@Data
public class TopicEvaluateStandard {
    //fields
    private String                            topic_id;           //主题ID
    private String                            standard_id;        //标准ID
    private String                            standard_name;      //标准名称
    private String                            standard_type;      //标准类型(1：评政府，2：评企业，3:综合 默认1)
    private String                            standard_belonged;  //标准所属(1：主题评价，2：即时评价，默认1)
    private String                            standard_describe;  //标准描述
    private double                            standard_plan_score;//标准计划分数
    private Integer                           order_no;           //排序编号
    private String                            create_time;        //创建时间
    //keywords
    private Integer                           pageNo;             //开始页数
    private Integer                           pageSize;           //查询总数
    private String 							suggest_initiate;	//意见发起
    private String 							work_content;	//办事内容
    private double 							real_score;	//办事内容
    private List<TopicEvaluateStandardDetail> evaluateDetailList;
}
