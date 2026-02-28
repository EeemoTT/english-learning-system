package com.studyEnglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studyEnglish.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-19
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select * from course where recommend = '是'")
    Course getRecommend();

    @Select("select * from course where recommend = '否' order by id desc limit 6")
    List<Course> selectTop6();
}
