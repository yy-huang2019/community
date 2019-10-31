package com.isoft.community.mapper;

import com.isoft.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    //增加评论数
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,content) values (#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{content})")
    int insert(Comment comment);
}
