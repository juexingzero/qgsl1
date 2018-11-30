package com.manhui.gsl.jbqgsl.model;

import java.util.Date;
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
 *     1.00     kevin    2018年8月22日                
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table sys_position
 */
@Data
public class Position {
    //fields
    private String     position_id;  //岗位ID
    private String     position_name;//岗位名称
    private String     dept_id;      //所属部门ID
    private String     dept_name;    //所属部门名称
    private String     leader_id;    //岗位领导ID
    private String     leader_type;  //岗位领导类型(P：岗位，U：用户)
    private String     keyword;      //关键字
    private Integer    order_no;     //排序号
    private String     memo;         //备注
    private String     creator_id;   //创建人ID
    private Date       create_time;  //操作时间
    private Date       alter_time;   //修改时间
    //keywords
    private String     p_position_id;//父级岗位ID
    private String     user_id;      //用户编码
    private Integer    is_primary;   //是否主要岗位(0：否，1；是（同一员工只有一个主岗位）)
    private String     position_type;//D 部门 Y 岗位
    private Boolean    isLeaf;       //是否有选择按钮
    private String     where;        //条件
    private String     sortField;        //排序字段 
    private String     sortOrder;        //排序顺序 description asc
    private Integer    pageNo;       //开始页数
    private Integer    pageSize;     //查询总数
}
