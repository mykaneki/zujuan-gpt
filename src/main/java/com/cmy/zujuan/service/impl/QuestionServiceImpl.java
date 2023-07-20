package com.cmy.zujuan.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cmy.zujuan.pojo.*;
import com.cmy.zujuan.service.QuestionService;
import com.cmy.zujuan.utils.ApiRequestUtil;
import com.cmy.zujuan.utils.RequestParamsBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private GetQuestionsParams getQuestionsParams;

    @Resource
    private MessageResponse messageResponse;

    @Value("${xuekubao.api-endpoint}")
    private String url;

    @Value("${xuekubao.api-key}")
    private  String accessKey;

    @Override
    public ApiResponse getQuestions(GetQuestionsParams getQuestionsParams) {
        GetQuestionsParams params = ApiRequestUtil.extractAnswer(getQuestionsParams, messageResponse);
        // 根据 qtypeName字段 设置 qtyeId
        setQtypeIdByName(params);
        params.setAccessKey(accessKey);
        System.out.println("从GPT解析出的参数");
        System.out.println(params);
        System.out.println("---------------------------------");
        // 返回的JSON字符串
        String json;

        // 使用 RequestParamsBuilder 构建 HttpRequest
        HttpRequest request = RequestParamsBuilder.buildGetQuestionsRequest(params, url);
        // GetQuestionsParams(accessKey=bda6410ed7bb412d5f172ca9b9566e4b, knowledgeId=DAA3A71BD46E16A9, page=1, diff=2, paperType=33, qtypeId=97, qtypeName=完形填空, year=null, subjectId=3, pharseId=2, editionId=21, gradeId=202, count=3)

        // 使用 Hutool 的 HttpRequest 发起请求
        System.out.println("向学库宝发送的请求");
        System.out.println(request);
        try (HttpResponse response = request.execute()) {
            json = response.body();
        }
        // 处理学库宝返回的JSON字符串
        JSONObject jsonObject = JSONUtil.parseObj(json);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode(jsonObject.getStr("errorCode"));
        apiResponse.setDataCount(jsonObject.getStr("dataCount"));

        List<Question> dataList = new ArrayList<>();
        // 提取字符串中的data字段 包含试题
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject dataJson = jsonArray.getJSONObject(i);
            Question question = new Question();
            // 将JSON数据设置到question对象中
            question.setTitle(dataJson.getStr("title"));
            question.setOption_a(dataJson.getStr("option_a"));
            question.setOption_b(dataJson.getStr("option_b"));
            question.setOption_c(dataJson.getStr("option_c"));
            question.setOption_d(dataJson.getStr("option_d"));
            question.setQtpye(dataJson.getStr("qtpye"));
            question.setDiff(dataJson.getStr("diff"));
            question.setYear(dataJson.getStr("year"));
            question.setSource(dataJson.getStr("source"));
            question.setSubjectId(dataJson.getStr("subjectId"));
            question.setPaperTpye(dataJson.getStr("paperTpye"));
            question.setQid(dataJson.getStr("qid"));
            question.setIsSub(dataJson.getStr("isSub"));
            question.setIsNormal(dataJson.getStr("isNormal"));
            question.setIsunique(dataJson.getStr("isunique"));
            question.setId(dataJson.getStr("id"));

            List<Knowledge> knowledgeList = new ArrayList<>();
            JSONArray knowledgeArray = dataJson.getJSONArray("knowledgeMore");
            for (int j = 0; j < knowledgeArray.size(); j++) {
                JSONObject knowledgeJson = knowledgeArray.getJSONObject(j);
                Knowledge knowledge = new Knowledge();
                // 将JSON数据设置到knowledge对象中
                knowledge.setId(knowledgeJson.getStr("id"));
                knowledge.setKnowledgeName(knowledgeJson.getStr("knowledgeName"));
                knowledge.setKnowledgeId(knowledgeJson.getStr("knowledgeId"));

                knowledgeList.add(knowledge);
            }

            question.setKnowledgeMore(knowledgeList);
            dataList.add(question);
        }

        apiResponse.setData(dataList);
        return apiResponse;
    }


    private Map<String, GetQuestionsParams> questionIdMap = new HashMap<>();

    // 初始化方法，将在类实例化后执行
    @PostConstruct
    public void init() {
        loadQuestions("D:\\project\\ideaprj\\zujuan\\src\\main\\java\\com\\cmy\\zujuan\\file\\apiData.xlsx");
    }

    // 从.xlsx文件中加载配置数据
    private void loadQuestions(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);  // 读取第一个工作表
            int numRows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < numRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String subjectId = String.valueOf((int) row.getCell(0).getNumericCellValue());
                String pharseId = String.valueOf((int) row.getCell(1).getNumericCellValue());
                String qtypeName = row.getCell(2).getStringCellValue();
                String qtypeId = String.valueOf((int) row.getCell(3).getNumericCellValue());

                GetQuestionsParams params = new GetQuestionsParams();
                params.setSubjectId(subjectId);
                params.setPharseId(pharseId);
                params.setQtypeId(qtypeId);

                String key = questionKey(subjectId, pharseId, qtypeName);
                questionIdMap.put(key, params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 生成映射键
    private String questionKey(String subjectId, String pharseId, String qtypeName) {
        return subjectId + "-" + pharseId + "-" + qtypeName;
    }

    // 获取问题参数
    @Override
    public GetQuestionsParams getQuestionParams(String subjectId, String pharseId, String qtypeName) {
        String key = questionKey(subjectId, pharseId, qtypeName);
        return questionIdMap.getOrDefault(key, null);
    }

    private void setQtypeIdByName(GetQuestionsParams params) {
        String key = questionKey(params.getSubjectId(), params.getPharseId(), params.getQtypeName());
        GetQuestionsParams questionParams = questionIdMap.getOrDefault(key, null);
        if (questionParams != null) {
            params.setQtypeId(questionParams.getQtypeId());
        }
    }

}