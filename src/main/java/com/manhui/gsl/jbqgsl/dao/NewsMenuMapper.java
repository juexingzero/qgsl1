package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.NewsMenu;

/**
 * 新闻栏目管理
 **/
@Mapper
public interface NewsMenuMapper {
    /**
     * 新增数据
     * 
     * @param menu
     */
    Integer save( NewsMenu menu );

    /**
     * 查询list
     * 
     * @param menu
     * @return
     */
    List<NewsMenu> queryList( NewsMenu menu );

    /**
     * 根据id 查询数据
     * 
     * @param menuId
     * @return
     */
    NewsMenu queryById( Integer menuId );

    /**
     * 根据上级id 查询数据
     * 
     * @param menu
     * @return
     */
    List<NewsMenu> queryListBySuperiorId( NewsMenu menu );

    /**
     * 根据id 修改
     * 
     * @param menu
     * @return
     */
    Integer updateById( NewsMenu menu );
}
