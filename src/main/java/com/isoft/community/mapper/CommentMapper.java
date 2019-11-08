package com.isoft.community.mapper;

import com.isoft.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    //增加评论数
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,content) values (#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{content})")
    int insert(Comment comment);

    //找到评论人的id
    @Select("select * from comment where id = ${parent_id}")
    Comment found(@Param("parent_id") Integer parent_id);

    //通过评论找到该问题的parentId
    @Select("select * from comment where parent_id = ${id} and type = ${type} order by gmt_create desc")
    List<Comment> selectByParentId(@Param("id") Integer id , @Param("type")Integer type);

    //跟新评论的二级评论数
    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void addComment(Integer id);
}
