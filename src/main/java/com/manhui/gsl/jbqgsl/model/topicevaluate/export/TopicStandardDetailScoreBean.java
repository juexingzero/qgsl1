package com.manhui.gsl.jbqgsl.model.topicevaluate.export;

import lombok.Data;

/**
 * @类名称 TopicStandardDetailScoreBean.java
 * @类描述 主题评价导出excel
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月27日 下午2:56:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月27日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Data
public class TopicStandardDetailScoreBean {
    private String detail_order;     //细则序号
    private String passive_name;     //被评价方名称
    private String active_name;      //评价方名称
    private String detail_content;   //评价标准内容(只记录二级)
    private String real_score;       //实际分数
    private String work_content;     //办事内容
    private String suggest_initiate; //意见发起
}
