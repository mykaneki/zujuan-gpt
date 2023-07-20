package com.cmy.zujuan.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cmy.zujuan.pojo.Knowledge;
import com.cmy.zujuan.pojo.Question;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class JsonParseUtilTest {

    // ctrl + alt + shilft + V
    @Test
    public void parseFromFile() {
        // 替换为您的文件路径
        List<Question> questions = new ArrayList<>();
        Question question = null;
        // String filePath = "D:\\project\\ideaprj\\zujuan\\src\\test\\java\\com\\cmy\\zujuan\\file\\questionAndAnwser.json";
        String filePath = "D:\\project\\ideaprj\\zujuan\\src\\test\\java\\com\\cmy\\zujuan\\file\\question.json";
        File file = FileUtil.file(filePath);
        String jsonStr = FileUtil.readUtf8String(file);

        // 如果文件为空，返回
        if (StrUtil.isBlank(jsonStr)) {
            System.out.println("文件内容为空");
            return;
        }

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if ("0".equals(jsonObject.getStr("errorCode"))) {
            JSONArray dataArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                question = JSONUtil.toBean(dataObj, Question.class);

                JSONArray knowledgeMoreArray = dataObj.getJSONArray("knowledgeMore");
                List<Knowledge> knowledgeMore = JSONUtil.toList(knowledgeMoreArray, Knowledge.class);
                question.setKnowledgeMore(knowledgeMore);

                // 处理question对象...
                questions.add(question);
            }
        }
        System.out.println(questions);
    }
}