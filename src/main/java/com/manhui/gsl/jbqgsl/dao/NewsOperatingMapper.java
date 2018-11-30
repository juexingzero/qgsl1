package com.manhui.gsl.jbqgsl.dao;

import com.manhui.gsl.jbqgsl.model.CommonData;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新闻评论收藏，点赞管理
 **/
@Mapper
public interface NewsOperatingMapper {
    /**
     * 新增数据
     *
     * @param newsOperating
     */
    Integer save(NewsOperating newsOperating);

    /**
     * 根据id修改
     *
     * @param newsOperating
     * @return
     */
    Integer updateById(NewsOperating newsOperating);

    /**
     * 查询用户 收藏新闻
     * @param
     * @return
     */
    List<NewsOperating> getUserCollectionNews(NewsOperating operating);

    /**
     * 询人员 对 新闻的操作数据
     * @param operating
     * @return
     */
    List<NewsOperating> getUserNewsOperatingList(NewsOperating operating);

    /**
     * 查询新闻评论数据列表
     * @param operating
     * @return
     */
    List<NewsOperating> queryCommentList(NewsOperating operating);

    /**
     * 根据id查询数据
     * @param operatingId
     * @return
     */
    NewsOperating getNewsOperatingById(Integer operatingId);

    /**
     * 查询回执数据
     * @param operating
     * @return
     */
    List<NewsOperating> queryReceiptDataList(NewsOperating operating);

    List<NewsOperating> queryCommentInfo(@Param("operatingIds") List<Integer> operatingIds);
}
