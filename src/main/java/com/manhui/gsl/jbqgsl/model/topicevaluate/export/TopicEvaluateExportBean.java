package com.manhui.gsl.jbqgsl.model.topicevaluate.export;

import java.util.List;
import lombok.Data;

/**
 * @类名称 TopicEvaluateExportBean.java
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
public class TopicEvaluateExportBean {
    //主题基本信息
    private String                               topic_id;            //机构ID
    private String                               topic_name;          //主题名称
    private String                               topic_number;        //主题编号
    private String                               evaluate_start_time; //评价开始时间
    private String                               evaluate_end_time;   //评价结束时间
    private String                               actives;             //评价方数量
    //被评价方得分列表
    private List<TopicPassiveScoreBean>          passiveScoreList;
    //被评价方得分和评价方打分列表
    private List<TopicPassiveAndActiveScoreBean> passiveAndActiveScoreList;
}
