package com.studyEnglish.controller;


import cn.hutool.core.collection.CollUtil;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Word;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.service.IMemoryRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@RestController
@RequestMapping("/memory")
public class MemoryRecordController {

    @Resource
    private IMemoryRecordService memoryRecordService;


    //获取新单词
    @GetMapping("/newWords")
    public Result getNewWords(@RequestParam Integer userId,
                              @RequestParam Integer count) {
        List<Word> newWords = memoryRecordService.getNewWords(userId, count);
        if(CollUtil.isEmpty(newWords)) {
            throw new ServiceException("-1", "您已经学习完该单词书！");
        }
        return Result.success(newWords);
    }
    //获取复习单词
    @GetMapping("/reviewWords")
    public Result getReviewWords(@RequestParam Integer userId) {
        List<Word> reviewWords = memoryRecordService.getReviewWords(userId);
        if(CollUtil.isEmpty(reviewWords)) {
            throw new ServiceException("-1", "您今天已经完成复习了！");
        }
        return Result.success(reviewWords);
    }

    //学习新单词,添加记忆表
    @PostMapping("/newWord")
    public Result learnNewWord(@RequestBody Map<String,Integer> map){
        int userId = map.get("userId");
        int wordId = map.get("wordId");
        memoryRecordService.learnNewWord(userId,wordId);
        return Result.success();

    }
    //复习单词，更新记忆表
    @PutMapping("/reviewWord")
    public Result updateReviewWord(@RequestBody Map<String,Integer> map){
        int userId = map.get("userId");
        int wordId = map.get("wordId");
        memoryRecordService.reviewOldWord(userId,wordId);
        return Result.success();
    }
    //更新今日学习时长
    @PutMapping("/learningTime")
    public Result updateLearningTime(@RequestBody Map<String,Integer> map){
        int userId = map.get("userId");
        int learningTime = map.get("learningTime");
        memoryRecordService.updateTodayLearningTime(userId,learningTime);
        return Result.success();
    }
}

