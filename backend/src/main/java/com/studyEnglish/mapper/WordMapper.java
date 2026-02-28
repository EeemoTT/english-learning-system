package com.studyEnglish.mapper;

import com.studyEnglish.entity.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@Mapper
public interface WordMapper extends BaseMapper<Word> {

    List<Integer> findNewWordId(Map<String, Integer> map);


}
