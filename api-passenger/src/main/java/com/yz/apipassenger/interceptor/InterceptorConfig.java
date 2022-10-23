package com.yz.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-22:28
 * @Description: com.yz.apipassenger.interceptor
 * @version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return  new JwtInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor())
        //拦截的路径
        .addPathPatterns("/**")
        //不拦截的路径
        .excludePathPatterns("/noauthTest")
        .excludePathPatterns("/verification-code")
        .excludePathPatterns("/verification-code-check");

    }
}
