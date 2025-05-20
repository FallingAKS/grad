package com.grad.datamonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class DataMonitoringApplication {
  public static void main(String[] args) {
    SpringApplication.run(DataMonitoringApplication.class, args);
  }
}