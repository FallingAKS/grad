package com.grad.ai.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * DeepSeek API 客户端
 */
@Slf4j
@Component
public class DeepSeekClient {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private final Gson gson = new Gson();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * 发送聊天请求
     *
     * @param prompt   提示词
     * @param context  上下文
     * @return 响应文本
     */
    public String chat(String prompt, List<Map<String, String>> context) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "deepseek-chat");
            requestBody.addProperty("prompt", prompt);
            requestBody.add("context", gson.toJsonTree(context));
            requestBody.addProperty("temperature", 0.7);
            requestBody.addProperty("max_tokens", 2000);

            HttpPost request = new HttpPost(apiUrl + "/v1/chat/completions");
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JsonObject jsonResponse = gson.fromJson(result, JsonObject.class);
                    return jsonResponse.getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .get("message").getAsJsonObject()
                            .get("content").getAsString();
                }
            }
        } catch (IOException e) {
            log.error("调用 DeepSeek API 失败", e);
        }
        return "抱歉，AI 服务暂时无法使用，请稍后再试。";
    }

    /**
     * 智能预测内容
     *
     * @param context 上下文内容
     * @return 预测的文本
     */
    public String predictContent(String context) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "deepseek-chat");
            requestBody.addProperty("prompt", "根据以下上下文，预测并生成相关的后续内容：\n\n" + context);
            requestBody.addProperty("temperature", 0.8);
            requestBody.addProperty("max_tokens", 1000);

            HttpPost request = new HttpPost(apiUrl + "/v1/completions");
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JsonObject jsonResponse = gson.fromJson(result, JsonObject.class);
                    return jsonResponse.getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .get("text").getAsString();
                }
            }
        } catch (IOException e) {
            log.error("调用 DeepSeek API 失败", e);
        }
        return "抱歉，AI 服务暂时无法使用，请稍后再试。";
    }
} 