package com.studyEnglish.service.impl;

import com.studyEnglish.entity.PaperQuestion;
import com.studyEnglish.entity.Question;
import com.studyEnglish.mapper.PaperQuestionMapper;
import com.studyEnglish.service.IPaperQuestionService;
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
 * @since 2024-02-29
 */
@Service
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionMapper, PaperQuestion> implements IPaperQuestionService {

    @Resource
    private PaperQuestionMapper paperQuestionMapper;

    @Override
    public List<Question> selectQuestion(Integer paperId) {
        return paperQuestionMapper.selectQuestion(paperId);
    }
}
