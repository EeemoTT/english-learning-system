package com.studyEnglish.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/7
 **/
@Data
public class AccountDTO {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;

    @TableField(exist = false)
    private String token;


}
