package com.studyEnglish.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyEnglish.entity.Course;
import com.studyEnglish.mapper.CourseMapper;
import com.studyEnglish.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-19
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Course getRecommend() {
        return courseMapper.getRecommend();
    }

    @Override
    public List<Course> selectTop6() {
        return courseMapper.selectTop6();
    }
}
