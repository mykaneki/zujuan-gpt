package com.cmy.zujuan.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 根据知识点取试题
 */
@Data
@Component
public class GetQuestionsParams {
    /**
     * 秘钥 必填
     */
    private String accessKey;
    /**
     * 知识点id，参考第3个API内容的oldId内容 必填
     */
    private String knowledgeId;
    /**
     * 分页码 必填
     */
    private String page;
    /**
     * 难度id diffTypes
     */
    private String diff;
    /**
     * 试卷类型id paperTypes
     */
    private String paperType;
    /**
     * 题型id qtypes
     */
    private String qtypeId;
    /**
     * 题型名字 qtypeName
     */
    private String qtypeName;
    /**
     * 年份 多年份格式：2022,2018
     */
    private String year;
    /**
     * 学科id
     */
    private String subjectId;

    /**
     * 学段id
     */
    private String pharseId;
    /**
     * 教材版本id
     */
    private String editionId;
    /**
     * 年级id
     */
    private String gradeId;
    /**
     * 题目数量
     */
    private String count;
}