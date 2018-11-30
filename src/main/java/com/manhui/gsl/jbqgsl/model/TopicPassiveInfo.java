package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @类名称 TopicPassiveInfo.java
 * @类描述 主题被评价方信息
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月10日 下午16:20:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月10日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table topic_passive_info
 */
@Data
public class TopicPassiveInfo {
    //fields
    private String  topic_id;                  //主题ID
    private String  institution_id;            //机构ID
    private String  institution_name;          //机构名称
    private String  institution_type;          //机构类型(1：区级部门，2：街镇，3：企业，默认1)
    private String  institution_linkman_name;  //机构联系人姓名
    private String  institution_linkman_phone; //机构联系人电话
    private String  industry_id;               //关联行业ID
    private String  institution_main_id;       //企业所属区级主管部门ID
    private String  street_main_id;            //企业所属街镇主管部门ID
    private double  real_score_avg;            //实得平均分
    private String  create_time;               //创建时间
    private String  update_time;               //修改时间
    //keywords
    private Integer pageNo;                    //开始页数
    private Integer pageSize;                  //查询总数
}
