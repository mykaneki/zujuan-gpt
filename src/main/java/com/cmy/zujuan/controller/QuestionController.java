package com.cmy.zujuan.controller;

import com.cmy.zujuan.pojo.ApiResponse;
import com.cmy.zujuan.pojo.GetQuestionsParams;
import com.cmy.zujuan.pojo.MessageResponse;
import com.cmy.zujuan.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @Resource
    private GetQuestionsParams getQuestionsParams;

    @Resource
    private MessageResponse messageResponse;
    @PostMapping("/questions")
    public ApiResponse getQuestions() {
        ApiResponse questions = questionService.getQuestions(getQuestionsParams);

        String url = "API_URL"; // 替换为实际的API URL

        return null;
    }
}