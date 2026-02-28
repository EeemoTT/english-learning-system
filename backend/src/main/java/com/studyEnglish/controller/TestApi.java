package com.studyEnglish.controller;

import com.studyEnglish.common.Result;
import com.studyEnglish.entity.wenxinyiyan.BaiduChatMessage;
import com.studyEnglish.entity.wenxinyiyan.BaiduService;
import com.studyEnglish.entity.wenxinyiyan.ErnieBotTurboStreamParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eeemot_t
 * @Description TODO 设置了全局token，在该页面做接口测试
 * @date 2024/2/27
 **/
@RestController
@RequestMapping("/testAnswer")
public class TestApi {
    @GetMapping
    public Result onlineAnswerTogether(){
        String apiKey = "NhBq3MyjFOQ745OvQ5tabyXu";
        String secretKey = "pTuNAwdFMmq8oBDJ6ElN1Df0QxoxlIQx";
        List<BaiduChatMessage> content = new ArrayList<>();
        BaiduChatMessage message = new BaiduChatMessage();
        message.setContent("你好啊");
        message.setRole("user");
        content.add(message);
        //todo 吧message和role弄进List里
        ErnieBotTurboStreamParam ernieBotTurboStreamParam = new ErnieBotTurboStreamParam();
        ernieBotTurboStreamParam.setMessages(content);
        ernieBotTurboStreamParam.setUser_id("2");
        BaiduService baiduService = new BaiduService(apiKey,secretKey);
        System.out.print(baiduService.ernieBotTurbo(ernieBotTurboStreamParam));
        return Result.success(baiduService.ernieBotTurbo(ernieBotTurboStreamParam));
    }
}
