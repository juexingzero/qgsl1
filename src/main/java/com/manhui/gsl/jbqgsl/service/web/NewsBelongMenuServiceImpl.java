package com.manhui.gsl.jbqgsl.service.web;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.NewsBelongMenuMapper;
import com.manhui.gsl.jbqgsl.model.NewsBelongMenu;

/**
 * 新闻所属栏目管理
 */
@Service
public class NewsBelongMenuServiceImpl implements NewsBelongMenuService {
    @Resource
    private NewsBelongMenuMapper newsBelongMenuMapper;

    @Override
    public Integer save( NewsBelongMenu belongMenu ) {
        return newsBelongMenuMapper.save( belongMenu );
    }

    /**
     * 根据新闻id 删除数据
     * 
     * @param newsId
     * @return
     */
    @Override
    public Integer delBelongByNewsId( Integer newsId ) {
        return newsBelongMenuMapper.delBelongByNewsId( newsId );
    }
}
