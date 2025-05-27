package com.grad.ai.service.impl;

import com.grad.ai.client.DeepSeekClient;
import com.grad.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * AI 服务实现类
 */
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final DeepSeekClient deepSeekClient;

    @Override
    public String predictContent(String context) {
        return deepSeekClient.predictContent(context);
    }

    @Override
    public String chat(String question, List<Map<String, String>> context) {
        return deepSeekClient.chat(question, context);
    }
} 