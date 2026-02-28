package com.studyEnglish.service;

import com.studyEnglish.entity.SignIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-24
 */
public interface ISignInService extends IService<SignIn> {

    void add(SignIn signin);

    SignIn selectByUserId(Integer id);
}
