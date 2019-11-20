package com.isoft.community.service;

import com.isoft.community.dto.CommentDTO;
import com.isoft.community.enums.CommentTypeEnum;
import com.isoft.community.enums.NotificationStatusEnum;
import com.isoft.community.enums.NotificationTypeEnum;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import com.isoft.community.mapper.CommentMapper;
import com.isoft.community.mapper.NotificationMapper;
import com.isoft.community.mapper.QuestionMapper;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.Comment;
import com.isoft.community.model.Notification;
import com.isoft.community.model.Question;
import com.isoft.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper qustionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional               //自动将该方法体加上事务
    public void insert(Comment comment, User commentator) {
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

            //回复问题
            Question question = qustionMapper.getByID(dbComment.getParent_id());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);           //插入评论入数据库
            commentMapper.addComment(comment.getParent_id());   //增加其创建新的评论的父评论的评论数+1

            //创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

        }else {                                                                  //否则回复的是该问题
            //回复问题
            Question question = qustionMapper.getByID(comment.getParent_id());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setComment_count(0);
            commentMapper.insert(comment);           //插入评论入数据库

            //增加评论数
            qustionMapper.addComment(question.getId());

            //创建通知
            createNotify(comment,question.getCreator(),commentator.getName(), question.getTitle() ,NotificationTypeEnum.REPLY_QUESTION, question.getId());

        }
    }

    //创建通知
    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Integer outer_id) {
        //如果收到通知的人和触发通知的人是同一个人，则不用触发通知
        if(receiver == comment.getCommentator()) {
            return;
        }
            Notification notification = new Notification();
            notification.setGmt_create(System.currentTimeMillis());
            notification.setType(notificationType.getType());
            notification.setOuter_id(outer_id);
            notification.setNotifier(comment.getCommentator());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setReceiver(receiver);
            notification.setNotifier_name(notifierName);
            notification.setOuter_title(outerTitle);

    }


    public List<CommentDTO> listByTargetId(Integer id , CommentTypeEnum type) {
        //获取所有的评论
        List<Comment> comments = commentMapper.selectByParentId(id , type.getType());           //通过评论的parent_id和type的类型找到该问题

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取lambda表达式去重的评论人的id               (用map遍历comments，返回一个得到commentator的结果集，将其计算出来放回到一个set中)
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //通过评论人的id获取评论人并转化为map
        List<User> users = new ArrayList<>();
        for(Integer userId : userIds){
            User user = userMapper.findByID(userId);
            users.add(user);
        }                                           //(收集user_id和user使用map方法)
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //转化comment为commentDTO
//        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
//            CommentDTO commentDTO = new CommentDTO();     //出现的问题都是修后一个是因为直接实例化了一个commentDTO
//            BeanUtils.copyProperties(comment,commentDTO);
//            commentDTO.setUser(userMap.get(comment.getCommentator()));
//            return commentDTO;
//        }).collect(Collectors.toList());

        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(Comment comment : comments){
            //此处是将comment循环传递到commentDTO，需要多个对象将其得到其当前的comment对象
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            //使用上一步的userMap中得到当前评论人的user对象，而不用直接使用双重for循环将comment和user拿到
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
