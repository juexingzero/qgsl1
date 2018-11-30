package com.manhui.gsl.jbqgsl.model.topicevaluate.export;

import lombok.Data;

/**
 * @类名称 TopicActiveScoreBean.java
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
public class TopicActiveScoreBean {
    private String active_order; //评价方排名
    private String active_name;  //评价方名称
    private String active_score; //评价方打分
}
