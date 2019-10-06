package com.isoft.community.mapper;


import com.isoft.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QustionMapper {
    @Insert("insert into question (title ,description ,gmt_create ,gmt_modified  ,creator ,tag) values (#{title} ,#{description} ,#{gmt_create} ,#{gmt_modified} ,#{creator} ,#{tag})")
    void create(Question question);
}
