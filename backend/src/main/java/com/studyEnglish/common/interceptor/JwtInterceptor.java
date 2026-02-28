package com.studyEnglish.common.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.studyEnglish.common.Constants;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.enums.RoleEnum;
import com.studyEnglish.exception.ServiceException;
import com.studyEnglish.service.IAdminService;
import com.studyEnglish.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/10
 **/

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取user id
        //Admin admin = null;
        AccountDTO accountdto = null;

        try {
            String id = JWT.decode(token).getAudience().get(0);
            String role = id.split("-")[1];
            if (RoleEnum.ADMIN.name().equals(role)) {
                accountdto = adminService.getById(id);
                // BeanUtils.copyProperties(admin, account);
            }
            if (RoleEnum.USER.name().equals(role)) {
                accountdto = userService.getById(id);
                // BeanUtils.copyProperties(admin, account);
            }
        }catch (JWTDecodeException jd) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        //查询数据库

        if (ObjectUtil.isNull(accountdto)) {
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        //User user = userService.getById(id);
        //if (user == null) {
        //    throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        //}
        //用户密码加签验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(accountdto.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException jv) {
            throw new ServiceException(Constants.CODE_401,"token验证失败 ，请重新登录");
        }
        return true ;
    }
}
