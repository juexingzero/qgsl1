package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * 新闻所属栏目表 表 tb_news_menu
 */
@Data
public class NewsBelongMenu {
    private Integer id;    //id
    private Integer menuId;//栏目id
    private Integer newsId;//新闻id
    private Integer state; //状态
}
