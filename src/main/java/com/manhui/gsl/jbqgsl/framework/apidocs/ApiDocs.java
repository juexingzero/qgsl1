package com.manhui.gsl.jbqgsl.framework.apidocs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApiDocs {
    @Bean
    public Docket webApis() {
        return WebApis.createRestApi();
    }

    @Bean
    public Docket appApis() {
        return AppApis.createRestApi();
    }

    @Bean
    public Docket gzhApis() {
        return GzhApis.createRestApi();
    }

    @Bean
    public Docket commonApis() {
        return CommonApis.createRestApi();
    }

    @Bean
    public Docket apiApis() {
        return ApiApis.createRestApi();
    }
}
