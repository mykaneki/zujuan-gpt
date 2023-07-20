package com.cmy.zujuan.pojo;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public Result(int status, String message, T data) {
        this.code = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(-1, message, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(-1, message, null);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
