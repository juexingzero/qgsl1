package com.manhui.gsl.jbqgsl.framework.apidocs;

import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class GzhApis {
    public static Docket createRestApi() {
        return new Docket( DocumentationType.SWAGGER_2 ).groupName( "GZH" )
                .genericModelSubstitutes( DeferredResult.class )
                .useDefaultResponseMessages( false )
                .forCodeGeneration( true )
                .pathMapping( "/" )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.manhui.gsl.jbqgsl.controller.gzh" ) )
                .build()
                .apiInfo( apiInfo() );
    }

    private static ApiInfo apiInfo() {
        Contact contact = new Contact("kevin", "", "");
        return new ApiInfoBuilder().title( "江北区智能工商联信息化平台 - 接口文档" )
                .description( "本文档只说明微信公众号端的相关接口" )
                .contact( contact )
                .version( "1.0.20180802" )
                .build();
    }
}
