package com.manhui.gsl.jbqgsl.model;

import java.util.List;
import lombok.Data;

/**
 * 评价流水评分表
 *
 * evaluate_flowing_score
 */
@Data
public class EvaluateFlowingScore {
    //fields
    private String             score_id;                      //评分ID
    private String             topic_id;                      //主题ID
    private String             topic_standard_id;             //主题标准ID
    private String             topic_standard_detail_id;      //主题标准详情ID
    private String             topic_standard_detail_name;    //主题标准详情名称
    private String             forthwith_id;                  //即时ID
    private String             forthwith_standard_id;         //即时标准ID
    private String             forthwith_standard_detail_id;  //即时标准详情ID
    private String             forthwith_standard_detail_name;//即时标准详情名称
    private String             standard_p_detail_id;      //标准详情上级ID
    private Integer            standard_detail_level;    //标准详情级别
    private String             passive_id;                    //被评价方ID
    private String             passive_name;                  //被评价方名称
    private String             actice_id;                     //评价方ID
    private String             active_name;                   //评价方名称
    private String             flag;                          //是否评分(0：不了解，1：已评分)
    private Double             plan_score;                    //计划分数
    private Double             real_score;                    //实得分数
    private String             create_time;                   //创建时间
    //keywords
    List<EvaluateFlowingScore> evaluateFlowingScoreList;      //显示用
    private String             p_detail_id;                   //显示用父级
}
