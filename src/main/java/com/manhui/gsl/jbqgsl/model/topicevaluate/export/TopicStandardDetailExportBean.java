package com.manhui.gsl.jbqgsl.model.topicevaluate.export;

import java.util.List;
import lombok.Data;

/**
 * @类名称 TopicStandardDetailExportBean.java
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
public class TopicStandardDetailExportBean {
    private List<TopicStandardDetailScoreBean> tsdsbList;
}
