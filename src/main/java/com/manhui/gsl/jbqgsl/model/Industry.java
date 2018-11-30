package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

@Data
public class Industry {
    private String industry_id;         //行业ID
    private String industry_name;       //行业名称
    private String industry_describe;   //行业描述
    private String del_flag;            //删除标识
    private String create_time;         //创建时间
    private String update_time;         //修改时间
}
