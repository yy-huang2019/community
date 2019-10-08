package com.isoft.community.dto;

import com.isoft.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {               //传输层，与service打交道
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
    private User user;                   //与QuestionMapper相比多了一个User对象，用来将User表中的avatar_url属性获得相应的头像信息
}
