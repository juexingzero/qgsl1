package com.manhui.gsl.jbqgsl.controller.web.news;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsOperatingType;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.service.web.NewsOperatingService;
import com.manhui.gsl.jbqgsl.service.web.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 后台 新闻互动数据管理，评论，收藏，点赞，
 */
@Controller
@RequestMapping(WebNewsOperatingController.ROOT_URL)
public class WebNewsOperatingController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/newsOperating";
    @Resource
    private NewsService newsService;
    @Resource
    private NewsOperatingService newsOperatingService;
    /**
     * 新闻互动内容 页面显示
     * @param newsId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/interactiveDataPage" )
    public String interactiveDataPage( Integer newsId, Model model ) throws Exception {
        News news = newsService.queryNewById( newsId );
        model.addAttribute( "news", news );
        return "/web/html/news/newsInteractiveDataPage";
    }

    /**
     * 查询新闻 互动内容 列表
     */
    @RequestMapping( "/queryInteractiveData" )
    @ResponseBody
    public Map<String, Object> queryInteractiveData(NewsOperating operating ) {
        operating.setType(NewsOperatingType.commentNums.getId());
        PageInfo<NewsOperating> pageInfo = newsOperatingService.queryInteractiveDataList(operating);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );
        return dataMap;
    }

    /**
     * 删除 新闻互动数据
     * @param operatingId
     * @return
     */
    @RequestMapping("/delNewsOperating")
    @ResponseBody
    public String delNewsOperating(Integer operatingId, HttpServletRequest request) throws Exception {
        String userid = AppUtil.getCookie( request, "user_id" );
        NewsOperating operating = new NewsOperating();
        operating.setOperatingId(operatingId);
        ResultMessage message = newsOperatingService.del(operating,userid);
        return new Gson().toJson(message);
    }

    /**
     * 查询回执数据页面显示
     * @param newsId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/receiptDataPage" )
    public String receiptDataPage( Integer newsId, Model model ) throws Exception {
        News news = newsService.queryNewById( newsId );
        model.addAttribute( "news", news );

        return "/web/html/news/newsReceiptDataPage";
    }

    /**
     * 回执数据显示
     * @param operating
     * @return
     */
    @RequestMapping( "/receiptData" )
    @ResponseBody
    public Map<String, Object> receiptData(NewsOperating operating ) {
        operating.setType(NewsOperatingType.receiptNums.getId());
        PageInfo<NewsOperating> pageInfo = newsOperatingService.queryReceiptDataList(operating);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );
        return dataMap;
    }
}
