package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.entity.Notice;
import com.studyEnglish.service.INoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-18
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private INoticeService noticeService;

    @PostMapping
    public Boolean save(@RequestBody Notice notice) {
        return noticeService.saveOrUpdate(notice);
    }

    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return noticeService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return noticeService.removeById(id);
    }

    @GetMapping
    public List<Notice> selectAll() {
        return noticeService.list();
    }

    @GetMapping("/{id}")
    public Notice selectById(@PathVariable Integer id) {
        return noticeService.getById(id);
    }

    @GetMapping("/page")
    public Page<Notice> selectPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String title) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if (!"".equals(title)) {
            queryWrapper.like("title",title);
        }
        queryWrapper.orderByDesc("id");
        return noticeService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }




}

