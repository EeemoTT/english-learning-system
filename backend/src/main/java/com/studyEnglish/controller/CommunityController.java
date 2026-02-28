package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Community;
import com.studyEnglish.service.ICommunityService;
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
 * @since 2024-02-26
 */
@RestController
@RequestMapping("/community")
public class CommunityController {
    @Resource
    private ICommunityService communityService;

    @PostMapping
    public Boolean save(@RequestBody Community community) {
        if(community.getId() == null){
            community.setUserId(TokenUtils.getCurrentUser().getId());
            community.setUsername(TokenUtils.getCurrentUser().getNickname());
        }
        return communityService.saveOrUpdate(community);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return communityService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(communityService.removeById(id));
    }

    @GetMapping
    public List<Community> selectAll() {
        return communityService.list();
    }

    @GetMapping("/{id}")
    public Community selectById(@PathVariable Integer id) {
        return communityService.getById(id);
    }

    @GetMapping("/page")
    public Page<Community> selectPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String userId) {
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        if (!"".equals(userId)) {
            queryWrapper.eq("user_id",userId);
        }
        queryWrapper.orderByDesc("id");
        return communityService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }
}

