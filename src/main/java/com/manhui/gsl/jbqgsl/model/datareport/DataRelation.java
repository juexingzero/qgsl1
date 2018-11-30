package com.manhui.gsl.jbqgsl.model.datareport;

import lombok.Data;

/**
 * @类名称 DataRelation.java
 * @类描述 数据上报关联表
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月21日18:18:55
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     Jiangxiaosong    2018年11月21日       创建
 *     ----------------------------------------------
 *       </pre>
 *
 * @table member_data_relation
 */
@Data
public class DataRelation {
    private String id;                  //ID
    private String template_id;        //模板ID
    private String qy_id;               //企业ID
    private String gr_id;               //个人ID(暂留)
    private String tt_id;               //团体ID(暂留)
    private String report_time;        //上报时间
    private String data_report_id;     //上报数据ID
    private String report_state;        //上报状态（0：未上报，1：已上报）
}
