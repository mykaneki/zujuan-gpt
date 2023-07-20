package com.cmy.zujuan.pojo;

import cn.hutool.json.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageResponse {
    private String event;
    private String taskId;
    private String id;
    private JSONObject answer;
    private long createdAt;
    private String conversationId;
}