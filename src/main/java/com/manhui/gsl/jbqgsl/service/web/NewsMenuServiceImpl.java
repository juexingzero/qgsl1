package com.manhui.gsl.jbqgsl.service.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.MenuState;
import com.manhui.gsl.jbqgsl.dao.NewsMenuMapper;
import com.manhui.gsl.jbqgsl.model.NewsMenu;

/**
 * 新闻栏目管理
 */
@Service
public class NewsMenuServiceImpl implements NewsMenuService {
    @Resource
    private NewsMenuMapper newsMenuMapper;

    /**
     * 保存根目录
     * 
     * @param menu
     * @return
     */
    @Override
    public ResultMessage saveMenuRoot( NewsMenu menu, String userid ) {
        menu.setType( 0 );
        menu.setState( MenuState.UNUSED.getId() );
        menu.setCreateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
        menu.setCreateUserId( userid );
        Integer bool = newsMenuMapper.save( menu );
        return new ResultMessage();
    }

    /**
     * 查询list
     * 
     * @param menu
     * @return
     */
    @Override
    public List<NewsMenu> queryList( NewsMenu menu ) {
        List<Integer> stateList = new ArrayList<>();
        if ( menu.getState() == null ) {
            stateList.add( MenuState.UNUSED.getId() );
            stateList.add( MenuState.USING.getId() );
        }
        else {
            stateList.add( menu.getState() );
        }
        menu.setStateList( stateList );
        return newsMenuMapper.queryList( menu );
    }

    /**
     * 根据id 查询数据
     * 
     * @param menuId
     * @return
     */
    @Override
    public NewsMenu queryById( Integer menuId ) {
        NewsMenu menu = newsMenuMapper.queryById( menuId );
        if ( menu == null ) {
            throw new RuntimeException( "数据不存在或已被删除" );
        }
        //处理图片
        if ( StringUtils.isNotBlank( menu.getFileImg() ) ) {
            String imgs[] = menu.getFileImg().split( "," );
            List<Map<String, String>> listMap = new ArrayList<>();
            for ( int i = 0; i < imgs.length; i++ ) {
                Map<String, String> imgMap = new HashMap<>();
                imgMap.put( "name", "" );
                imgMap.put( "path", imgs[i] );
                listMap.add( imgMap );
            }
            menu.setImgObj( new Gson().toJson( listMap ) );
        }
        else {
            menu.setImgObj( "" );
        }
        return menu;
    }

    /**
     * 保存子节点
     * 
     * @param menu
     * @param userid
     * @return
     */
    @Override
    public ResultMessage saveChildMenu( NewsMenu menu, String userid ) {
        menu.setType( 1 );
        menu.setState( MenuState.UNUSED.getId() );
        menu.setCreateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
        menu.setCreateUserId( userid );
        Integer bool = newsMenuMapper.save( menu );
        return new ResultMessage();
    }

    /**
     * 删除节点
     * 
     * @param menuId 上级id
     * @param userid
     * @return
     */
    @Override
    public ResultMessage delMenu( Integer menuId, String userid ) throws Exception {
        try {
            NewsMenu menu = new NewsMenu();
            menu.setSuperiorId( menuId );
            List<Integer> states = new ArrayList<>();
            states.add( MenuState.USING.getId() );
            states.add( MenuState.UNUSED.getId() );
            menu.setStateList( states );
            List<NewsMenu> menus = newsMenuMapper.queryListBySuperiorId( menu );
            if ( menus != null && !menus.isEmpty() ) {
                //有子节点不能删除
                return new ResultMessage( "拥有下级栏目的栏目不能删除" );
            }
            menu = new NewsMenu();
            menu.setMenuId( menuId );
            menu.setState( MenuState.DEL.getId() );
            menu.setUpdateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
            menu.setUpdateUserid( userid );
            Integer bool = newsMenuMapper.updateById( menu );
        }
        catch ( Exception e ) {
            throw new RuntimeException( "数据异常或已被删除" );
        }
        return new ResultMessage();
    }

    /**
     * 修改节点
     * 
     * @param menu
     * @param userid
     * @return
     */
    @Override
    public ResultMessage updateMenu( NewsMenu menu, String userid ) {
        menu.setUpdateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
        menu.setUpdateUserid( userid );
        Integer bool = newsMenuMapper.updateById( menu );
        if ( bool.equals( 0 ) ) {
            return new ResultMessage( "修改失败，数据不存在或已被删除" );
        }
        return new ResultMessage();
    }
}
