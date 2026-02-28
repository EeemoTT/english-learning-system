package com.studyEnglish.service;

import com.studyEnglish.entity.MemoryRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.studyEnglish.entity.Word;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
public interface IMemoryRecordService extends IService<MemoryRecord> {

    List<Word> getNewWords(Integer userId, Integer count);

    List<Word> getReviewWords(Integer userId);

    void learnNewWord(int userId, int wordId);

    void reviewOldWord(int userId, int wordId);

    void updateTodayLearningTime(int userId, int learningTime);

    int getReviewCount(Integer userId);

}
