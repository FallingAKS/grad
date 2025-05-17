package com.grad.interaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TeacherStudentInteractionApplication {
  public static void main(String[] args) {
    SpringApplication.run(TeacherStudentInteractionApplication.class, args);
  }
}