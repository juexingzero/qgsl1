package com.manhui.gsl.jbqgsl.dao;

import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.NewsBelongMenu;

/**
 * 新闻所属栏目管理
 **/
@Mapper
public interface NewsBelongMenuMapper {
    /**
     * 新增数据
     * 
     * @param belongMenu
     */
    Integer save( NewsBelongMenu belongMenu );

    /**
     * 根据新闻id 删除数据
     * 
     * @param newsId
     * @return
     */
    Integer delBelongByNewsId( Integer newsId );
}
