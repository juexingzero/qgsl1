package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.model.NewsMenu;

/**
 * 新闻栏目管理
 */
public interface NewsMenuService {
    /**
     * 保存 根栏目
     * 
     * @param menu
     * @return
     */
    ResultMessage saveMenuRoot( NewsMenu menu, String userid );

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
     * 保存子节点
     * 
     * @param menu
     * @param userid
     * @return
     */
    ResultMessage saveChildMenu( NewsMenu menu, String userid );

    /**
     * 删除节点
     * 
     * @param menuId 上级id
     * @param userid
     * @return
     */
    ResultMessage delMenu( Integer menuId, String userid ) throws Exception;

    /**
     * 修改栏目
     * 
     * @param menu
     * @param userid
     * @return
     */
    ResultMessage updateMenu( NewsMenu menu, String userid );
}
