package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Admin;
import com.studyEnglish.service.IAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-08
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IAdminService adminService;

    @PostMapping
    public Boolean save(@RequestBody Admin admin) {
        return adminService.saveOrUpdate(admin);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return adminService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return adminService.removeById(id);
    }

    @GetMapping
    public List<Admin> selectAll() {
        return adminService.list();
    }

    @GetMapping("/{id}")
    public Admin selectById(@PathVariable Integer id) {
        return adminService.getById(id);
    }

    @GetMapping("/username/{username}")
    public Result selectById(@PathVariable String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(adminService.getOne(queryWrapper));
    }

    @GetMapping("/page")
    public Page<Admin> selectPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username",username);
        }
        queryWrapper.orderByDesc("id");
        return adminService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }




}

