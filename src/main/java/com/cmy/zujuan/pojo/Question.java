package com.cmy.zujuan.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Question {
    // 题目 选项
    private String title;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    // 题型
    private String qtpye;
    // 难度
    private String diff;
    // 年份
    private String year;
    // 来源
    private String source;
    // 学科id，参考第1个API的code内容
    private String subjectId;
    // 试卷类型
    private String paperTpye;
    // 试题md5 根据这个获取答案
    private String qid;

    // 答案字段
    // 根据试题md52获取答案解析api
    private String answer1;
    private String answer2;
    private String parse;

    private String isSub;
    private String isNormal;
    private String isunique;
    private String id;
    // 联系的其他知识点
    private List<Knowledge> knowledgeMore;
}
