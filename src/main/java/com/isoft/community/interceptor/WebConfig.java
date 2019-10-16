package com.isoft.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {         //实现WebMvcConfigurer接口的方法可以自定义spring mvc的配置(自定义interceptor的配置)

    @Autowired
    private SessionInterceptor sessionInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");        //对所有的请求都进行拦截
    }
}

//    Spring Boot 默认提供Spring MVC 自动配置，不需要使用@EnableWebMvc注解
//    如果需要配置MVC（拦截器interceptor、格式化、视图等） 请使用添加@Configuration并实现WebMvcConfigurer接口.不要添加@EnableWebMvc注解。
//    @EnableWebMvc 只能添加到一个@Configuration配置类上，用于导入Spring Web MVC configuration
//    最后，如果Spring Boot在classpath里看到有 spring webmvc 也会自动添加@EnableWebMvc。
//    more config about WebMvcConfigurer from blog(https://blog.csdn.net/x763795151/article/details/85252540)