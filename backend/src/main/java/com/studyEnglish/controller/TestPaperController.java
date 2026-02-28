package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Test;
import com.studyEnglish.entity.TestPaper;
import com.studyEnglish.service.ITestPaperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-01
 */
@RestController
@RequestMapping("/testPaper")
public class TestPaperController {
    @Resource
    private ITestPaperService testPaperService;

    @PostMapping
    public Result save(@RequestBody TestPaper testPaper) {
        testPaperService.addPaper(testPaper);
        return Result.success();
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return testPaperService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return testPaperService.removeById(id);
    }

    @GetMapping("test/{testId}")
    public Result findDetail(@PathVariable Integer testId) {
        return Result.success(testPaperService.getOne(new QueryWrapper<TestPaper>().eq("test_id", testId)));
    }

    @GetMapping
    public List<TestPaper> selectAll() {
        return testPaperService.list();
    }

    @GetMapping("/{id}")
    public TestPaper selectById(@PathVariable Integer id) {
        return testPaperService.getById(id);
    }

}

