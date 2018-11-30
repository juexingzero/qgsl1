package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

@Data
public class EvaluateFlowingSuggest {
    private String suggest_id;            //意见ID
    private String topic_id;              //主题ID
    private String topic_standard_id;     //主题标准ID
    private String forthwith_id;          //即时ID
    private String forthwith_standard_id; //即时标准ID
    private String passive_id;            //被评价方id
    private String passive_name;          //被评价方名称
    private String actice_id;             //评价方名称
    private String active_name;           //评价方名称
    private String work_content;          //办事内容
    private String suggest_initiate;      //意见发起
    private String suggest_is_answer;     //意见是否已回复(0：否，1：是，默认0)
    private String suggest_answer;        //意见回复
    private String create_time;           //创建时间
}
