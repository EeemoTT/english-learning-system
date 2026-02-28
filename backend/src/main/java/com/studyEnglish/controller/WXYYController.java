package com.studyEnglish.controller;

import com.studyEnglish.common.Result;
import com.studyEnglish.entity.wenxinyiyan.BaiduChatMessage;
import com.studyEnglish.entity.wenxinyiyan.BaiduEventSourceListener;
import com.studyEnglish.entity.wenxinyiyan.BaiduService;
import com.studyEnglish.entity.wenxinyiyan.ErnieBotTurboStreamParam;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/8
 **/

@RestController
@RequestMapping("/wxyy")
public class WXYYController {

    @PostMapping("/AnswerType1")
    public Result onlineAnswerTogether(@RequestBody List<BaiduChatMessage> content){
        String apiKey = "NhBq3MyjFOQ745OvQ5tabyXu";
        String secretKey = "pTuNAwdFMmq8oBDJ6ElN1Df0QxoxlIQx";
        ErnieBotTurboStreamParam ernieBotTurboStreamParam = new ErnieBotTurboStreamParam();
        ernieBotTurboStreamParam.setMessages(content);
        String userId = Integer.toString( TokenUtils.getCurrentUser().getId() );
        ernieBotTurboStreamParam.setUser_id(userId);
        BaiduService baiduService = new BaiduService(apiKey,secretKey,300,300,300,null);
        return Result.success(baiduService.ernieBotTurbo(ernieBotTurboStreamParam));
    }
    @PostMapping("/AnswerType2")
    public Result onlineAnswerByStream(@RequestBody List<BaiduChatMessage> content){
        String apiKey = "NhBq3MyjFOQ745OvQ5tabyXu";
        String secretKey = "pTuNAwdFMmq8oBDJ6ElN1Df0QxoxlIQx";
        ErnieBotTurboStreamParam ernieBotTurboStreamParam = new ErnieBotTurboStreamParam();
        ernieBotTurboStreamParam.setMessages(content);
        String userId = Integer.toString( TokenUtils.getCurrentUser().getId() );
        ernieBotTurboStreamParam.setUser_id(userId);
        BaiduService baiduService = new BaiduService(apiKey,secretKey);
        baiduService.ernieBotTurboStream(ernieBotTurboStreamParam,new BaiduEventSourceListener());
        return Result.success();
    }
}
