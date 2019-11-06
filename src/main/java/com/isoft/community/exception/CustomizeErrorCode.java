package com.isoft.community.exception;

//枚举既可以使用变量，也可以使用构造方法
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换一个问题试一试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何一个问题或评论进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论，请先进行登录！"),
    SYS_ERROR(2004,"服务冒烟了，要不然你稍后再试试吧！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不在了，要不换一个试一试？"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空！"),
    ;

    //复写ICustomizeErrorCode的方法，传回QUESTION_NOT_FOUND
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    private String message;               //定义两个变量，通过下面的构造方法进行传值操作
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
