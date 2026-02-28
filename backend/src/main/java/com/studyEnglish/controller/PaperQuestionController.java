package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.entity.PaperQuestion;
import com.studyEnglish.entity.Question;
import com.studyEnglish.service.IPaperQuestionService;
import com.studyEnglish.service.IQuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *`
 * @author EeemoT^T
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/paperQuestion")
public class PaperQuestionController {
    @Resource
    private IPaperQuestionService paperQuestionService;

    @PostMapping
    public Boolean save(@RequestBody PaperQuestion paperQuestion) {
        return paperQuestionService.saveOrUpdate(paperQuestion);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return paperQuestionService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return paperQuestionService.removeById(id);
    }

    @GetMapping
    public List<PaperQuestion> selectAll() {
        return paperQuestionService.list();
    }

    @GetMapping("/{id}")
    public PaperQuestion selectById(@PathVariable Integer id) {
        return paperQuestionService.getById(id);
    }
}

