package com.cmy.zujuan.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cmy.zujuan.pojo.MessageResponse;
import jakarta.annotation.Resource;

public class MessageResponseUtil {
    /**
     * 解析返回的消息
     * 1. answer 字段是一个 JSON 字符串，需要再次解析
     * 2. 将数据存入 MessageResponse 对象中
     *
     * @param json 从 GPT 接收到的 JSON 字符串
     * @return MessageResponse 对象
     */
    public static MessageResponse fromJson(MessageResponse messageResponse,JSONObject json) {
        // 从 "data" 字段中获取子 JSON 对象
        JSONObject data = json.getJSONObject("data");
        // 在创建 MessageResponse 对象之前解析 answer 字段
        // 解析出错 因为中文逗号
        String answer = data.getStr("answer");
        //
        answer = toHalfWidthUtil.toHalfWidth(answer);
        JSONObject answerJson = parseAnswerToJson(answer);
        // {"学段":"2","年级":"202","学科":"3","教材版本":"22","题目数量":"3","试卷类型":"36","难度":"2","知识点":"AC539936CCF02065D63DBAE632DF5D4A","题型":"完形填空","年份":"NULL"}
        System.out.println("GPT返回的answerJson");
        System.out.println(answerJson);
        System.out.println("------------------------------------------------------------");

        messageResponse.setAnswer(answerJson);
        // 提取相关字段并设置到 MessageResponse 对象中
        messageResponse.setEvent(data.getStr("event"));
        messageResponse.setTaskId(data.getStr("task_id"));
        messageResponse.setId(data.getStr("id"));
        messageResponse.setCreatedAt(data.getLong("created_at"));
        messageResponse.setConversationId(data.getStr("conversation_id"));

        return messageResponse;
    }

    public static JSONObject parseAnswerToJson(String answer) {
        return JSONUtil.parseObj(answer);
    }

}