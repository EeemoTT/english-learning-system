package com.studyEnglish.mapper;

import com.studyEnglish.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-27
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.* ,u.nickname,u.avatar from comment c left join user u on c.user_id = u.id" +
            " where c.community_id = #{communityId} order by id desc")
    List<Comment> findTree(@Param("communityId") Integer communityId);
}
