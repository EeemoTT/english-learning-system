package com.studyEnglish.service;

import com.studyEnglish.entity.TestPaper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-01
 */
public interface ITestPaperService extends IService<TestPaper> {

    void addPaper(TestPaper testPaper);
}
