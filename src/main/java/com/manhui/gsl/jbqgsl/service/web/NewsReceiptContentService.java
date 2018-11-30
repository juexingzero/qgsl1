package com.manhui.gsl.jbqgsl.service.web;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.model.NewsReceiptContent;

import java.text.ParseException;
import java.util.Map;

/**
 * 新闻回执内容管理
 */
public interface NewsReceiptContentService {

    /**
     * 保存回执内容
     * @param receiptContent
     * @return
     */
    ResultMessage saveReceipt(NewsReceiptContent receiptContent) throws Exception;

    /**
     * 查询人员 新闻回执内容
     * @param receiptContent
     * @return
     */
    ResultMessage queryUserReceipt(NewsReceiptContent receiptContent);

    /**
     * 修改 人员回执内容
     * @param receiptContent
     * @return
     */
    ResultMessage updateUserReceipt(NewsReceiptContent receiptContent);
}
