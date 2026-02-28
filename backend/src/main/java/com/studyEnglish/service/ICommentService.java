package com.studyEnglish.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studyEnglish.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-27
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> fineTree(Integer communityId);
}
