package com.studyEnglish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.entity.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-08
 */
public interface IAdminService extends IService<Admin> {

    AccountDTO login(AccountDTO accountDTO);
}
