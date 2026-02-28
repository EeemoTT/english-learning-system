package com.studyEnglish.dto;

import lombok.Data;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/18
 **/
@Data
public class UserPasswordDTO {
    private Integer userId;
    private String password;
    private String phone;
    private String newPassword;
}
