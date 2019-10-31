package com.isoft.community.controller;

import com.isoft.community.dto.CommentDTO;
import com.isoft.community.mapper.CommentMapper;
import com.isoft.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Comment comment;

    //增加评论
    @ResponseBody                    //返回的也是json的格式
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO){        //请求的是json的格式
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentMapper.insert(comment);
        return null;
    }
}
