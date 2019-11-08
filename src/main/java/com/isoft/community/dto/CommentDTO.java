package com.isoft.community.dto;

import com.isoft.community.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component            //与@Autowired配合使用，bean不用在实例化对象
@Data
public class CommentDTO {
    private Integer parent_id;
    private String content;
    private Integer type;
    private Integer id;
    private Integer commentator;
    private long gmt_create;
    private long gmt_modified;
    private long like_count;
    private User user;
    private Integer comment_count;
}
