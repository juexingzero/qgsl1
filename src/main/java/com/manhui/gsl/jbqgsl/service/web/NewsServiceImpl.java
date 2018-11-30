package com.manhui.gsl.jbqgsl.service.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.MenuState;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsState;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.dao.NewsMapper;
import com.manhui.gsl.jbqgsl.dao.NewsMenuMapper;
import com.manhui.gsl.jbqgsl.model.CommonData;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.NewsBelongMenu;
import com.manhui.gsl.jbqgsl.model.NewsMenu;

/**
 * 新闻发布管理
 */
@Service
public class NewsServiceImpl implements NewsService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private NewsMapper            newsMapper;
    @Resource
    private NewsBelongMenuService newsBelongMenuService;
    @Resource
    private NewsMenuMapper        newsMenuMapper;

    /**
     * 查询数据列表
     * 
     * @param news
     * @return
     */
    @Override
    public PageInfo<News> queryList( News news ) {
        List<Integer> stateList = new ArrayList<>();
        if ( news.getState() == null ) {
            //查询默认状态数据
            stateList.add( NewsState.ADD.getId() );
            stateList.add( NewsState.RELEASE.getId() );
            stateList.add(NewsState.OBTAINED.getId());
        }else {
            stateList.add( news.getState() );
        }
        news.setStateList( stateList );
        PageHelper.startPage( news.getPageNo(), news.getPageSize() );//当前第几页，每页显示多少条
        List<News> newsList = newsMapper.queryList( news );

        PageInfo<News> info;
        if(newsList != null && !newsList.isEmpty()){
            for(News e : newsList){
                e.setStateStr(NewsState.codeMap.get(e.getState()));
            }
            info = new PageInfo<News>( newsList );
        }else{
            info = new PageInfo<News>();
        }

        return info;
    }


    /**
     * 保存新闻信息
     * 
     * @param news
     * @param userid
     * @return
     */
    @Override
    public ResultMessage saveNews( News news, String userid ) throws Exception {
        news.setCreateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
        news.setReleaseUserId( userid );
        Integer bool = newsMapper.save( news );
        if ( bool == 0 ) {
            return new ResultMessage( "系统异常" );
        }
        //保存新闻栏目信息表数据
        String menuIds[] = news.getMenuIds().split( "," );
        saveBelongData( menuIds, news.getNewsId() );
        /*if ( news.getState().equals( NewsState.RELEASE.getId() ) ) {
            //送审
        }*/
        return new ResultMessage();
    }

    /**
     * 删除新闻
     * 
     * @param news
     * @param userid
     * @return
     */
    @Override
    public ResultMessage delNews( News news, String userid ) {
        news.setUpdateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );
        news.setUpdateUserId( userid );
        news.setState( NewsState.DEL.getId() );
        Integer bool = newsMapper.updateById( news );
        if ( bool == 0 ) {
            return new ResultMessage( "系统异常请刷新重新操作" );
        }
        return new ResultMessage();
    }

    /**
     * 根据id 查询数据
     * 
     * @param newsId
     * @return
     */
    @Override
    public News queryNewById( Integer newsId ) throws Exception {
        News news = new News();
        news.setNewsId( newsId );
        news = newsMapper.queryById( news );
        if ( news == null ) {
            throw new RuntimeException( "数据不存在或已被删除" );
        }
        //处理图片显示
        if ( StringUtils.isNotBlank( news.getNewsImg() ) ) {
            String imgs[] = news.getNewsImg().split( "," );
            List<Map<String, String>> listMap = new ArrayList<>();
            for ( int i = 0; i < imgs.length; i++ ) {
                Map<String, String> imgMap = new HashMap<>();
                imgMap.put( "name", "" );
                imgMap.put( "path", imgs[i] );
                listMap.add( imgMap );
            }
            news.setImgObj( new Gson().toJson( listMap ) );
        }else {
            news.setImgObj( "" );
        }
        return news;
    }

    /**
     * 修改新闻
     * 
     * @param news
     * @param userid
     * @return
     */
    @Override
    public ResultMessage updateNews( News news, String userid ) throws Exception{
        news.setUpdateUserId( userid );
        news.setUpdateTime( new DateTime().toString( "yyyy-MM-dd HH:mm:ss" ) );

        Integer bool = newsMapper.updateById( news );
        if ( bool == 0 ) {
            return new ResultMessage( "系统异常请刷新重新操作" );
        }

        //保存新闻栏目信息
        if(StringUtils.isNotBlank(news.getMenuIds())){
            //删除新闻栏目信息表
            Integer belongBool = newsBelongMenuService.delBelongByNewsId( news.getNewsId() );
            String menuIds[] = news.getMenuIds().split( "," );
            saveBelongData( menuIds, news.getNewsId() );
        }
        /*if ( news.getState().equals( NewsState.RELEASE.getId() ) ) {
            //送审
        }*/
        return new ResultMessage();
    }

    /**
     * 手机端查询数据集合
     * @param news
     * @return
     */
    @Override
    public PageInfo<News> appQueryList(News news) throws ParseException {
        PageHelper.startPage( news.getPageNo(), news.getPageSize() );//当前第几页，每页显示多少条
        List<News> newsList = newsMapper.queryList( news );
        if(newsList != null && !newsList.isEmpty()){
            for(News e : newsList){
                e.setReleaseTime(DateUtil.getConversionTime(e.getReleaseTime(),DateUtil.sdf));
            }
        }
        PageInfo<News> info;
        if(newsList != null && !newsList.isEmpty()){
            info = new PageInfo<News>( newsList );
        }else{
            info = new PageInfo<News>();
        }

        return info;
    }

    /**
     * app 搜索 新闻或栏目
     * @param paramMap
     * @return
     */
    @Override
    public ResultMessage getBlurryQueryNewsOrMenu(Map<String, Object> paramMap) {
        String pageNo = paramMap.get("pageNo").toString();
        String pageSize = paramMap.get("pageSize").toString();
        PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));//当前第几页，每页显示多少条
        List<CommonData> dataList = newsMapper.getBlurryQueryNewsOrMenu(paramMap);
        PageInfo<CommonData> info;
        if(dataList != null && !dataList.isEmpty()){
            info = new PageInfo<CommonData>(dataList);
        }else{
            info = new PageInfo<CommonData>();
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",info.getList());
        dataMap.put("total",info.getTotal());
        return new ResultMessage(dataMap);
    }

    /**
     * 保存新闻栏目信息
     */
    private void saveBelongData( String menuIds[], Integer newsId ) {
        //
        NewsMenu menu = new NewsMenu();
        menu.setState( MenuState.USING.getId() );
        NewsBelongMenu belongMenu;
        for ( int i = 0; i < menuIds.length; i++ ) {
            belongMenu = new NewsBelongMenu();
            belongMenu.setMenuId( Integer.parseInt( menuIds[i] ) );
            belongMenu.setNewsId( newsId );
            Integer belongBool = newsBelongMenuService.save( belongMenu );
            //修改栏目为已使用状态
            menu.setMenuId( Integer.parseInt( menuIds[i] ) );
            Integer menuBool = newsMenuMapper.updateById( menu );
        }
    }
}
