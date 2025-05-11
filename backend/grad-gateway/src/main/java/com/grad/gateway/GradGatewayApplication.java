package com.grad.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GradGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GradGatewayApplication.class, args);
  }
}