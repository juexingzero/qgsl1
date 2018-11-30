package com.manhui.gsl.jbqgsl.controller.app.linkman;

import java.util.List;
import lombok.Data;

/**
 * @类名称 Dept.java
 * @类描述 部门基础类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月22日 下午12:56:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月22日                修改memo属性的数据类型为String，增加positionList属性
 *     ----------------------------------------------
 *       </pre>
 */
@Data
public class DeptResult {
    private String               dept_id;     //部门ID
    private String               dept_name;   //部门名称
    private Integer              dept_order;  //部门序号
    private List<DeptResult>     deptList;    //当前部门下的子级部门列表
    private List<PositionResult> positionList;//当前部门下的岗位列表
}
