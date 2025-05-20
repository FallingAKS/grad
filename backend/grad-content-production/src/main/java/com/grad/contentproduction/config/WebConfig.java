package com.grad.contentproduction.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${file.upload.path:/upload}")
  private String uploadPath;

  /**
   * 配置静态资源访问
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 配置上传文件的访问路径
    registry.addResourceHandler("/upload/**")
        .addResourceLocations("file:" + uploadPath + File.separator);
  }
}