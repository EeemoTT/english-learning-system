package com.studyEnglish.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.studyEnglish.common.Constants;
import com.studyEnglish.common.Result;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.dto.UserPasswordDTO;
import com.studyEnglish.enums.RoleEnum;
import com.studyEnglish.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/8
 **/
@RestController
public class RegisterController {
    @Autowired
    private IUserService userService;
    @PostMapping("/register")
    public Result register(@RequestBody AccountDTO accountDTO){
        if (StrUtil.isBlank(accountDTO.getUsername()) || StrUtil.isBlank(accountDTO.getPassword())
        || ObjectUtil.isEmpty(accountDTO.getRole())) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        if(RoleEnum.USER.name().equals(accountDTO.getRole())){
            userService.register(accountDTO);
        }
        return Result.success();
    }

    @PostMapping("/updatePW")
    public Result updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {

        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }
}
