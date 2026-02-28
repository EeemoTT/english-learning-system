package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Question;
import com.studyEnglish.service.IQuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-28
 */
@RestController
@RequestMapping("//question")
public class QuestionController {
    @Resource
    private IQuestionService questionService;

    @PostMapping
    public Boolean save(@RequestBody Question question) {
        return questionService.saveOrUpdate(question);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return questionService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return questionService.removeById(id);
    }

    @GetMapping
    public List<Question> selectAll() {
        return questionService.list();
    }

    @GetMapping("/{id}")
    public Question selectById(@PathVariable Integer id) {
        return questionService.getById(id);
    }


    @GetMapping("/page")
    public Page<Question> selectPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") Integer type) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        if (type != null) {
            queryWrapper.eq("type",type);
        }
        queryWrapper.orderByDesc("id");
        return questionService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

