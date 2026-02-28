package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Course;
import com.studyEnglish.service.ICourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-19
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private ICourseService courseService;

    @PostMapping
    public Boolean save(@RequestBody Course course) {
        return courseService.saveOrUpdate(course);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return courseService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return courseService.removeById(id);
    }

    @GetMapping
    public List<Course> selectAll() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public Course selectById(@PathVariable Integer id) {
        return courseService.getById(id);
    }

    @GetMapping("/page")
    public Page<Course> selectPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        return courseService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    @GetMapping("/getRecommend")
    public Result getRecommend() {
        Course course = courseService.getRecommend();
        return Result.success(course);
    }

    @GetMapping("/selectTop6")
    public Result selectTop6() {
        List<Course> list = courseService.selectTop6();
        return Result.success(list);
    }




}

