package com.cmy.zujuan.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cmy.zujuan.pojo.GetQuestionsParams;
import com.cmy.zujuan.pojo.MessageResponse;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestUtil {

    private static final String BASE_URL = "https://www.xuekubao.com/index.php";
    private static final String S = "Index";
    private static final String M = "Api";
    private static final String A1 = "getQuestions";
    private static final String A2 = "getAnswer";
    private static String requestUrl = String.format("%s?s=%s&m=%s&a=%s", BASE_URL, S, M, A1);
    private static String requestUrl2 = String.format("%s?s=%s&m=%s&a=%s", BASE_URL, S, M, A2);

    @Value("${xuekubao.api-key}")
    private static String accessKey;

    public static JSONObject sendFirstRequest(GetQuestionsParams params) {
        Map<String, Object> formData = new HashMap<>();
        formData.put("accessKey", params.getAccessKey());
        formData.put("knowledgeId", params.getKnowledgeId());
        formData.put("qtypeId", params.getQtypeId());
        formData.put("page", params.getPage());
        formData.put("paperType", params.getPaperType());
        formData.put("diff", params.getDiff());
        formData.put("year", params.getYear());

        HttpResponse response = HttpRequest.post(requestUrl)
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .form(formData)
                .execute();

        if (response.isOk()) {
            String body = response.body();
            return JSONUtil.parseObj(body);
        } else {
            throw new RuntimeException("请求失败，HTTP状态码：" + response.getStatus());
        }
    }

    public static JSONObject sendSecondRequest(String accessKey, String qid) {
        Map<String, Object> formData = new HashMap<>();
        formData.put("accessKey", accessKey);
        formData.put("qid", qid);

        HttpResponse response = HttpRequest.post(requestUrl2)
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .form(formData)
                .execute();

        if (response.isOk()) {
            String body = response.body();
            return JSONUtil.parseObj(body);
        } else {
            throw new RuntimeException("请求失败，HTTP状态码：" + response.getStatus());
        }
    }

    public void Main() {
        GetQuestionsParams params = new GetQuestionsParams();
        params.setAccessKey(accessKey);
        params.setKnowledgeId("215755794610B6B55E6A27F3F200BB50");
        params.setQtypeId("301");
        params.setPage("1");
        params.setPaperType("39");
        params.setDiff("3");
        params.setYear("2022,2023");

        JSONObject firstResponse = ApiRequestUtil.sendFirstRequest(params);
        String qid = firstResponse.getStr("qid");

        if (qid != null) {
            JSONObject secondResponse = ApiRequestUtil.sendSecondRequest(params.getAccessKey(), qid);
            System.out.println("第二个请求的响应：" + secondResponse.toStringPretty());
        } else {
            System.out.println("无法获取qid");
        }
    }

    public static GetQuestionsParams extractAnswer(GetQuestionsParams params,MessageResponse messageResponse) {
        if (messageResponse != null && messageResponse.getAnswer() != null) {
            JSONObject answerJson = messageResponse.getAnswer();
            // {"学段":"2","年级":"301","学科":"2","教材版本":"55","试卷类型":"37","难度":"2","知识点":"0865860A7CDF4A65D79DD78C31F8A7D9","题目数量":"4","题型":"选择题","年份":"2021,2022"}
            params.setAccessKey(accessKey);
            params.setPage("1");

            params.setPharseId(getStringIfNotNull(answerJson, "学段"));
            params.setGradeId(getStringIfNotNull(answerJson, "年级"));
            params.setSubjectId(getStringIfNotNull(answerJson, "学科"));
            params.setEditionId(getStringIfNotNull(answerJson, "教材版本"));
            params.setCount(getStringIfNotNull(answerJson, "题目数量"));
            params.setPaperType(getStringIfNotNull(answerJson, "试卷类型"));
            params.setDiff(getStringIfNotNull(answerJson, "难度"));
            params.setKnowledgeId(getStringIfNotNull(answerJson, "知识点"));
            params.setQtypeName(getStringIfNotNull(answerJson, "题型"));
            params.setYear(getStringIfNotNull(answerJson, "年份"));

            return params;
        }
        return null;
    }

    private static String getStringIfNotNull(JSONObject jsonObject, String key) {
        String value = jsonObject.getStr(key);
        return "NULL".equalsIgnoreCase(value) ? null : value;
    }
}
