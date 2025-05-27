package com.grad.ai.model;

import lombok.Data;

/**
 * 预测请求
 */
@Data
public class PredictRequest {

    /**
     * 上下文内容
     */
    private String context;
} 