package com.studyEnglish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studyEnglish.entity.Comment;
import com.studyEnglish.mapper.CommentMapper;
import com.studyEnglish.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> fineTree(Integer communityId) {
        return commentMapper.findTree(communityId);}
}
