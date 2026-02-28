package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Test;
import com.studyEnglish.service.ITestService;
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
@RequestMapping("/test")
public class TestController {
    @Resource
    private ITestService testService;

    @PostMapping
    public Boolean save(@RequestBody Test test) {
        return testService.saveOrUpdate(test);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return testService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return testService.removeById(id);
    }

    @GetMapping
    public List<Test> selectAll() {
        return testService.list();
    }

    @GetMapping("/{id}")
    public Test selectById(@PathVariable Integer id) {
        return testService.getById(id);
    }


    @GetMapping("/page")
    public Page<Test> selectPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String name,
                                  @RequestParam(defaultValue = "") String state) {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        if (!"".equals(state)) {
            queryWrapper.like("state",state);
        }
        queryWrapper.orderByDesc("id");
        return testService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

