package com.studyEnglish.exception;

import lombok.Getter;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/8
 **/
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code = code;
    }
}
