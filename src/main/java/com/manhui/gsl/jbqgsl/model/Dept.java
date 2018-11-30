package com.manhui.gsl.jbqgsl.model;

import java.util.Date;
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
 *     1.00     kevin    2018年8月22日                修改memo属性的数据类型为String
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table sys_dept
 */
@Data
public class Dept implements Comparable<Dept>{
    //fields
    private String  dept_id;          //部门ID
    private String  dept_code;        //部门编码
    private String  dept_name;        //部门名称
    private String  dept_type;        //部门类型(1：政府部门，2：民营企业)
    private String  p_dept_id;        //上级部门ID(顶级部门id为-1)
    private String  p_dept_name;      //上级部门名称
    private String  leader_id;        //部门领导ID
    private String  leader_name;      //部门领导名称
    private String  leader_type;      //部门领导类型(U：岗位，U：用户)
    private String  keyword;          //关键字
    private Integer order_no;         //排序号
    private String  memo;             //备注
    private String  creator_id;       //创建人ID
    private Date    create_time;      //操作人ID
    private Date    alter_time;       //修改时间
    private String  inner_outer_dept; //内部外部部门 默认(0：内部，1：外部)
    //keywords
    private Integer size;             //总数
	@Override
	public int compareTo(Dept o) {
		if(o == null) {
			return 10;
		}else {
			int i = this.getOrder_no() - o.getOrder_no();//先按照年龄排序
			return i;
		}
	}
}
