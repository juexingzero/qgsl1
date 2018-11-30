package com.manhui.gsl.jbqgsl.service.web;

import com.manhui.gsl.jbqgsl.model.NewsBelongMenu;

/**
 * 新闻所属栏目管理
 */
public interface NewsBelongMenuService {
    /**
     * 保存数据
     * 
     * @param belongMenu
     * @return
     */
    Integer save( NewsBelongMenu belongMenu );

    /**
     * 根据新闻id删除数据
     * 
     * @param newsId
     * @return
     */
    Integer delBelongByNewsId( Integer newsId );
}
