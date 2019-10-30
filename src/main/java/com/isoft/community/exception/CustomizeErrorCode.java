package com.isoft.community.exception;

//枚举既可以使用变量，也可以使用构造方法
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题不在了，要不换一个问题试一试？");

    //复写ICustomizeErrorCode的方法，传回QUESTION_NOT_FOUND
    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
