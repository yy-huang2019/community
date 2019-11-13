package com.isoft.community.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outer_id;
    private int type;
    private int status;
    private Long gmt_create;
    private String notifier_name;
    private String outer_title;
}
