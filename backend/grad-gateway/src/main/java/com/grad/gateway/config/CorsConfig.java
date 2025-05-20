package com.grad.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration config = new CorsConfiguration();

    // 允许的域名
    config.addAllowedOrigin("http://localhost:5173"); // 前端开发服务器
    config.addAllowedOrigin("http://localhost:5173"); // 前端生产服务器

    // 允许的HTTP方法
    config.addAllowedMethod("*");

    // 允许的请求头
    config.addAllowedHeader("*");

    // 允许携带认证信息（cookies等）
    config.setAllowCredentials(true);

    // 暴露的响应头
    config.addExposedHeader("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }
}