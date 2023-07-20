package com.cmy.zujuan.pojo;

import lombok.Data;


/**
 * 试卷类型
 * 39 历年真题 32 模拟题 34 期末考试 35 期中考试 36 月考试卷 38 单元测试 37 其他
 */
@Data
public class PaperType {

    private String id;

    private String name;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}