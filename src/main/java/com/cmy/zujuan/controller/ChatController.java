package com.cmy.zujuan.controller;

import com.cmy.zujuan.pojo.*;
import com.cmy.zujuan.service.ChatService;
import com.cmy.zujuan.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// 跨域
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ChatController {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.api-endpoint}")
    private String apiEndpoint;
    @Resource
    private GetQuestionsParams getQuestionsParams;

    @Resource
    private MessageResponse messageResponse;
    @Resource
    private ChatService chatService;
    @Resource
    private QuestionService questionService;

    @PostMapping("/message")
    public Result sendMessage(@RequestBody ChatMessage chatMessage) {
        System.out.println(chatMessage);
        System.out.println("/api/message");
        try {
            messageResponse = chatService.sendMessage(chatMessage);
            ApiResponse questions = questionService.getQuestions(getQuestionsParams);
            System.out.println("学库宝返回的试题 " + questions);
            return Result.success(questions);
        } catch (RuntimeException e) {
            return Result.failure(e.getMessage());
        }
    }
}
