package com.isoft.community.exception;

import lombok.Data;

@Data
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }      //通过在外层拿到该方法
}
