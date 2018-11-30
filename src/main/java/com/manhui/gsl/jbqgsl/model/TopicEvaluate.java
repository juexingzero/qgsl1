package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @类名称 TopicEvaluate.java
 * @类描述 主题评价
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午12:16:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table topic_evaluate
 */
@Data
public class TopicEvaluate {
    //fields
    private String  topic_id;                  //机构ID
    private String  topic_number;              //主题编号(格式：PJyyyyMMdd00x)
    private String  topic_name;                //主题名称
    private String  topic_type;                //主题类型(1：半年评价，2：年度评价，默认1)
    private String  topic_describe;            //主题描述
    private String  evaluate_start_time;       //评价开始时间
    private String  evaluate_end_time;         //评价结束时间
    private String  evaluate_state;            //评价状态(0：待发布，1：未开始，2：评价中，3：已结束，4：已撤销，默认1)
    private String  del_flag;                  //删除标识(0：未删除，1：已删除，默认0)
    private String  create_time;               //创建时间
    private String  update_time;               //修改时间
    //keywords
    private Integer pageNo;                    //开始页数
    private Integer pageSize;                  //查询总数
    private String  institution_name_passives; //被评价方的机构名称集
    private String  institution_name_actives;  //评价方的机构名称集
    private int     passiveInstitutionNum;     //被评价机构数
    private int     alreadyEvaluateNum;        //已评价机构数
}
