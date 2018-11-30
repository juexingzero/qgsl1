package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * 字典
 **/
@Data
public class Dictionary {
    // 编码
    private Integer dict_id;
    // 字典类别
    private String  dict_type;
    // 字典名字
    private String  dict_name;
    // 字典值
    private String  dict_value;
    // 父级编码
    private Integer dict_pid;
    // 父级字典名称
    private String  dict_pidName;
    // 是否系统参数 0默认为不为系统参数，1为系统参数
    private Integer isSystem;
    // 查询条件
    private String  where;
    // 开始条数
    private Integer pageNo;
    // 查询条数
    private Integer pageSize;
}
