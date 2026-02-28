package com.studyEnglish.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.studyEnglish.entity.DateRecord;
import com.studyEnglish.entity.MemoryRecord;
import com.studyEnglish.entity.Word;
import com.studyEnglish.mapper.DateRecordMapper;
import com.studyEnglish.mapper.MemoryRecordMapper;
import com.studyEnglish.mapper.WordMapper;
import com.studyEnglish.service.IMemoryRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@Service
public class MemoryRecordServiceImpl extends ServiceImpl<MemoryRecordMapper, MemoryRecord> implements IMemoryRecordService {

    @Resource
    private WordMapper wordMapper;
    @Resource
    private MemoryRecordMapper memoryRecordMapper;
    @Resource
    private DateRecordMapper dateRecordMapper;

    //艾宾浩斯遗忘曲线
    private  int[] memoryCircle = {1,2,4,7,15};

    @Override
    public List<Word> getNewWords(Integer userId, Integer count) {
        Map<String, Integer> map = new HashMap<>();
        map.put("userId",userId);
        map.put("count",count);
        List<Integer> newWordsId = wordMapper.findNewWordId(map);
        List<Word> newWords = new ArrayList<>(count);
        for (Integer i : newWordsId){
            Word word = wordMapper.selectById(i);
            newWords.add(word);
        }
        return newWords;
    }

    @Override
    public List<Word> getReviewWords(Integer userId) {
        int reviewWordCount = memoryRecordMapper.findReviewWordCount(userId);
        List<Integer> reviewWordId =memoryRecordMapper.findReviewWordId(userId);
        List<Word> reviewWords = new ArrayList<>(reviewWordCount);
        for (Integer i : reviewWordId){
            Word word = wordMapper.selectById(i);
            reviewWords.add(word);
        }
        return reviewWords;
    }

    @Override
    public void learnNewWord(int userId, int wordId) {
        Date todayDate = DateUtil.parseDate(DateUtil.today());

        MemoryRecord newMemoryRecord = new MemoryRecord();
        newMemoryRecord.setUserId(userId);
        newMemoryRecord.setWordId(wordId);
        newMemoryRecord.setPhase(1);
        newMemoryRecord.setNextmemoryTime(DateUtil.offsetDay(todayDate,memoryCircle[0]));
        try{
            memoryRecordMapper.insert(newMemoryRecord);

            LambdaQueryWrapper<DateRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
            DateRecord dateRecord = dateRecordMapper.selectOne(lambdaQueryWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate, todayDate));
            if(dateRecord == null){
                //如果当天没有学习记录，则新增学习记录
                DateRecord newDateRecord = new DateRecord();
                newDateRecord.setUserId(userId);
                newDateRecord.setDate(todayDate);
                newDateRecord.setLearningTime(1);
                newDateRecord.setLearnCount(1);
                newDateRecord.setReviewCount(0);
                dateRecordMapper.insert(newDateRecord);
            }
            else{ //当天有学习记录则将已学习单词数+1
                dateRecord.setLearnCount(dateRecord.getLearnCount()+1);
                dateRecordMapper.update(dateRecord,lambdaQueryWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate, todayDate));
            }
        } catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //涉及多表同步更新，有异常则事务回滚
        }

    }

    @Override
    public void reviewOldWord(int userId, int wordId) {
        Date todayDate = DateUtil.parseDate(DateUtil.today());

        try{
            LambdaQueryWrapper<MemoryRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
            MemoryRecord memoryRecord = memoryRecordMapper.selectOne(lambdaQueryWrapper.eq(MemoryRecord::getUserId, userId).eq(MemoryRecord::getWordId, wordId));
            int index = memoryRecord.getPhase();
            if(index < 5){
                Date nextMemoryTime = DateUtil.offsetDay(todayDate,memoryCircle[index]);
                memoryRecord.setNextmemoryTime(nextMemoryTime);
                memoryRecord.setPhase(memoryRecord.getPhase()+1);
                memoryRecordMapper.update(memoryRecord,lambdaQueryWrapper.eq(MemoryRecord::getUserId, userId).eq(MemoryRecord::getWordId, wordId));
            }else{
                //phase = 5时，下次不用再复习了
                memoryRecord.setPhase(memoryRecord.getPhase()+1);
                memoryRecordMapper.update(memoryRecord,lambdaQueryWrapper.eq(MemoryRecord::getUserId, userId).eq(MemoryRecord::getWordId, wordId));

            }

            LambdaQueryWrapper<DateRecord> dateRecordWrapper = Wrappers.lambdaQuery();
            DateRecord dateRecord = dateRecordMapper.selectOne(dateRecordWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate, todayDate));
            if(dateRecord == null){
                //如果当天没有学习记录，则新增学习记录
                DateRecord newDateRecord = new DateRecord();
                newDateRecord.setUserId(userId);
                newDateRecord.setDate(todayDate);
                newDateRecord.setLearningTime(1);
                newDateRecord.setLearnCount(0);
                newDateRecord.setReviewCount(1);
                dateRecordMapper.insert(newDateRecord);
            }
            else{
                //当天有学习记录则将已复习单词数+1
                dateRecord.setReviewCount(dateRecord.getReviewCount()+1);
                dateRecordMapper.update(dateRecord,dateRecordWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate, todayDate));
            }
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //涉及多表同步更新，有异常则事务回滚
        }
    }

    @Override
    public void updateTodayLearningTime(int userId, int learningTime) {
        Date todayDate = DateUtil.parseDate(DateUtil.today());
        LambdaQueryWrapper<DateRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
        DateRecord dateRecord = dateRecordMapper.selectOne(lambdaQueryWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate,todayDate));

        dateRecord.setLearningTime(dateRecord.getLearningTime() + learningTime);
        dateRecordMapper.update(dateRecord,lambdaQueryWrapper.eq(DateRecord::getUserId,userId).eq(DateRecord::getDate,todayDate));

    }

    @Override
    public int getReviewCount(Integer userId) {
        int count = memoryRecordMapper.findReviewWordCount(userId);
        return count;
    }



}
