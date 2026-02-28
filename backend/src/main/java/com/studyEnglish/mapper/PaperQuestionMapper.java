package com.studyEnglish.mapper;

import com.studyEnglish.entity.PaperQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studyEnglish.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-29
 */
@Mapper
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {

    List<Question> selectQuestion(Integer paperId);
}
