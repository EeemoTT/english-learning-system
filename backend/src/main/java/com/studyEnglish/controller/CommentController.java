package com.studyEnglish.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Comment;
import com.studyEnglish.service.ICommentService;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @PostMapping
    public Boolean save(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            comment.setUserId(TokenUtils.getCurrentUser().getId());

            if( comment.getPid() != null) {
                Integer pid = comment.getPid();
                Comment pComment = commentService.getById(pid);
                if (pComment.getOid() != null) {
                    comment.setOid(pComment.getOid());
                } else {
                    comment.setOid(comment.getPid());
                }
            }

        }
        return commentService.saveOrUpdate(comment);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return commentService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return commentService.removeById(id);
    }

    @GetMapping
    public List<Comment> selectAll() {
        return commentService.list();
    }

    @GetMapping("/tree/{communityId}")
    public Result selectTree(@PathVariable Integer communityId) {
        //select all comments
        List<Comment> communityComments = commentService.fineTree(communityId);
        //select origin comments
        List<Comment> originList = communityComments.stream().filter(comment ->  comment.getOid() == null).collect(Collectors.toList());
        //设置子节点
        for (Comment origin: originList) {
            List<Comment> comments = communityComments.stream().filter(comment -> origin.getId().equals(comment.getOid())).collect(Collectors.toList());
            comments.forEach(comment -> {
                communityComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst().ifPresent((v ->{
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                }));
            });
            origin.setChildren(comments);
        }

        communityComments.stream().close();

        return Result.success(originList);
    }

    @GetMapping("/{id}")
    public Comment selectById(@PathVariable Integer id) {
        return commentService.getById(id);
    }

    @GetMapping("/page")
    public Page<Comment> selectPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        return commentService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }
}

