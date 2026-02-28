package com.studyEnglish.entity.wenxinyiyan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/8
 **/
@Data
public class Token implements Serializable {
    private String access_token;
    private Integer expires_in;
    private String error;
    private String error_description;
}
