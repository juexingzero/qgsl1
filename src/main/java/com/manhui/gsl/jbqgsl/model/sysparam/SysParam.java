package com.manhui.gsl.jbqgsl.model.sysparam;

import lombok.Data;

import java.util.List;

/**
 * @类名称 SysParam.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:16:23
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
 * @table sys_param
 */
@Data
public class SysParam {
    //fields
    private String param_id;         //属性ID
    private String p_param_id;         //父级属性
    private String param_key;       //参数标识
    private String param_value;     //参数名称
    private String param_describe;    //参数描述
    private String param_order;     //参数序号
    private String create_time;     //创建时间
    private String update_time;     //修改时间

    private List<SysParam> child_param;
}
