package com.grad.ai.controller;

import com.grad.ai.model.ChatRequest;
import com.grad.ai.model.PredictRequest;
import com.grad.ai.model.Result;
import com.grad.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AI 控制器
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    /**
     * 智能预测内容
     */
    @PostMapping("/predict")
    public Result<String> predictContent(@RequestBody PredictRequest request) {
        if (request.getContext() == null || request.getContext().trim().isEmpty()) {
            return Result.error("上下文内容不能为空");
        }
        String content = aiService.predictContent(request.getContext());
        return Result.success(content);
    }

    /**
     * 问答对话
     */
    @PostMapping("/chat")
    public Result<String> chat(@RequestBody ChatRequest request) {
        if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
            return Result.error("问题不能为空");
        }
        String answer = aiService.chat(request.getQuestion(), request.getContext());
        return Result.success(answer);
    }
} 