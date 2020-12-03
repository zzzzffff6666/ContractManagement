package com.zhang.contract.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    //注册数据源
    @ConfigurationProperties(prefix="spring.datasource")
    @Bean(name = "dataSource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1. 配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> init = new HashMap<>();
        init.put("loginUsername", "admin");
        init.put("loginPassword", "123456");
        init.put("allow", ""); //默认为允许所有访问

        bean.setInitParameters(init);
        return bean;
    }

    //2. 配置一个监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> init = new HashMap<>();
        init.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(init);
        //bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
