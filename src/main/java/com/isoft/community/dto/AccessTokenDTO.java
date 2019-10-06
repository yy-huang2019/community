package com.isoft.community.dto;


import lombok.Data;

@Data
public class AccessTokenDTO {                //将五个变量封装起来，直接调用，将其传递给github的API，返回其access_token
    private  String client_id;
    private  String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;
}
