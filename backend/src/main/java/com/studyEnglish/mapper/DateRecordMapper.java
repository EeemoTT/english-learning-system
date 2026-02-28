package com.studyEnglish.mapper;

import com.studyEnglish.entity.DateRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@Mapper
public interface DateRecordMapper extends BaseMapper<DateRecord> {

    List<DateRecord> findLatestSevenDaysData(Integer userId);
}
