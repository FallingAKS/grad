package com.grad.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class IndexRouteConfig {

  @Bean
  public RouterFunction<ServerResponse> indexRouterFunction() {
    return RouterFunctions
        .route(RequestPredicates.GET("/"),
            request -> ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue("<html><head><meta charset=\"UTF-8\"><title>Grad Gateway</title>" +
                    "<style>" +
                    "body { font-family: 'Arial', sans-serif; margin: 0; padding: 20px; background: #f5f5f5; color: #333; line-height: 1.6; }"
                    +
                    ".container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }"
                    +
                    "h1 { color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 10px; }" +
                    "h2 { color: #3498db; margin-top: 20px; }" +
                    "ul { padding-left: 20px; }" +
                    "li { margin-bottom: 8px; }" +
                    "code { background: #f8f8f8; padding: 2px 5px; border-radius: 3px; color: #e74c3c; }" +
                    "</style></head>" +
                    "<body><div class='container'>" +
                    "<h1>欢迎访问 Grad Gateway 服务</h1>" +
                    "<p>这是高校融媒体平台的 API 网关服务，负责路由和管理所有微服务请求。</p>" +
                    "<h2>可用服务：</h2>" +
                    "<ul>" +
                    "<li><code>/api/data</code> - 数据监测服务</li>" +
                    "<li><code>/api/content</code> - 内容生产服务</li>" +
                    "<li><code>/api/forum</code> - 校园论坛服务</li>" +
                    "<li><code>/api/interaction</code> - 师生互动服务</li>" +
                    "</ul>" +
                    "<p>状态：<span style='color:green;font-weight:bold;'>正常运行中</span></p>" +
                    "</div></body></html>"));
  }
}