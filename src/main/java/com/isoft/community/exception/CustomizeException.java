package com.isoft.community.exception;

import lombok.Data;

@Data
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }      //通过在外层拿到该方法
}
