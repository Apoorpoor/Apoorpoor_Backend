package com.example.apoorpoor_backend.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addCorsMappings(final CorsRegistry registry){
        registry.addMapping("/**")
                .exposedHeaders("ACCESS_KEY", "REFRESH_KEY", "NICKNAME")
                //.allowedOriginPatterns("http://localhost:3000","http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD");
    }
}

