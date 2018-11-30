package com.manhui.gsl.jbqgsl.model;

import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

/**
 * @类名称 CompanyElegant.java
 * @类描述 企业风采
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:09:27
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table company_elegant
 */
@Data
public class CompanyElegant extends PageModel {
    //fields
    private String  elegant_id;       //风采ID
    private String  company_name;     //企业名称
    private String  company_type;     //企业分类(1：普通会员，2：优秀会员，默认1)
    private String  company_logo;     //企业logo
    private String  company_image;    //企业图片
    private String  company_describe; //企业描述
    private Integer order_no;         //排序编号
    private String  release_time;     //发布时间
    //keywords
    private Object  imgObj;           //用于图片回显
}
