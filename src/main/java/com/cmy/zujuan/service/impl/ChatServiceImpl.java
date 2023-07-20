package com.cmy.zujuan.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cmy.zujuan.pojo.ChatMessage;
import com.cmy.zujuan.pojo.GetQuestionsParams;
import com.cmy.zujuan.pojo.MessageResponse;
import com.cmy.zujuan.pojo.Result;
import com.cmy.zujuan.service.ChatService;
import com.cmy.zujuan.utils.MessageResponseUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {


    @Resource
    private MessageResponse messageResponse;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.api-endpoint}")
    private String apiEndpoint;

    /**
     * 发送消息
     *
     * @param chatMessage 前端输入的消息
     * @return messageResponse
     */
    @Override
    public MessageResponse sendMessage(ChatMessage chatMessage) {
        // 将 ChatMessage 实例转换为 JSON 字符串
        // requestBody {"inputs":{},"query":"3道浙教版初中七年级下英语完形填空","response_mode":"blocking","user":"abc-123"}
        String requestBody = chatMessage.toRequestBody();
        // 向 GPT 发送请求
        HttpResponse response = HttpRequest.post(apiEndpoint)
                .header("Authorization", "Bearer " + apiKey)
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .execute();

        if (response.isOk()) {
            JSONObject entries = JSONUtil.parseObj(response.body());
            // 使用 Result 类创建一个成功的响应
            Result<JSONObject> result = Result.success(entries);
            JSONObject jsonResult = JSONUtil.parseObj(result);
            // 此时的 messageResponse 为 null
            MessageResponseUtil.fromJson(messageResponse, jsonResult);
            return messageResponse;
        } else {
            throw new RuntimeException("Failed to send message: " + response.body());
        }
    }
}