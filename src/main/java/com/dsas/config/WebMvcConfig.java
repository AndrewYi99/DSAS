package com.dsas.config;

import com.dsas.interceptor.loginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//配置地址映射
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //重写方法，注册拦截器（InterceptorRegistry）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器类
        HandlerInterceptor interceptor = new loginInterceptor();

        List<String> patterns=new ArrayList<>();
        patterns.add("/user/register");
        patterns.add("/user/login");
        patterns.add("/admin/login");
        patterns.add("/admin/toLogin");
        patterns.add("/assets/**");
        patterns.add("/images/**");
        //patterns.add("/verify_code");
       // patterns.add("/admin/showIndex");
        //注册拦截器类，添加黑名单(addPathPatterns("/**")),‘/*’只拦截一个层级，'/**'拦截全部
        // 和白名单(excludePathPatterns("List类型参数"))，将不必拦截的路径添加到List列表中
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**").excludePathPatterns(patterns);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("classpath:/admin").addResourceLocations("/static/");
    }
}
