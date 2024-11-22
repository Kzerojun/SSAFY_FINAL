package com.enjoytrip.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /files/** 경로로 요청된 파일을 C:/uploads 디렉토리에서 제공
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/Users/SSAFY/Desktop/ssafy");
    }
}
