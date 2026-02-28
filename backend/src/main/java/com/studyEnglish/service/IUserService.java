package com.studyEnglish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.dto.UserPasswordDTO;
import com.studyEnglish.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-06
 */
public interface IUserService extends IService<User> {

    AccountDTO login(AccountDTO accountDTO);

    User register(AccountDTO accountDTO);

    void updatePassword(UserPasswordDTO accountDTO);
}
