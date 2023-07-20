package com.cmy.zujuan.pojo;

import lombok.Data;

import java.util.List;
/**
 * {
 *   "event": "message",
 *   "task_id": "0c173c18-c70b-48f2-be5d-db3e886a7345",
 *   "id": "74b9e727-bb9c-411f-83a0-c82421233890",
 *   "answer": "{\"学段\": \"2\", \"年级\": \"202\", \"学科\": \"3\", \"教材版本\": \"22\", \"题目数量\": \"3\", \"试卷类型\": \"36\", \"难度\": \"2\", \"题型\": \"完形填空\"}",
 *   "created_at": 1686417159,
 *   "conversation_id": "a1b71c98-0612-498e-9ec8-149b19398575"
 * }
 */
@Data
public class ApiResponse {
    private String errorCode;
    private List<Question> data;
    private String dataCount;
}
