package com.isoft.community.mapper;

import com.isoft.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {

    //将所要发布的问题插入到数据库中
    @Insert("insert into question (title ,description ,gmt_create ,gmt_modified  ,creator ,tag) values (#{title} ,#{description} ,#{gmt_create} ,#{gmt_modified} ,#{creator} ,#{tag})")
    void create(Question question);

    //分页功能展示提问(按照时间倒序排列)
    @Select("select * from question order by gmt_create desc,id asc limit ${offset} , ${size}")
    List<Question> List(@Param("offset") Integer offset, @Param("size") Integer size);

    //查询问题总数
    @Select("select count(1) from question")
    Integer count();

    //分页展示个人主页所提问题(按照时间倒序排列)
    @Select("select * from question where creator = #{user_id} order by gmt_create desc,id asc limit ${offset} , ${size}")
    List<Question> ListByUserId(@Param("user_id") Integer user_id, @Param("offset") Integer offset, @Param("size") Integer size);


    //个人主页通过id找到所提出的问题
    @Select("select count(1) from question where creator = #{user_id}")
    Integer countByUserID(@Param("user_id") Integer user_id);

    //通过问题发起人的id找到该问题的信息
    @Select("select * from question where id = #{id}")
    Question getByID(@Param("id")Integer id);

    //跟新所发布的问题
    @Update("update question set title = #{title} , description = #{description} , tag = #{tag} , gmt_modified = #{gmt_modified} where id = #{id}")
    int update(Question question);

    //跟新所发布的问题的阅读数
    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void incView(Integer id);

    //跟新所发布问题的评论数
    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void addComment(Integer id);

    //通过该问问题的tag通过正则表达式regexp找到相应问题将其展示在相关问题列表内
    @Select("select * from question where id != ${id} and tag regexp #{tag}")
    List<Question> selectRelated(@Param("tag") String tag, @Param("id") Integer id);

    //查询问题
    @Select("select count(*) from question where title regexp #{search}")
    Integer countBySearch(@Param("search") String search);

    //通过搜索将问题展示出来
    @Select("select * from question where  title regexp #{search} order by gmt_create desc,id asc limit ${offset} , ${size}")
    List<Question> selectBySearch(@Param("search")String search, @Param("offset")Integer offset, @Param("size") Integer size);

}
