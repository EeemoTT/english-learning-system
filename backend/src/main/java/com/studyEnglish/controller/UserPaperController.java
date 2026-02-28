package com.studyEnglish.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.UserPaper;
import com.studyEnglish.service.IUserPaperService;
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
 * @since 2024-03-01
 */
@RestController
@RequestMapping("/userPaper")
public class UserPaperController {
    @Resource
    private IUserPaperService userPaperService;

    @PostMapping
    public Result save(@RequestBody UserPaper userPaper) {
        if(userPaper.getId() == null) {
            userPaper.setTime(DateUtil.now());
            userPaper.setUserId(TokenUtils.getCurrentUser().getId());
        }
        userPaperService.saveOrUpdate(userPaper);
        return Result.success();
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return userPaperService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userPaperService.removeById(id);
    }

    @GetMapping
    public List<UserPaper> selectAll() {
        return userPaperService.list();
    }

    @GetMapping("/{id}")
    public UserPaper selectById(@PathVariable Integer id) {
        return userPaperService.getById(id);
    }

    @GetMapping("/page")
    public Page<UserPaper> selectPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String name,
                                  @RequestParam(defaultValue = "") String userId) {
        QueryWrapper<UserPaper> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        if (!"".equals(userId)) {
            queryWrapper.eq("user_id",userId);
        }
        queryWrapper.orderByDesc("id");
        return userPaperService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }


}

