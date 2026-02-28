package com.studyEnglish.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.studyEnglish.common.Constants;
import com.studyEnglish.entity.SignIn;
import com.studyEnglish.entity.User;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.mapper.SignInMapper;
import com.studyEnglish.mapper.UserMapper;
import com.studyEnglish.service.ISignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-24
 */
@Service
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {
    @Autowired
    private SignInMapper signInMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public void add(SignIn signin) {

        // 先获取今天的日期
        String day = DateUtil.format(new Date(), "yyyy-MM-dd");

        // 判断一下该用户在当天有没有签过到
        SignIn dbSignin = signInMapper.selectByUserIdAndDay(signin.getUserId(), day);
        if (ObjectUtil.isNotEmpty(dbSignin)) {
            throw new ServiceException(Constants.CODE_500,"您今天已经签过到了");
        }

        // 查询用户有没有签到记录
        SignIn signTmp = signInMapper.selectByUserId(signin.getUserId());

        if (ObjectUtil.isNotEmpty(signTmp)) {
            // 把签到数据更新成当前时间和当前日期
            signTmp.setTime(DateUtil.now());
            signTmp.setDay(day);
            signInMapper.updateById(signTmp);
        } else {
            signin.setTime(DateUtil.now());
            signin.setDay(day);
            signInMapper.insert(signin);
        }
        // 签到成功后，登录天数+1
        User user = userMapper.selectById(signin.getUserId());
        user.setScore(user.getScore() + 1);
        userMapper.updateById(user);

    }

    @Override
    public SignIn selectByUserId(Integer userId) {
        return signInMapper.selectByUserId(userId);
    }
}
