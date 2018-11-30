package com.manhui.gsl.jbqgsl.model;

import java.util.List;
import lombok.Data;

/**
 * @类名称 TopicEvaluateStandard.java
 * @类描述 主题评价标准详情
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月15日 下午8:16:25
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
 * @table topic_evaluate_standard_detail
 */
@Data
public class TopicEvaluateStandardDetail {
    //fields
    private String                            topic_id;               //主题ID
    private String                            detail_id;              //详情ID
    private String                            standard_id;            //标准ID
    private String                            detail_name;            //详情名称
    private double                            detail_plan_score;      //详情计划分数
    private int                               detail_level;           //详情级别
    private String                            p_detail_id;            //上级详情ID
    private int                               order_no;               //排序编号
    private String                            create_time;            //创建时间
    //keywords
    private double                            detail_real_score;      //详情实得分数
    private List<TopicEvaluateStandardDetail> evaluateDetailChildList;
}
