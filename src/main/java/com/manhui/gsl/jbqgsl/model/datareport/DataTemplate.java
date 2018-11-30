package com.manhui.gsl.jbqgsl.model.datareport;

import lombok.Data;

/**
 * @类名称 DataTemplate.java
 * @类描述 数据模板表
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月20日22:18:55
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     Jiangxiaosong    2018年11月20日       创建
 *     ----------------------------------------------
 *       </pre>
 *
 * @table member_data_template
 */
@Data
public class DataTemplate {
    private String template_id;         //模板ID
    private String template_title;      //模板标题
    private String template_type;       //模板类型(1：个人，2：团体，3：企业)
    private String start_time;          //开始时间
    private String end_time;            //结束时间
    private String introduce;           //模板介绍
    private String state;               //模板状态（0.未开始，1.进行中，2.已结束）
    private String create_time;         //创建时间
    private String update_time;         //更新时间
}
