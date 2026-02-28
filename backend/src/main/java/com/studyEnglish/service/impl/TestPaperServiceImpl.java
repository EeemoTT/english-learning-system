package com.studyEnglish.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.studyEnglish.entity.TestPaper;
import com.studyEnglish.mapper.TestPaperMapper;
import com.studyEnglish.service.ITestPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-01
 */
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper> implements ITestPaperService {
    @Transactional
    @Override
    public void addPaper(TestPaper testPaper) {
        remove(new UpdateWrapper<TestPaper>().eq("test_id", testPaper.getTestId()));
        save(testPaper);
    }
}
