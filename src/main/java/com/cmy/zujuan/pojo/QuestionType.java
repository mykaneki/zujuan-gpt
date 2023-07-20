package com.cmy.zujuan.pojo;

import lombok.Data;

/**
 * 题型
 *
 * subjectId：学科id，参考第1个API的code内容，选填字段
 * pharseId：学段id，参考第1个API的code内容，必填字段，默认为1
 */
@Data
public class QuestionType {

    private String id;

    private String subjectId;

    private String pharseId;

    private String typeName;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPharseId() {
        return pharseId;
    }

    public void setPharseId(String pharseId) {
        this.pharseId = pharseId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}