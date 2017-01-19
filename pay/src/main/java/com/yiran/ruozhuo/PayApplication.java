package com.yiran.ruozhuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by ruozhuo on 2017/1/17.
 */
@SpringBootApplication
public class PayApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PayApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

}
