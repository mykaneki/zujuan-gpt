package com.cmy.zujuan.pojo;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ChatMessage {
    private Map<String, Object> inputs;
    private String query;
    private String response_mode;
    private String user;

    public ChatMessage(String query, String user) {
        this.inputs = new LinkedHashMap<>(); // 初始化 inputs 为一个空的 LinkedHashMap
        this.query = query;
        this.response_mode = "blocking";
        this.user = user;
    }

    public ChatMessage() {
        this.inputs = new LinkedHashMap<>(); // 初始化 inputs 为一个空的 LinkedHashMap
        this.response_mode = "blocking";
    }

    // 将 ChatMessage 实例转换为 JSON 字符串
    public String toRequestBody() {
        return JSONUtil.toJsonStr(this);
    }
}