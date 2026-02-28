package com.studyEnglish.service;

import com.studyEnglish.entity.PaperQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.studyEnglish.entity.Question;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-29
 */
public interface IPaperQuestionService extends IService<PaperQuestion> {

    List<Question> selectQuestion(Integer paperId);
}
