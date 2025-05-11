package com.grad.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class GradEurekaApplication {

  public static void main(String[] args) {
    SpringApplication.run(GradEurekaApplication.class, args);
  }
}