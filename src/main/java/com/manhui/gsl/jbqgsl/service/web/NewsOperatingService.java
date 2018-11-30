package com.manhui.gsl.jbqgsl.service.web;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.model.CommonData;
import com.manhui.gsl.jbqgsl.model.NewsOperating;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 新闻操作管理*（点赞，评论，收藏）
 */
public interface NewsOperatingService {

    /**
     * \ 保存新闻信息
     *
     * @return
     */
    ResultMessage saveNewsOperating(NewsOperating operating) throws Exception;

    /**
     * 查询用户 收藏新闻
     * @return
     */
    ResultMessage getUserCollectionNews(NewsOperating operating) throws ParseException;

    /**
     * 取消人员收藏
     * @param operating
     * @return
     */
    ResultMessage cancelUserCollection(NewsOperating operating);

    /**
     * 查询 新闻评论列表
     * @param operating
     * @return
     */
    Map<String,Object> queryCommentList(NewsOperating operating) throws ParseException;

    /**
     * 查询 人员对新闻 是否 点赞，收藏，
     * @param operating
     * @return
     */
    Map<String,Object> getUserNewsOperatingList(NewsOperating operating);

    /**
     * 查询 新闻互动 内容
     * @param operating
     * @return
     */
    PageInfo<NewsOperating> queryInteractiveDataList(NewsOperating operating);

    /**
     * 删除数据
     * @param operating
     * @return
     */
    ResultMessage del(NewsOperating operating,String userid) throws Exception;

    /**
     * 根据id 修改数据
     * @param operating
     * @return
     */
    ResultMessage updateById(NewsOperating operating);

    /**
     * 查询回执数据
     * @param operating
     * @return
     */
    PageInfo<NewsOperating> queryReceiptDataList(NewsOperating operating);

    List<NewsOperating> queryCommentInfo(List<Integer> operatingIds);

    /**
     * 批量查询
     * @param ids
     * @return
     */
    List<NewsOperating> getByOperatingIds(String[] ids);
}
