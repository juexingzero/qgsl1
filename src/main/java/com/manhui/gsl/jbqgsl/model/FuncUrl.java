package com.manhui.gsl.jbqgsl.model;

import lombok.Data;
import java.util.Date;

/**
 * 功能url
 **/
@Data
public class FuncUrl {
    private String  url_id;      //URL_ID
    private String  func_id;     //功能ID
    private String  func_name;   //功能名称
    private String  func_url;    //功能URL
    private String  record_log;  //是否记录日志(Y：是，N：否)
    private String  order_no;    //排序号
    private String  creator_id;  //创建人ID
    private Date    create_time; //创建时间
    private String  update_bools;//是否修改(修改：true，添加：false)
    private Integer pageNo;      //开始页数
    private Integer pageSize;    //查询总数
}
