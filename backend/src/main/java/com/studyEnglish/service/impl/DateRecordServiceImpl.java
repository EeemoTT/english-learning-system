package com.studyEnglish.service.impl;

import com.studyEnglish.entity.DateRecord;
import com.studyEnglish.mapper.DateRecordMapper;
import com.studyEnglish.service.IDateRecordService;
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
 * @since 2024-03-05
 */
@Service
public class DateRecordServiceImpl extends ServiceImpl<DateRecordMapper, DateRecord> implements IDateRecordService {

    @Resource
    private DateRecordMapper dateRecordMapper;
    @Override
    public List<DateRecord> findLatestSevenDaysData(Integer userId) {

        return dateRecordMapper.findLatestSevenDaysData(userId);
    }
}
