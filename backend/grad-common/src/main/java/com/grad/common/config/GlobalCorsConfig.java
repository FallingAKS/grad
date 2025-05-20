package com.grad.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置
 */
@Configuration
public class GlobalCorsConfig {

  /**
   * 配置跨域过滤器
   */
  @Bean
  public CorsFilter corsFilter() {
    // 创建CORS配置
    CorsConfiguration config = new CorsConfiguration();

    // 允许的域名，可以配置为具体的前端域名，如 http://localhost:5173
    config.addAllowedOriginPattern("*");

    // 允许跨域的请求方法
    config.addAllowedMethod("*");

    // 允许的请求头
    config.addAllowedHeader("*");

    // 允许携带身份凭证（cookies等）
    config.setAllowCredentials(true);

    // 预检请求的缓存时间（秒）
    config.setMaxAge(3600L);

    // 配置所有接口都支持跨域
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    // 返回配置好的CORS过滤器
    return new CorsFilter(source);
  }
}