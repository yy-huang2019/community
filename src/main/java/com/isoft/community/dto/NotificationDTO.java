package com.isoft.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private int status;
    private Long gmt_create;
    private Integer notifier;
    private Integer type;
    private String  type_name;
    private String outer_title;
    private String notifier_name;
    private Integer outer_id;
}
