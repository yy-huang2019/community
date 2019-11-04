package com.isoft.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Comment {
    private Integer id;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private long gmt_create;
    private long gmt_modified;
    private long like_count;
    private String content;
}
