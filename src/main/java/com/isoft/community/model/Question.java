package com.isoft.community.model;

import lombok.Data;

@Data
public class Question {                           //与数据库打交道的，一一和数据库对应
    private Integer id;
    private String title;
    private String description;
    private long gmt_create;
    private long gmt_modified;
    private String tag;
    private Integer creator;
    private Integer view_count;
    private Integer like_count;
    private Integer comment_count;
}
