package com.studyEnglish.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.studyEnglish.common.Constants;
import com.studyEnglish.common.Result;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.enums.RoleEnum;
import com.studyEnglish.service.IAdminService;
import com.studyEnglish.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eeemot_t
 * @Description TODO 登录/注册
 * @date 2024/2/7
 **/
@RestController
public class EnterController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody AccountDTO accountDTO){
        if (StrUtil.isEmpty(accountDTO.getUsername()) || ObjectUtil.isEmpty(accountDTO.getPassword())
                || ObjectUtil.isEmpty(accountDTO.getRole())
        ) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        if(RoleEnum.ADMIN.name().equals(accountDTO.getRole())) {
            accountDTO = adminService.login(accountDTO);
        }
        if(RoleEnum.USER.name().equals(accountDTO.getRole())) {
          accountDTO = userService.login(accountDTO);
        }



        return Result.success(accountDTO);
    }

}
