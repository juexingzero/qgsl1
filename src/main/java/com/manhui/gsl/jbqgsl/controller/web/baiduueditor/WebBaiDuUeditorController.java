package com.manhui.gsl.jbqgsl.controller.web.baiduueditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 百度ueditor配置文件使用，在ueditor.config.js 的 serverUrl 配置
 */
@Controller
@RequestMapping( "/baiDuUeditor" )
public class WebBaiDuUeditorController {
    /**
     * 新闻管理页面 |
     * 
     * @return
     */ 
    @RequestMapping( "/config" )
    @ResponseBody
    public String config( HttpServletRequest request, HttpServletResponse response ) {
        response.setContentType( "application/json;charset=utf-8" );
        String s = "{\n" +
                "            \"imageActionName\": \"uploadimage\",\n" +
                "                \"imageFieldName\": \"file\", \n" +
                "                \"imageMaxSize\": 204800, \n" +
                "                \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\".JPG\",\".PNG\",\".svg\",\".SVG\"], \n" +
                "                \"imageCompressEnable\":\"\", \n" +
                "                \"imageCompressBorder\": 1600, \n" +
                "                \"imageInsertAlign\": \"none\", \n" +
                "                \"imageUrlPrefix\": \"\",\n" +
                "                \"imagePathFormat\": \"/news/uploadFile/{yyyy}{mm}{dd}/{time}{rand:6}\" }";
        return s;
    }
}
