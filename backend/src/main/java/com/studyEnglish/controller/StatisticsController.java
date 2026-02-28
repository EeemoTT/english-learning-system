package com.studyEnglish.controller;

import cn.hutool.core.util.ObjectUtil;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Course;
import com.studyEnglish.service.ICourseService;
import com.studyEnglish.service.IQuestionService;
import com.studyEnglish.service.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/22
 **/
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IWordService wordService;
    @Autowired
    private IQuestionService questionService;


    @GetMapping("/getData")
    public Result getData() {
        List<Long> list =new ArrayList<>();
        list.add((long)courseService.list().size());
        list.add((long)wordService.list().size());
        list.add((long)questionService.list().size());

        return Result.success(list);
    }

    @GetMapping("/getCoursePie")
    public Result getCoursePie() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        List<Course> typeList = courseService.list();
        Map<String,Long> collect = typeList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getType()))
                .collect(Collectors.groupingBy(Course::getType, Collectors.counting()));
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "VIDEO".equals(key) ? "视频课程" : "图文课程");
            map.put("value", collect.get(key));
            list.add(map);
        }
        typeList.stream().close();
        resultMap.put("text", "平台课程分类统计图");
        resultMap.put("subText", "统计维度：课程分类");
        resultMap.put("name", "占比数据");
        resultMap.put("data", list);

        return Result.success(resultMap);
    }


}
