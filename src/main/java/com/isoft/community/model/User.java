package com.isoft.community.model;

import lombok.Data;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

@Data
public class User {
    private Integer id;
    private String name;
    private long gmt_create;
    private String account_id;
    private long gmt_modified;
    private String token;
    private String avatar_url;
}
