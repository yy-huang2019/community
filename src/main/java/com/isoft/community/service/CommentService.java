package com.isoft.community.service;

import com.isoft.community.enums.CommentTypeEnum;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import com.isoft.community.mapper.CommentMapper;
import com.isoft.community.mapper.QustionMapper;
import com.isoft.community.model.Comment;
import com.isoft.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QustionMapper qustionMapper;

    @Transactional               //自动将该方法体加上事务
    public void insert(Comment comment) {
        if(comment.getParent_id() == 0){            //没有找到parent_id
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){      //没有找到回复的类型

            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){              //(comment=2)如果该评论的类型与当前评论的类型一致则回复的是评论
            //回复评论
            Comment dbComment =  commentMapper.found(comment.getParent_id());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);           //插入评论入数据库
        }else {                                                                  //否则回复的是该问题
            //回复问题
            Question question = qustionMapper.getByID(comment.getParent_id());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);           //插入评论入数据库

            //增加评论数
            qustionMapper.addComment(question.getId());
        }
    }
}
