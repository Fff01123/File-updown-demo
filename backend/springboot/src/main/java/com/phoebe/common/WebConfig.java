package com.phoebe.common;

/**
 * @program: springboot
 * @description:
 * * description: 控制层的配置类（controller,springmvc）
 *  * 1.controller ——> 例如给controller路径加上一个前缀
 *  * 2.全局异常处理器
 *  * 3.handlerMapping handlerAdapter
 *  * 4.静态资源处理
 *  * 5.jsp 视图解析器前后缀
 *  * 6.json转化器
 *  * 7.拦截器
 * @author: F_phoebe
 * @create: 2024-05-08 16:14
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class WebConfig implements  WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/files/**");
        log.info("拦截器已经启动");
    }
}