package com.isoft.community.model;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class User {
    private Integer id;
    private String name;
    private long gmt_create;
    private String account_id;
    private long gmt_modified;
    private String token;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gmt_create=" + gmt_create +
                ", account_id='" + account_id + '\'' +
                ", gmt_modified=" + gmt_modified +
                ", token='" + token + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer id, String name, long gmt_create, String account_id, long gmt_modified, String token) {
        this.id = id;
        this.name = name;
        this.gmt_create = gmt_create;
        this.account_id = account_id;
        this.gmt_modified = gmt_modified;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public long getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
