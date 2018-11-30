package com.manhui.gsl.jbqgsl.controller.web.news;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsState;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsType;
import com.manhui.gsl.jbqgsl.common.util.AppNewsMenuConfig;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.CommonData;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.NewsMenu;
import com.manhui.gsl.jbqgsl.service.web.IOrganizationalService;
import com.manhui.gsl.jbqgsl.service.web.NewsMenuService;
import com.manhui.gsl.jbqgsl.service.web.NewsOperatingService;
import com.manhui.gsl.jbqgsl.service.web.NewsService;
import io.swagger.annotations.ApiOperation;

/**
 * 新闻发布管理
 */
@Controller
@RequestMapping(WebNewsController.ROOT_URL)
public class WebNewsController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/news";
    @Value( "${file_upload_path}" )
    private String      fileUploadPath;
    @Resource
    private NewsService newsService;

    @Resource
    private NewsMenuService newsMenuService;
    @Resource
    private NewsOperatingService newsOperatingService;
    @Resource
    private AppNewsMenuConfig appNewsMenuConfig;
    @Resource
    private IOrganizationalService organizationalService;
    /**
     * 新闻管理页面
     *
     * @return
     */
    @RequestMapping( "/queryListPage" )
    public String queryListPage(Integer menuId,ModelMap model) {
        model.addAttribute("menuId",menuId);
        //获得需要展示 回执的栏目
        String receiptMenuIds = "";
        Map<String,Integer> receiptMenuMap = appNewsMenuConfig.getAppNewsMenuMap().get("receipt");
        if(receiptMenuMap != null && !receiptMenuMap.isEmpty()){
            for(Map.Entry<String,Integer> entry : receiptMenuMap.entrySet()){
                receiptMenuIds += entry.getValue()+",";
            }
        }
        model.addAttribute("receiptMenuIds",receiptMenuIds);
        return "/web/html/news/newsList";
    }

    /**
     * 查询新闻列表
     */
    @RequestMapping( "/queryList" )
    @ResponseBody
    public Map<String, Object> queryList(HttpServletRequest request, News news ) {
        String user_id = AppUtil.getCookieByName( request, "user_id" );
        String inner_outer_dept = organizationalService.getInnerOuterDeptFlagByUserId( user_id );
        //外部部门-江北政策-只能展示发布人自己发布的新闻
        if("1".equals( inner_outer_dept )) {
            news.setReleaseUserId( user_id );
        }
        PageInfo<News> pageInfo = newsService.queryList( news );
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );
        return dataMap;
    }

    /**
     * 跳转新闻新增页面
     *
     * @return
     */
    @RequestMapping( "/addNewsPage" )
    public String addNewsPage(ModelMap model,News news,HttpServletRequest request) {
        String user_name = AppUtil.getCookieByName( request, "user_name" );
        if(StringUtils.isNotBlank(user_name)){
            user_name = "("+user_name+")";
        }
        Map<String,String> newsTypeMap = NewsType.codeMap;
        List<CommonData> dataList = new ArrayList<>();
        if(newsTypeMap != null && !newsTypeMap.isEmpty()){
            CommonData data;
            for(Map.Entry<String,String> entry : newsTypeMap.entrySet()){
                data = new CommonData();
                data.setId(entry.getKey());
                data.setName(entry.getValue());
                dataList.add(data);
            }
        }
        //查询栏目
        NewsMenu menu = newsMenuService.queryById(news.getMenuId());
        if(menu == null){
            throw new RuntimeException("新闻栏目不存在或已被删除，请刷新重试！");
        }
        model.addAttribute("menu",menu);
        model.addAttribute("newsTypeList",new Gson().toJson(dataList));
        model.addAttribute("user_name",user_name);
        return "/web/html/news/addNewsPage";
    }

    /**
     * 保存新闻
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping( "/saveNews" )
    @ResponseBody
    public String saveNews( String json, HttpServletRequest request ) throws Exception {
        String userid = AppUtil.getCookie( request, "user_id" );
        JSONObject obj = JSON.parseObject( json );
        News news = obj.toJavaObject( News.class );
        ResultMessage message;
        //不管是否是发布了新闻，修改后都要进入保存状态，需要重新发布
        news.setImgType("img");
        //判断上传的是视频还是图片
        if(StringUtils.isNotBlank(news.getVideoUrl())){
            //视频类型
            news.setImgType("video");
        }

        if ( news.getNewsId() == null ) {
            //新增
            news.setState( NewsState.ADD.getId() );
            message = newsService.saveNews( news, userid );
        }
        else {
            //修改
            news.setState(NewsState.ADD.getId());
            message = newsService.updateNews( news, userid );
        }
        return new Gson().toJson( message );
    }

    /**
     * 发布新闻
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping( "/releaseNews" )
    @ResponseBody
    public String releaseNews( String json, HttpServletRequest request ) throws Exception {
        String userid = AppUtil.getCookie( request, "user_id" );
        JSONObject obj = JSON.parseObject( json );
        News news = obj.toJavaObject( News.class );
        ResultMessage message;
        news.setState( NewsState.RELEASE.getId() );//赋值状态
//        news.setReleaseTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));//修改发布时间
        if ( news.getNewsId() == null ) {
            //新增
            message = newsService.saveNews( news, userid );
        }
        else {
            //修改
            message = newsService.updateNews( news, userid );
        }
        return new Gson().toJson( message );
    }

    /**
     * 新闻 下架
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping( "/obtained" )
    @ResponseBody
    public String obtained( String json, HttpServletRequest request ) throws Exception {
        String userid = AppUtil.getCookie( request, "user_id" );
        JSONObject obj = JSON.parseObject( json );
        News news = obj.toJavaObject( News.class );
        ResultMessage message;
        news.setState( NewsState.OBTAINED.getId());//赋值状态
//        news.setReleaseTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));//修改发布时间
        if ( news.getNewsId() == null ) {
            //新增
            message = newsService.saveNews( news, userid );
        }
        else {
            //修改
            message = newsService.updateNews( news, userid );
        }
        return new Gson().toJson( message );
    }
    /**
     * 删除新闻
     *
     * @param newsId
     * @param request
     * @return
     */
    @RequestMapping( "/delNews" )
    @ResponseBody
    public String delNews( Integer newsId, HttpServletRequest request ) {
        String userid = AppUtil.getCookie( request, "user_id" );
        News news = new News();
        news.setNewsId( newsId );
        ResultMessage message = newsService.delNews( news, userid );
        return new Gson().toJson( message );
    }

    /**
     * 修改新闻页面显示
     *
     * @param newsId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/updateNewsPage" )
    public String updateNewsPage( Integer newsId, Model model ,HttpServletRequest request) throws Exception {
        String user_name = AppUtil.getCookieByName( request, "user_name" );
        if(StringUtils.isNotBlank(user_name)){
            user_name = "("+user_name+")";
        }
        News news = newsService.queryNewById( newsId );
        model.addAttribute( "news", news );

        Map<String,String> newsTypeMap = NewsType.codeMap;
        List<CommonData> dataList = new ArrayList<>();
        if(newsTypeMap != null && !newsTypeMap.isEmpty()){
            CommonData data;
            for(Map.Entry<String,String> entry : newsTypeMap.entrySet()){
                data = new CommonData();
                data.setId(entry.getKey());
                data.setName(entry.getValue());
                dataList.add(data);
            }
        }
        model.addAttribute("newsTypeList",new Gson().toJson(dataList));
        model.addAttribute("user_name",user_name);
        return "/web/html/news/updateNewsPage";
    }

    /**
     * 详情新闻页面显示
     *
     * @param newsId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/detailNewsPage" )
    public String detailNewsPage( Integer newsId, Model model ,HttpServletRequest request) throws Exception {
        String user_name = AppUtil.getCookieByName( request, "user_name" );
        if(StringUtils.isNotBlank(user_name)){
            user_name = "("+user_name+")";
        }
        News news = newsService.queryNewById( newsId );
        model.addAttribute( "news", news );

        Map<String,String> newsTypeMap = NewsType.codeMap;
        List<CommonData> dataList = new ArrayList<>();
        if(newsTypeMap != null && !newsTypeMap.isEmpty()){
            CommonData data;
            for(Map.Entry<String,String> entry : newsTypeMap.entrySet()){
                data = new CommonData();
                data.setId(entry.getKey());
                data.setName(entry.getValue());
                dataList.add(data);
            }
        }
        model.addAttribute("newsTypeList",new Gson().toJson(dataList));
        model.addAttribute("user_name",user_name);
        return "/web/html/news/newsDetails";
    }

    /**
     * 保存 新闻内容图片
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping( "/uploadFile" )
    @ResponseBody
    public String uploadFile( MultipartFile file ) throws Exception {
        String trueFileName = null;
        String realPath = null;
        String fileName = file.getOriginalFilename();
        try {
            String reg = ".+(.gif|.jpg|.jpeg|.png|.svg|.GIF|.JPG|.PNG|.SVG)$";
            Pattern pattern = Pattern.compile( reg );
            Matcher matcher = pattern.matcher( fileName );
            if ( !matcher.find() ) {
                String config = "{\"state\": \"error\"," +
                        "\"url\": \"" +
                        "\"," +
                        "\"title\": \"  \"图片格式不正确," +
                        "\"original\": \"" +
                        fileName +
                        "\"}";
                return config;
            }
            String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            fileName = new Date().getTime() + suffix;

            //判断文件是否存在
            File filePath = new File(fileUploadPath);
            //判断文件夹是否存在,如果不存在则创建文件夹
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            //保存图片
            file.transferTo( new File( fileUploadPath + "/" + fileName ) );
            //url的值为图片的实际访问地址 这里我用了Nginx代理，访问的路径是http://localhost/xxx.png
            String config = "{\"state\": \"SUCCESS\"," +
                    "\"url\": \"" +
                    "/upload/" +
                    fileName +
                    "\"," +
                    "\"title\": \"" +
                    fileName +
                    "\"," +
                    "\"original\": \"" +
                    fileName +
                    "\"}";
            return config;
        }
        catch ( IllegalStateException | IOException e ) {
            e.printStackTrace();
        }
        return "";
    }
    
    @ApiOperation( value = "进入选择栏目页面", notes = "进入选择栏目页面" )
    @RequestMapping( value = "toSelectMenuPage", method = RequestMethod.GET )
    public String toSelectMenuPage() {
        return "/web/html/news/selectMenu";
    }
}
