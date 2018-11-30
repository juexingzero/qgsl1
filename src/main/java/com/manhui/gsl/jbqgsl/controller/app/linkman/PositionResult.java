package com.manhui.gsl.jbqgsl.controller.app.linkman;

import java.util.List;
import lombok.Data;

/**
 * @类名称 Position.java
 * @类描述 岗位基础类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月22日 下午12:59:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月22日                增加userList属性
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table sys_position
 */
@Data
public class PositionResult {
    private String              position_id;    //岗位ID
    private String              position_name;  //岗位名称
    private Integer             position_order; //岗位序号
    private String              dept_name; //部门名称
    private List<LinkManResult> linkManList;    //岗位下对应的联系人列表
}
