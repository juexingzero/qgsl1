package com.manhui.gsl.jbqgsl.framework.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:config.properties")
public class DefaultView implements WebMvcConfigurer {
	//新闻
    @Value( "${file_upload_path}" )
    private String fileUploadPath;
    //安卓apk
    @Value( "${file_download_apk}" )
    private String fileDownloadApk;
   //app_user
    @Value( "${file_upload_path2}" )
    private String fileUploadPath2;
    //后台头像
    @Value( "${file_download_back_userImg}" )
    private String file_download_back_userImg;
    //企业会员证件
    @Value( "${file_companyMember_path}" )
    private String file_companyMember_path;
    //企业会员证件
    @Value( "${file_personMember_path}" )
    private String file_personMember_path;

    /**
      addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
      registry.addViewController("请求路径").setViewName("请求页面文件路径")
     */
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "/manager/index" );
    }

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor( new LogInterceptor() )
                .addPathPatterns( "/**" )
                .excludePathPatterns( "/login/login" )
                .excludePathPatterns( "/login/loginSubmit" )
                .excludePathPatterns( "/topicEvaluate/checkAndUpdateEvaluateState" )
                .excludePathPatterns( "/resources/**" )
                .excludePathPatterns( "/static/**" );
    }

    /**
     * 配置静态访问资源
     * 
     * @param registry
     */
    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        registry.addResourceHandler( "/upload/**" ).addResourceLocations( "file:" + fileUploadPath );
        registry.addResourceHandler( "/apk/**" ).addResourceLocations( "file:" + fileDownloadApk );
        registry.addResourceHandler( "/back/**" ).addResourceLocations( "file:" + file_download_back_userImg);
        registry.addResourceHandler( "/member/**" ).addResourceLocations( "file:" + file_companyMember_path);
        registry.addResourceHandler( "/personmember/**" ).addResourceLocations( "file:" + file_personMember_path);
        registry.addResourceHandler( "/heads/**" ).addResourceLocations( "file:" + fileUploadPath2+"/heads/" );
        registry.addResourceHandler( "/qrpath/**" ).addResourceLocations( "file:" + fileUploadPath2+"/qrpath/" );
        registry.addResourceHandler( "/swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
        WebMvcConfigurer.super.addResourceHandlers( registry );
    }
}
