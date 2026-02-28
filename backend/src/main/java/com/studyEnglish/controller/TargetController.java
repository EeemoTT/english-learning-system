package com.studyEnglish.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.*;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.service.IDateRecordService;
import com.studyEnglish.service.IMemoryRecordService;
import com.studyEnglish.service.ITargetService;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@RestController
@RequestMapping("/target")
public class TargetController {
    @Resource
    private ITargetService targetService;
    @Resource
    private IMemoryRecordService memoryRecordService;
    @Resource
    private IDateRecordService dateRecordService;

    @GetMapping("/userId/{userId}")
    public Result selectById(@PathVariable Integer userId) {
        QueryWrapper<Target> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return Result.success(targetService.getOne(queryWrapper));}

    @PostMapping
    public Result choosing(@RequestBody Target target) {
        if (target.getId() == null) {
            target.setUserId(TokenUtils.getCurrentUser().getId());
        }
            List<Target> list = targetService.list(new QueryWrapper<Target>()
                    .eq("user_id" ,TokenUtils.getCurrentUser().getId()));
            if(CollUtil.isNotEmpty(list)) {
                throw new ServiceException("-1", "您已经选择了词书进行学习！");
            }

        targetService.saveOrUpdate(target);
        return Result.success();
    }

    @PostMapping("/{userId}")
    public Result restartLearning(@PathVariable Integer userId) {
        List<Target> list = targetService.list(new QueryWrapper<Target>().eq("user_id", userId)
                .eq("user_id" ,TokenUtils.getCurrentUser().getId()));
        if(CollUtil.isEmpty(list)) {
            throw new ServiceException("-1", "您还未学习！");
        }
        QueryWrapper<Target> queryWrapper = new QueryWrapper<>();
        QueryWrapper<MemoryRecord> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<DateRecord> queryWrapper2 = new QueryWrapper<>();
        queryWrapper.eq("user_id" ,TokenUtils.getCurrentUser().getId());
        queryWrapper1.eq("user_id" ,TokenUtils.getCurrentUser().getId());
        queryWrapper2.eq("user_id" ,TokenUtils.getCurrentUser().getId());
        targetService.remove(queryWrapper);
        memoryRecordService.remove(queryWrapper1);
        dateRecordService.remove(queryWrapper2);
        return Result.success();
    }
}

