package com.cmy.zujuan.service;

import com.cmy.zujuan.pojo.ApiResponse;
import com.cmy.zujuan.pojo.GetQuestionsParams;

public interface QuestionService {

    ApiResponse getQuestions(GetQuestionsParams getQuestionsParams);

    /**
     * 根据提供的学科id、学段id、题型名字获取题型id
     * @param subjectId 学科id
     * @param pharseId 学段id
     * @param qtypeName 题型名字
     * @return 题型id
     */
    GetQuestionsParams getQuestionParams(String subjectId, String pharseId, String qtypeName);
}
