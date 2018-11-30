package com.manhui.gsl.jbqgsl.common.util;

import lombok.Data;

/**
 * 作用于 miniui的 下拉选生成，和树
 */
@Data
public class SelectDataMiniui {
    private String id;
    private String name;
    private String pid; //上级id
}
