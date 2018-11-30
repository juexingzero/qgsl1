package com.manhui.gsl.jbqgsl.model;

import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

/**
 * @类名称 CompanyFeedback.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月4日 上午9:16:22
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table company_feedback
 */
@Data
public class CompanyFeedback extends PageModel {
    //fields
    private String feedback_id;           //反馈ID
    private String feedback_number;       //反馈编号(格式：ZSyyyyMMdd00x)
    private String feedback_type;         //问题类型
    private String company_name;          //企业名称
    private String company_linkman_name;  //企业联系人姓名
    private String company_linkman_phone; //企业联系人电话
    private String feedback_man_id;       //反馈人ID
    private String feedback_man_name;     //反馈人姓名
    private String feedback_time;         //反馈时间
    private String feedback_content;      //反馈内容
    private String is_answer;             //是否已回复(0：未查看，1：已查看，2：已回复，默认0)
    private String answer_man_id;         //回复人ID
    private String answer_man_name;       //回复人姓名
    private String answer_time;           //回复时间
    private String answer_content;        //回复内容
}
