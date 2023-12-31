package com.igorfood.core.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedMethods("*");
    }

    @Bean
    public Filter shallowEtagHraderFilter(){
        return new ShallowEtagHeaderFilter();
    }
}
