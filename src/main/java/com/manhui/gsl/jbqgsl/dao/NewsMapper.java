package com.manhui.gsl.jbqgsl.dao;

import com.manhui.gsl.jbqgsl.model.CommonData;
import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.News;
import java.util.List;
import java.util.Map;

/**
 * 新闻发布管理
 **/
@Mapper
public interface NewsMapper {
    /**
     * 新增数据
     * 
     * @param news
     */
    Integer save( News news );

    /**
     * 根据id修改
     * 
     * @param news
     * @return
     */
    Integer updateById( News news );

    /**
     * 查询列表
     * 
     * @param news
     * @return
     */
    List<News> queryList( News news );

    /**
     * 根据id 查询数据
     * 
     * @param news
     * @return
     */
    News queryById( News news );

    /**
     * * app 搜索 新闻或栏目
     * @param paramMap
     * @return
     */
    List<CommonData> getBlurryQueryNewsOrMenu(Map<String,Object> paramMap);
}
