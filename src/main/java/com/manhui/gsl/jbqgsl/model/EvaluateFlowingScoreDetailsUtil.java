package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

import java.util.List;

@Data
public class EvaluateFlowingScoreDetailsUtil {
    private String superior_index;
    private String superior_name;
    private double superior_fraction;
    private double superior_actual_score;
    private String content;
    private List<EvaluateFlowingScoreSubordinateDetailsUtil> detailsList;

}
