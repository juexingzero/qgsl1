package com.manhui.gsl.jbqgsl.service.web;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.model.News;

import java.text.ParseException;
import java.util.Map;

/**
 * 新闻管理
 */
public interface NewsService {
    /**
     * 查询数据列表
     * 
     * @param news
     * @return
     */
    PageInfo<News> queryList( News news );

    /**
     * \ 保存新闻信息
     * 
     * @param news
     * @param userid
     * @return
     */
    ResultMessage saveNews( News news, String userid ) throws Exception;

    /**
     * 删除新闻
     * 
     * @param news
     * @param userid
     * @return
     */
    ResultMessage delNews( News news, String userid );

    /**
     * 根据id 查询数据
     * 
     * @param newsId
     * @return
     * @throws Exception
     */
    News queryNewById( Integer newsId ) throws Exception;

    /**
     * 修改新闻
     * 
     * @param news
     * @param userid
     * @return
     */
    ResultMessage updateNews( News news, String userid ) throws Exception;

    /**
     * 手机端查询数据集合
     * @param news
     * @return
     */
    PageInfo<News> appQueryList(News news) throws ParseException;

    /**
     * app 搜索 新闻或栏目
     * @param paramMap
     * @return
     */
    ResultMessage getBlurryQueryNewsOrMenu(Map<String,Object> paramMap);
}
