package com.manhui.gsl.jbqgsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class JbqGslApplication {
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
        return builder.sources( JbqGslApplication.class );
    }

    public static void main( String[] args ) throws Exception {
        SpringApplication.run( JbqGslApplication.class, args );
    }
}
