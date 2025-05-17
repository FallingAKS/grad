package com.grad.contentproduction.controller;

import com.grad.contentproduction.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

  private final AiService aiService;

  @Autowired
  public AiController(AiService aiService) {
    this.aiService = aiService;
  }

  @GetMapping("/predict")
  public String predictContent(@RequestParam String input) {
    return aiService.predictContent(input);
  }

  @GetMapping("/answer")
  public String answerQuestion(@RequestParam String question) {
    return aiService.answerQuestion(question);
  }
}