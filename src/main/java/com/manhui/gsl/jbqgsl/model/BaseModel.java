package com.manhui.gsl.jbqgsl.model;

import java.util.Date;
import lombok.Data;

@Data
public class BaseModel {
    private Integer order_no;             //排序编号
    private String  del_sign;             //删除标识(0：未删除，1：已删除，默认0)
    private Date    create_time;          //创建时间
    private Date    update_time;          //修改时间
}
