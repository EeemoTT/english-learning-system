package com.studyEnglish.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.dto.PaperByHandDTO;
import com.studyEnglish.dto.PaperDTO;
import com.studyEnglish.entity.Paper;
import com.studyEnglish.entity.PaperQuestion;
import com.studyEnglish.entity.Question;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.service.IPaperQuestionService;
import com.studyEnglish.service.IPaperService;
import com.studyEnglish.service.IQuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private IPaperService paperService;
    @Resource
    private IQuestionService questionService;

    @Resource
    private IPaperQuestionService paperQuestionService;

    @PostMapping
    public Boolean save(@RequestBody Paper paper) {
        return paperService.saveOrUpdate(paper);
    }


    @PostMapping("takePaperByHand")
    public Result takePaperByHand(@RequestBody PaperByHandDTO paperByHandDTO) {
        //移除老的试卷
        UpdateWrapper<PaperQuestion> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", paperByHandDTO.getPaperId());
        paperQuestionService.remove(updateWrapper);
        if(CollUtil.isEmpty(paperByHandDTO.getHandleQuestionIds())) {
            throw new ServiceException("-1", "题目数量不足");
        }
        List<Integer> handleQuestionIds = paperByHandDTO.getHandleQuestionIds();
        List<PaperQuestion> list = new ArrayList<>();
        for (Integer handleQuestionId : handleQuestionIds) {
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setPaperId(paperByHandDTO.getPaperId());
            paperQuestion.setQuestionId(handleQuestionId);
            list.add(paperQuestion);
        }
        paperQuestionService.saveBatch(list);
        return Result.success();
    }


    @PostMapping("takePaper")
    public Result takePaper(@RequestBody PaperDTO paperDTO) {
        //QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        //移除老的试卷
        UpdateWrapper<PaperQuestion> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", paperDTO.getPaperId());
        paperQuestionService.remove(updateWrapper);
        //查出所有
        List<Question> questionList = questionService.list();
        //type分类
        List<Question> type1List = questionList.stream().filter(question -> question.getType() == 1 ).collect(Collectors.toList());
        List<Question> type2List = questionList.stream().filter(question -> question.getType() == 2 ).collect(Collectors.toList());
        List<Question> type3List = questionList.stream().filter(question -> question.getType() == 3 ).collect(Collectors.toList());
        if(type1List.size() < paperDTO.getType1()) {
            throw new ServiceException("-1", "选择题数量不足");
        }
        if(type2List.size() < paperDTO.getType2()) {
            throw new ServiceException("-1", "判断题数量不足");
        }
        if(type3List.size() < paperDTO.getType3()) {
            throw new ServiceException("-1", "问答题数量不足");
        }
        //开始随机组卷
        List<PaperQuestion> paperQuestion = getPaperQuestion(type1List.size(),paperDTO.getType1(),type1List, paperDTO.getPaperId());
        paperQuestion.addAll(getPaperQuestion(type2List.size(),paperDTO.getType1(),type2List, paperDTO.getPaperId()));
        paperQuestion.addAll(getPaperQuestion(type3List.size(),paperDTO.getType1(),type3List, paperDTO.getPaperId()));
        paperQuestionService.saveBatch(paperQuestion);
        questionList.stream().close();
        return Result.success();
    }

    //获取试卷和题目关联关系list方法封装
    private List<PaperQuestion> getPaperQuestion(int questionSize, int paperQuestionSize, List<Question> source, Integer paperId) {
        List<Integer> typeRandomList = getEleList(questionSize, paperQuestionSize);
        List<PaperQuestion> list = new ArrayList<>();
        for (Integer index : typeRandomList) {
            Question question = source.get(index);
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setPaperId(paperId);
            paperQuestion.setQuestionId(question.getId());
            list.add(paperQuestion);
        }
        return list;
    }

    //获取随机数
    private List<Integer> getEleList(int sourceSize, int resultSize) {
        List<Integer> list = CollUtil.newArrayList();
        for(int i = 0; i < sourceSize; i++) {
            list.add(i);
        }
        return RandomUtil.randomEleList(list,resultSize);
    }


    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return paperService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return paperService.removeById(id);
    }

    @GetMapping("/view/{paperId}")
    public Result view(@PathVariable Integer paperId){
        //QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("paper_id", paperId);
        List<Question> list = paperQuestionService.selectQuestion(paperId);
        return Result.success(list);
    }

    @GetMapping
    public List<Paper> selectAll() {
        return paperService.list();
    }

    @GetMapping("/{id}")
    public Paper selectById(@PathVariable Integer id) {
        return paperService.getById(id);
    }


    @GetMapping("/page")
    public Page<Paper> selectPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        return paperService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

