package com.studyEnglish.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.DateRecord;
import com.studyEnglish.entity.MemoryRecord;
import com.studyEnglish.entity.Target;
import com.studyEnglish.entity.Word;
import com.studyEnglish.service.IDateRecordService;
import com.studyEnglish.service.IMemoryRecordService;
import com.studyEnglish.service.ITargetService;
import com.studyEnglish.service.IWordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/6
 **/
@RestController
@RequestMapping("/statisticsForWord")
public class StatisticWordController {
    @Resource
    private IWordService wordService;
    @Resource
    private IMemoryRecordService memoryRecordService;
    @Resource
    private IDateRecordService dateRecordService;
    @Resource
    private ITargetService targetService;

    //词书单词数
    @GetMapping("/wordCount")
    public Result getTotalWordCount(@RequestParam String book){
        LambdaQueryWrapper<Word> lambdaQueryWrapper = Wrappers.lambdaQuery();
        long count = wordService.count(lambdaQueryWrapper.eq(Word::getBook,book));
        return Result.success(count);
    }

    //已学习总量
    @GetMapping("learnedCount")
    public Result getLearnedCount(@RequestParam Integer userId){
        LambdaQueryWrapper<MemoryRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
        long count = memoryRecordService.count(lambdaQueryWrapper.eq(MemoryRecord::getUserId,userId));
        return Result.success(count);
    }

   //今日复习量
    @GetMapping("/reviewCount")
    public Result getReviewCount(@RequestParam Integer userId){
        return Result.success(memoryRecordService.getReviewCount(userId));
    }


    //学习词书
    @GetMapping("book")
    public Result getBook(@RequestParam Integer userId){
        LambdaQueryWrapper<Target> lambdaQueryWrapper = Wrappers.lambdaQuery();
        return Result.success(targetService.getOne(lambdaQueryWrapper.eq(Target::getUserId,userId)));
    }

    //学习各阶段百分比
    @GetMapping("/getPhasePie")
    public Result getPhasePie(@RequestParam Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        LambdaQueryWrapper<MemoryRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
        List<MemoryRecord> typeList = memoryRecordService.list(lambdaQueryWrapper.eq(MemoryRecord::getUserId, userId));
        Map<Integer,Long> collect = typeList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getPhase()))
                .collect(Collectors.groupingBy(MemoryRecord::getPhase, Collectors.counting()));
        for (Integer key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",
                    key.equals(1) ? "认识" :
                    key.equals(2) ? "了解" :
                    key.equals(3) ? "熟悉" :
                    key.equals(4) ? "掌握" :
                    key.equals(5) ? "牢记" :
                    "烂熟于心");
            map.put("value", collect.get(key));
            list.add(map);
        }
        typeList.stream().close();
        resultMap.put("text", "用户学习单词阶段百分比");
        resultMap.put("subText", "统计维度：学习阶段");
        resultMap.put("name", "占比数据");
        resultMap.put("data", list);

        return Result.success(resultMap);
    }

    //最近七天学习时间
    @GetMapping("/getStudyTime7")
    public Result getStudyTime7(@RequestParam Integer userId){
        List<DateRecord> records = dateRecordService.findLatestSevenDaysData(userId);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();
        List<Date> list = records.stream().map(DateRecord::getDate).collect(Collectors.toList());
        List<Integer> list1 =records.stream().map(DateRecord::getLearningTime).collect(Collectors.toList());
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd");
        for (Date item : list){
            xList.add(ft.format(item));
        }
        for (Integer item : list1){
            yList.add((long)(item));
        }

        records.stream().close();
        resultMap.put("text", "用户一周内学习时间统计");
        resultMap.put("subText", "统计维度：每日学习时间");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);

        return Result.success(resultMap);

    }
}
