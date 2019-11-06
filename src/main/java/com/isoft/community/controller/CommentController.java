package com.isoft.community.controller;

import com.isoft.community.dto.CommentCreateDTO;
import com.isoft.community.dto.ResultDTO;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.model.Comment;
import com.isoft.community.model.User;
import com.isoft.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private Comment comment;

    @Autowired
    private CommentService commentService;

    //增加评论
    @ResponseBody                    //返回的也是json的格式
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request){        //通过CommentDTO请求的是json的格式

        User user = (User) request.getSession().getAttribute("user");            //通过HttpServletRequest拿到user
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        //通过添加apache.commons.lang的工具包的StringUtils方法直接将null和空值进行判断
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        comment.setParent_id(commentDTO.getParent_id());    //parent_id依赖于type，type=1时parent为问题，type=2为parent为评论
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
