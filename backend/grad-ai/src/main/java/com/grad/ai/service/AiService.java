package com.grad.ai.service;

import java.util.List;
import java.util.Map;

/**
 * AI 服务接口
 */
public interface AiService {

    /**
     * 智能预测内容
     *
     * @param context 上下文内容
     * @return 预测的文本
     */
    String predictContent(String context);

    /**
     * 问答对话
     *
     * @param question 问题
     * @param context 上下文对话历史
     * @return 回答
     */
    String chat(String question, List<Map<String, String>> context);
} 