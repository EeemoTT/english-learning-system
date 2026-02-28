package com.studyEnglish.mapper;

import com.studyEnglish.entity.SignIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-24
 */
@Mapper
public interface SignInMapper extends BaseMapper<SignIn> {
    @Select("select * from sign_in where user_id = #{userId}")
    SignIn selectByUserId(Integer userId);

    @Select("select * from sign_in where user_id = #{userId} and day = #{day}")
    SignIn selectByUserIdAndDay(@Param("userId") Integer userId, @Param("day") String day);
}
