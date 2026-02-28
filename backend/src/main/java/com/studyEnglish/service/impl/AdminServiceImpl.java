package com.studyEnglish.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyEnglish.common.Constants;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.entity.Admin;
import com.studyEnglish.enums.RoleEnum;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.mapper.AdminMapper;
import com.studyEnglish.service.IAdminService;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    private static final Log LOG = Log.get();
    @Override
    public AccountDTO login(AccountDTO accountDTO) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", accountDTO.getUsername());
        queryWrapper.eq("password", accountDTO.getPassword());
        Admin one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one != null){
            BeanUtils.copyProperties(one, accountDTO);
            String tokenData = one.getId()+ "-" + RoleEnum.ADMIN.name();
            String token = TokenUtils.createToken(tokenData,one.getPassword());
            accountDTO.setToken(token);
            return accountDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }
}
