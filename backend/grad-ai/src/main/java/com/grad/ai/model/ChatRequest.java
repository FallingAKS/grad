package com.grad.ai.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 聊天请求
 */
@Data
public class ChatRequest {

    /**
     * 问题
     */
    private String question;

    /**
     * 上下文对话历史
     */
    private List<Map<String, String>> context;
} 