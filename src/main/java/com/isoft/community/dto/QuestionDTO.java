package com.isoft.community.dto;

import com.isoft.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;
}
