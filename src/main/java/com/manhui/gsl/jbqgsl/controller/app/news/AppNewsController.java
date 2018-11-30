package com.manhui.gsl.jbqgsl.controller.app.news;

import static java.util.Comparator.comparing;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.manhui.gsl.jbqgsl.common.enums.*;
import com.manhui.gsl.jbqgsl.common.enums.news.MenuModel;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsCommentState;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsOperatingType;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.util.AppNewsMenuConfig;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.RemoveUeditorStyleUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.NewsMenu;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.model.NewsReceiptContent;
import com.manhui.gsl.jbqgsl.service.app.IAppUserService;
import com.manhui.gsl.jbqgsl.service.web.NewsMenuService;
import com.manhui.gsl.jbqgsl.service.web.NewsOperatingService;
import com.manhui.gsl.jbqgsl.service.web.NewsReceiptContentService;
import com.manhui.gsl.jbqgsl.service.web.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api( tags = "新闻管理" )
@Controller
@RequestMapping(AppNewsController.ROOT_URL)
public class AppNewsController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_APP + "/appNews";

    /**
     * 表情转码
     */
    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
    @Value( "${file_upload_path}" )
    private String      fileUploadPath;
    @Resource
    private AppNewsMenuConfig appNewsMenuConfig;
    @Autowired
    private NewsMenuService newsMenuService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsOperatingService newsOperatingService;

    @Autowired
    private IAppUserService iAppUserService;
    @Autowired
    private NewsReceiptContentService newsReceiptContentService;
    /**
     * 获得新闻资讯栏目
     *
     * @return
     */
    @ApiOperation(value = "获得新闻资讯栏目", notes = "获得新闻资讯栏目")
    @RequestMapping(value = "/getNewsInformationList", method = RequestMethod.POST)
    @ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "key", value = "关键字", required = true, dataType = "字符串")
		
	})
    @ResponseBody
    public ResultMessage getNewsInformationList(String key) {
        Map<String,Map<String,Integer>> menuDataMap = appNewsMenuConfig.getAppNewsMenuMap();
        Map<String,Integer> menuMap = menuDataMap.get(key);

        List<Integer> menuIds = new ArrayList<>();
        List<NewsMenu> menuList = new ArrayList<>();
        if(menuMap != null && !menuMap.isEmpty()){
            for(Map.Entry<String,Integer> entry : menuMap.entrySet()){
                menuIds.add(entry.getValue());
            }

            //查询栏目
            NewsMenu menu = new NewsMenu();
            menu.setMenuIdList(menuIds);
            try {
                menuList = newsMenuService.queryList(menu);
            }catch (Exception e){
                //throw  new RuntimeException("系统异常请联系管理员");
                return new ResultMessage("系统异常请联系管理员");
            }

        }
        return new ResultMessage(menuList);
    }

    /**
     * 获得首页  新闻
     */
    @ApiOperation( value = "首页轮播新闻", notes = "首页轮播新闻" )
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "dataJson", value = "数据", required = false, dataType = "字符串")
		
	})
    @RequestMapping("/getHomeNewsList")
    @ResponseBody
    public ResultMessage getHomeNewsBannerList(String dataJson)throws ParseException {
        JSONObject obj = JSON.parseObject( dataJson );
        News news = obj.toJavaObject( News.class );
        //查询已经发布的新闻
        Map<String,Map<String,Integer>> menuDataMap = appNewsMenuConfig.getAppNewsMenuMap();
        Map<String,Integer> menuMap = menuDataMap.get("newsInformation");
        List<Integer> stateList = new ArrayList<>();
        stateList.add(NewsState.RELEASE.getId());
        news.setStateList(stateList);
        if(menuMap != null && !menuMap.isEmpty()){
            List<Integer> menuIdList = new ArrayList<>();
            for(Map.Entry<String,Integer> entry : menuMap.entrySet()){
                menuIdList.add(entry.getValue());
            }
            news.setMenuIdList(menuIdList);
        }
        PageInfo<News> pageInfo = newsService.appQueryList(news);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );

        return new ResultMessage(dataMap);
    }

    /**
     * 获得新闻,根据栏目查询
     */
    @RequestMapping("/getNewsList")
    @ResponseBody
    public ResultMessage getNewsList(News news)throws ParseException{
        if(StringUtils.isBlank(news.getMenuIds())&&news.getMenuId() == null){
            return new ResultMessage("参数错误!");
        }
        //查询已经发布的新闻
        List<Integer> stateList = new ArrayList<>();
        stateList.add(NewsState.RELEASE.getId());
        news.setStateList(stateList);
        if(StringUtils.isNotBlank(news.getMenuIds())){
            List<Integer> menuIdList = new ArrayList<>();
            String[] menuIdArray = news.getMenuIds().split(",");
            if(menuIdArray.length>0){
                for(int i=0;i<menuIdArray.length;i++){
                    menuIdList.add(Integer.parseInt(menuIdArray[i]));
                }
                news.setMenuIdList(menuIdList);
            }
        }
        PageInfo<News> pageInfo = newsService.appQueryList(news);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );

        return new ResultMessage(dataMap);
    }

    /**
     * 查询商会班子栏目 数据
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getLeaderNewsList")
    @ResponseBody
    public ResultMessage getLeaderNewsList()throws ParseException{
        Map<String,Map<String,Integer>> menuMap = appNewsMenuConfig.getAppNewsMenuMap();
        Map<String,Integer> commerceMenuIdMap = menuMap.get("orgOverview");
        if(commerceMenuIdMap == null || commerceMenuIdMap.isEmpty()){
            return new ResultMessage("数据错误，请稍后重试");
        }
        News news = new News();
        news.setMenuId(commerceMenuIdMap.get("menu_business_hui"));
        //查询已经发布的新闻
        List<Integer> stateList = new ArrayList<>();
        stateList.add(NewsState.RELEASE.getId());
        news.setStateList(stateList);


        PageInfo<News> pageInfo = newsService.appQueryList(news);
        List<News> dataList = pageInfo.getList();
        //对数据进行排序

//        Comparator<News> comparing = comparing((News n) -> n.getPriority());
//         1、 逆序排序
//        if(dataList != null && !dataList.isEmpty()){
//            dataList.sort(comparing(News::getPriority).reversed());
//        }      
        //2:去掉富文本标签
        if(dataList != null && !dataList.isEmpty()){
            for (News news2 : dataList) {//
            	String newsContent = RemoveUeditorStyleUtil.getTextFromHtml(news2.getNewsContent());
            	news2.setNewsContent(newsContent);
            }
        }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", dataList );

        return new ResultMessage(dataMap);
    }

    /**
     * 模糊查询新闻
     * @param dataJson
     * @return
     */
    @RequestMapping("/blurryQueryNews")
    @ResponseBody
    public ResultMessage blurryQueryNews(String dataJson) throws Exception{
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误!");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",obj.get("name"));
        paramMap.put("pageSize",obj.get("pageSize"));
        paramMap.put("pageNo",obj.get("pageNo"));
        ResultMessage message = newsService.getBlurryQueryNewsOrMenu(paramMap);
        return message;
    }

    /**
     * 根据id 查询新闻详情，跳转详情页面
     * @param newsId
     * @param model
     * @return
     */
    @RequestMapping("/getNewsDetails")
    public String getNewsDetails(Integer newsId,ModelMap model) throws Exception {
        if(newsId == null || newsId.equals(0)){
            throw new RuntimeException("参数错误!");
        }
        News news = new News();
        news.setViewNums(1);
        news.setNewsId(newsId);
        //修改查看数量
        ResultMessage message = newsService.updateNews(news,null);
        NewsMenu menu = null;
        String menuType = "listType";
        if(message.getState().equals(0)){
            news = newsService.queryNewById(newsId);
            
            if(!news.getState().equals(NewsState.RELEASE.getId())){
                //状态不是已经发布状态，返回null
                news = null;
            }else {
                menu = newsMenuService.queryById( Integer.valueOf(news.getMenuIds()));
                if(menu != null) {
                    menuType = menu.getMenuModel();
                }
            }
            
        }else {
            throw new RuntimeException("数据错误， 请联系管理员");
        }
        String url = "/web/html/news/appNews/news_detail";
        if(menuType.equals(MenuModel.TEXT_VIDEO_ARTICLES.getCode() )) {
            news.setReleaseTime(news.getReleaseTime().substring( 0, 10));
            url = "/web/html/news/appNews/news_detail_articles";
        }else {
            news.setReleaseTime(DateUtil.getConversionTime(news.getReleaseTime(),DateUtil.sdf));
        }
        model.addAttribute("news",news);
        return url;
    }

    /**
     * 根据id 查询新闻详情，返回详情数据
     * @param newsId
     * @return
     */
    @RequestMapping("/getNewsDetailsData")
    @ResponseBody
    public ResultMessage getNewsDetailsData(Integer newsId) throws Exception {
        if(newsId == null || newsId.equals(0)){
            throw new RuntimeException("参数错误!");
        }
        News news = new News();
        news.setViewNums(1);
        news.setNewsId(newsId);
        //修改查看数量
        ResultMessage message = newsService.updateNews(news,null);

        if(message.getState().equals(0)){
            news = newsService.queryNewById(newsId);
            news.setReleaseTime(DateUtil.getConversionTime(news.getReleaseTime(),DateUtil.sdf));
            if(!news.getState().equals(NewsState.RELEASE.getId())){
                //状态不是已经发布状态，返回null
                news = null;
            }
        }else {
            throw new RuntimeException("数据错误， 请联系管理员");
        }

        return new ResultMessage(news);
    }
    
    @ApiOperation( value = "修改新闻（collectionNums：收藏，viewNums：查看，commentNums：评论，likeNums：点赞）" )
    @RequestMapping(value = "/updateNewsOperating", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateNewsOperating(String dataJson) throws Exception {
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        Map<Integer,String> mapEnum = NewsOperatingType.codeMap;
        NewsOperating operating = obj.toJavaObject( NewsOperating.class );
        if((operating.getNewsId() == null) || operating.getType() == null ||
                mapEnum.get(operating.getType())==null || (!operating.getType().equals( NewsOperatingType.likeNums.getId()) && (operating.getUserId() == null
                || StringUtils.isBlank(operating.getUserName()))) || (StringUtils.isBlank(operating.getNewsName()))){//如果是点赞，将不要用户信息
            return new ResultMessage("参数错误");
        }
        if(operating.getType().equals("commentNums") || operating.getType().equals("1")){
            operating.setCommentState(NewsCommentState.ONE.getId());

            if(StringUtils.isBlank(operating.getContent())){
                return new ResultMessage("评论内容不能为空");
            }
        }
        if(StringUtils.isNotBlank(operating.getContent())){
            //表情转码
            String content = emojiConverter.toAlias(operating.getContent());
            operating.setContent(content);
        }
        ResultMessage message = newsOperatingService.saveNewsOperating(operating);
        return message;
    }

    @ApiOperation( value = "查询 当前人员 是否对新闻进行 收藏点赞，评论" )
    @RequestMapping(value = "/getUserNewsOperatingList", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage getUserNewsOperatingList(NewsOperating operating) throws ParseException {
        if(StringUtils.isBlank(operating.getUserId())||operating.getNewsId() == null){
            return new ResultMessage("参数错误");
        }
        Map<String,Object> dataMap = newsOperatingService.getUserNewsOperatingList(operating);
        return new ResultMessage(dataMap);
    }

    /**
     * 查询 新闻，评论列表
     * @param
     * @return
     */
    @RequestMapping("/queryCommentList")
    @ResponseBody
    public ResultMessage queryCommentList(NewsOperating operating) throws ParseException {
        if(operating.getNewsId() == null){
            return new ResultMessage("参数错误!");
        }
        operating.setType(NewsOperatingType.commentNums.getId());
        operating.setState(1);
        Map<String,Object> dataMap = newsOperatingService.queryCommentList(operating);
        return new ResultMessage(dataMap);
    }
    /**
     * 获得用户收藏新闻
     * @param dataJson
     * @return
     */
    @RequestMapping("/getUserCollectionNews")
    @ResponseBody
    public ResultMessage getUserCollectionNews(String dataJson) throws ParseException {
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误！");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        NewsOperating operating = obj.toJavaObject( NewsOperating.class );
        if(StringUtils.isBlank(operating.getUserId())){
            return new ResultMessage("参数错误！");
        }
        //查询用户
        AppUser u = iAppUserService.queryUserMemberModelById(operating.getUserId());
        if(u == null){
            return new ResultMessage("参数错误，或用户不存在！");
        }
        ResultMessage message = newsOperatingService.getUserCollectionNews(operating);
        return message;
    }

    /**
     * 取消用户收藏,点赞新闻
     * @param dataJson
     * @return
     */
    @RequestMapping("/cancelUserCollection")
    @ResponseBody
    public ResultMessage cancelUserCollection(String dataJson){
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误！");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        NewsOperating operating = obj.toJavaObject( NewsOperating.class );
        if((operating.getNewsId() == null && StringUtils.isBlank(operating.getNewsIds()))|| StringUtils.isBlank(operating.getUserId())
                || operating.getType() == null){
            return new ResultMessage("参数错误！");
        }
        if(StringUtils.isBlank(operating.getNewsIds())){
            operating.setNewsIds(operating.getNewsId()+"");
        }
        String newsIds[] = operating.getNewsIds().split(",");
        ResultMessage message = new ResultMessage();
        for(int i=0;i<newsIds.length;i++){
            operating.setNewsId(Integer.parseInt(newsIds[i]));
            message = newsOperatingService.cancelUserCollection(operating);
        }

        return message;
    }

    /**
     *  保存 回执内容
     * @param dataJson
     * @return
     */
    @RequestMapping("/saveReceipt")
    @ResponseBody
    public ResultMessage saveReceipt(String dataJson) throws Exception {
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误！");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        NewsReceiptContent receiptContent = obj.toJavaObject( NewsReceiptContent.class );
        if(StringUtils.isBlank(receiptContent.getUserId()) || receiptContent.getNewsId()==null ||
                StringUtils.isBlank(receiptContent.getNewsName()) || receiptContent.getReceiptType() == null||
                StringUtils.isBlank(receiptContent.getContent()) || StringUtils.isBlank(receiptContent.getUserName())){
            return new ResultMessage("参数错误！");
        }
        if(receiptContent.getReceiptType().equals(ReceiptType.three.getId()) && StringUtils.isBlank(receiptContent.getAssignPeople())){
            return new ResultMessage("指派人参加，参与人员不能为空！");
        }
        //查询用户
        AppUser u = iAppUserService.queryUserMemberModelById(receiptContent.getUserId());
        if(u == null){
            return new ResultMessage("参数错误，或用户不存在！");
        }
        ResultMessage message = newsReceiptContentService.saveReceipt(receiptContent);
        return message;
    }

    @ApiOperation( value = "查询人员新闻回执内" )
    @RequestMapping(value = "/queryUserReceipt", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage queryUserReceipt(String dataJson) throws Exception {
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误！");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        NewsReceiptContent receiptContent = obj.toJavaObject( NewsReceiptContent.class );
        if(StringUtils.isBlank(receiptContent.getUserId()) || receiptContent.getNewsId()==null){
            return new ResultMessage("参数错误！");
        }
        //查询用户
        AppUser u = iAppUserService.queryUserMemberModelById(receiptContent.getUserId());
        if(u == null){
            return new ResultMessage("参数错误，或用户不存在！");
        }
        ResultMessage message = newsReceiptContentService.queryUserReceipt(receiptContent);
        return message;
    }

    /**
     * 修改人员回执内容
     * @param dataJson
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUserReceipt")
    @ResponseBody
    public ResultMessage updateUserReceipt(String dataJson) throws Exception {
        if(StringUtils.isBlank(dataJson)){
            return new ResultMessage("参数错误！");
        }
        JSONObject obj = JSON.parseObject( dataJson );
        NewsReceiptContent receiptContent = obj.toJavaObject( NewsReceiptContent.class );
        if(StringUtils.isBlank(receiptContent.getUserId()) || receiptContent.getReceiptType() == null
                ||StringUtils.isBlank(receiptContent.getContent()) || receiptContent.getReceiptId() == null){
            return new ResultMessage("参数错误！");
        }
        if(receiptContent.getReceiptType().equals(ReceiptType.three.getId()) && StringUtils.isBlank(receiptContent.getAssignPeople())){
            return new ResultMessage("指派人参加，参与人员不能为空！");
        }
        //查询用户
        AppUser u = iAppUserService.queryUserMemberModelById(receiptContent.getUserId());
        if(u == null){
            return new ResultMessage("参数错误，或用户不存在！");
        }
        ResultMessage message = newsReceiptContentService.updateUserReceipt(receiptContent);
        return message;
    }
}
