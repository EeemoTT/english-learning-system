package com.studyEnglish.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Course;
import com.studyEnglish.entity.Favor;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.service.IFavorService;
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
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/favor")
public class FavorController {

    @Resource
    private IFavorService favorService;

    @PostMapping("/save")
    public Result save(@RequestBody Favor favor) {
        if(favor.getId() == null) {
            favor.setUserId(TokenUtils.getCurrentUser().getId());
            List<Favor> list = favorService.list(new QueryWrapper<Favor>().eq("course_id", favor.getCourseId())
                    .eq("user_id" ,TokenUtils.getCurrentUser().getId()));
            if(CollUtil.isNotEmpty(list)) {
                throw new ServiceException("-1", "您已经收藏！");
            }

        }
        favorService.saveOrUpdate(favor);
        return Result.success();
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return favorService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return favorService.removeById(id);
    }

    @PostMapping("/del")
    public Result deleteByCourseIdAndUserId(@RequestBody Favor favor) {
        List<Favor> list = favorService.list(new QueryWrapper<Favor>().eq("course_id", favor.getCourseId())
                .eq("user_id" ,TokenUtils.getCurrentUser().getId()));
        if(CollUtil.isEmpty(list)) {
            throw new ServiceException("-1", "您还未收藏！");
        }

        QueryWrapper<Favor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", favor.getCourseId());
        queryWrapper.eq("user_id" ,TokenUtils.getCurrentUser().getId());

        return Result.success(favorService.remove(queryWrapper));
    }

    @GetMapping
    public Result selectAll() {
        return Result.success(favorService.list());
    }

    @GetMapping("/{id}")
    public Favor selectById(@PathVariable Integer id) {
        return favorService.getById(id);
    }

    @GetMapping("/page")
    public Page<Favor> selectPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "") String userId) {
        QueryWrapper<Favor> queryWrapper = new QueryWrapper<>();
        if (!"".equals(userId)) {
            queryWrapper.eq("user_id",userId);
        }
        queryWrapper.orderByDesc("id");
        return favorService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

