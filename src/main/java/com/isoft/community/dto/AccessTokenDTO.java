package com.isoft.community.dto;

public class AccessTokenDTO {                //将五个变量封装起来，直接调用，将其传递给github的API，返回其access_token
    private  String client_id;
    private  String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getCode() {
        return code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AccessTokenDTO() {
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AccessTokenDTO(String client_id, String client_secret, String code, String redirect_uri, String state) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.redirect_uri = redirect_uri;
        this.state = state;
    }
}
