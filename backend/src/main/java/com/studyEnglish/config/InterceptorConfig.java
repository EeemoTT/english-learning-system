package com.studyEnglish.config;

import com.studyEnglish.common.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/10
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                                               .excludePathPatterns("/login","/register","/**/export","/**/import","/files/**","/testAnswer");
        //拦截所有，通过判断token是否合法
    }
}
