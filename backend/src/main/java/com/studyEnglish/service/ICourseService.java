package com.studyEnglish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyEnglish.entity.Course;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-19
 */
public interface ICourseService extends IService<Course> {

    Course getRecommend();

    List<Course> selectTop6();
}
