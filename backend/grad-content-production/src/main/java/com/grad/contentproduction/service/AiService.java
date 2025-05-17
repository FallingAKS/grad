package com.grad.contentproduction.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {
  private static final String DEEPSEEK_URL = "http://deepseek-api-url";
  private final RestTemplate restTemplate = new RestTemplate();

  public String predictContent(String input) {
    String url = DEEPSEEK_URL + "/predict?input=" + input;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }

  public String answerQuestion(String question) {
    String url = DEEPSEEK_URL + "/answer?question=" + question;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }
}