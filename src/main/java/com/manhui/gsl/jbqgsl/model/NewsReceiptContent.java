package com.manhui.gsl.jbqgsl.model;

import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

/**
 * 新闻 回执内容记录表
 * tb_news_receipt_content
 */
@Data
public class NewsReceiptContent extends PageModel {
    private Integer receiptId;
    //新闻id
    private Integer newsId;
    //新闻名称
    private String newsName;
    //新闻互动表id',
    private Integer operatingId;
    //类型'1=参加，2=请假，3=指派人员参加',
    private Integer receiptType;
    //参与人数
    private Integer userNums;
    //回执内容，',
    private String content;
    //指定参加人
    private String assignPeople;

    private String userId;
    private String userName;
}
