package com.zhang.contract.config;

import com.zhang.contract.component.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/index.html",	//Spring Boot默认使用http://localhost:端口号就能够访问到/index.html,所以要把它忽略掉
                "/js/**",		//忽略掉js请求
                "/img/**",		//忽略掉img请求，这里放的是页面需要使用到的固定的图片，像什么error.png,loading.gif之类的
                "/css/**",		//忽略掉css请求
                "/fonts/**",
                "/login",
                "/favicon.ico",
                "/",
                "/index.html",
                "/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}