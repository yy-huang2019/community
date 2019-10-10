package com.isoft.community.mapper;

import com.isoft.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QustionMapper {

    //将所要发布的问题插入到数据库中
    @Insert("insert into question (title ,description ,gmt_create ,gmt_modified  ,creator ,tag) values (#{title} ,#{description} ,#{gmt_create} ,#{gmt_modified} ,#{creator} ,#{tag})")
    void create(Question question);

    //分页功能展示提问
    @Select("select * from question limit ${offset} , ${size}")
    List<Question> List(@Param("offset")Integer offset, @Param("size")Integer size);

    //查询问题总数
    @Select("select count(1) from question")
    Integer count();
}
