package com.manhui.gsl.jbqgsl.dao;

import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.model.NewsReceiptContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 新闻回执内容 管理
 **/
@Mapper
public interface NewsReceiptContentMapper {

    /**
     * 新增数据
     * @param receiptContent
     */
    void save(NewsReceiptContent receiptContent);

    /**
     * 查询回执内容
     * @param receiptContent
     * @return
     */
    List<NewsReceiptContent> queryReceiptList(NewsReceiptContent receiptContent);

    /**
     * 根据id 查询
     * @param receiptId
     * @return
     */
    NewsReceiptContent queryById(Integer receiptId);

    int updateById(NewsReceiptContent receiptContent);
}
