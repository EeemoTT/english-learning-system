package com.studyEnglish.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyEnglish.common.Constants;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.dto.UserPasswordDTO;
import com.studyEnglish.entity.User;
import com.studyEnglish.enums.RoleEnum;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.mapper.UserMapper;
import com.studyEnglish.service.IUserService;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private UserMapper userMapper;

    private static final Log LOG = Log.get();

    @Override
    public AccountDTO login(AccountDTO accountDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", accountDTO.getUsername());
        queryWrapper.eq("password", accountDTO.getPassword());
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one != null){
            BeanUtils.copyProperties(one, accountDTO);
           String tokenData = one.getId() + "-" + RoleEnum.USER.name();
           String token = TokenUtils.createToken(tokenData,one.getPassword());
           accountDTO.setToken(token);
            return accountDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(AccountDTO accountDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", accountDTO.getUsername());
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one == null){
            one = new User();
            BeanUtils.copyProperties(accountDTO,one);
            save(one); //储存
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已经存在,请重新设置账号");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userPasswordDTO.getUserId());
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (ObjectUtil.isNull(one)) {
            throw new ServiceException(Constants.CODE_500,"用户不存在");
        }
        one.setPassword(userPasswordDTO.getNewPassword());
        updateById(one);
        //System.out.println(one);
    }
}
