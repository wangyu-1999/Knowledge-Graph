package com.wangyu.kgbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wongy
 */
@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Value("${my.address}")
    private String address;
    @Value("${front.port}")
    private String port;
    @Value("${folder.address}")
    private String folderAddress;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        String url = "http://" + address + ":" + port;
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(url)
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + folderAddress);
    }
}
