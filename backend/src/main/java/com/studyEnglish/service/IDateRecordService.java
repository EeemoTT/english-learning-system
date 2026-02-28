package com.studyEnglish.service;

import com.studyEnglish.entity.DateRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
public interface IDateRecordService extends IService<DateRecord> {

    List<DateRecord> findLatestSevenDaysData(Integer userId);
}
