package com.cmy.zujuan.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpRequest;
import com.cmy.zujuan.pojo.GetQuestionsParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

public class RequestParamsBuilder {


    public static HttpRequest buildGetQuestionsRequest(GetQuestionsParams params, String apiUrl) {
        Dict requestParams = new Dict();

        // 将必填 Body 参数添加到 Dict 对象中
        requestParams.set("accessKey", params.getAccessKey());
        requestParams.set("knowledgeId", params.getKnowledgeId());
        requestParams.set("page", params.getPage());

        // 根据需要将选填 Body 参数添加到 Dict 对象中
        if (params.getDiff() != null) {
            requestParams.set("diff", params.getDiff());
        }
        if (params.getPaperType() != null) {
            requestParams.set("paperType", params.getPaperType());
        }
        if (params.getQtypeId() != null) {
            requestParams.set("qtypeId", params.getQtypeId());
        }
        if (params.getYear() != null) {
            requestParams.set("year", params.getYear());
        }

        // 在 URL 中添加 Query 参数
        String requestUrl = apiUrl + "?s=Index&m=Api&a=getQuestions";

        // 使用 Dict 对象创建 HttpRequest
        HttpRequest request = HttpRequest.post(requestUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .form(requestParams);

        return request;
    }
}