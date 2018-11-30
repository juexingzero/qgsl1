package com.manhui.gsl.jbqgsl.model.commerce;

import lombok.Data;

/**
 * @类名称 CommerceCategory.java
 * @类描述 商会类别
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:24:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table commerce_category
 */
@Data
public class CommerceCategory {
    //fields
    private String  category_id;    //类别ID
    private String  category_name;  //类别名称
    private String  category_order; //类别序号
    private String  del_flag;       //删除标识(0：未删除，1：已删除，默认0)
    private String  create_time;    //创建时间
    private String  update_time;    //修改时间
    //keywords
    private Integer pageNo;         //开始页数
    private Integer pageSize;       //查询总数
}
