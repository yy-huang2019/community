package com.isoft.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private long parent_id;
    private String content;
    private Integer type;
}
