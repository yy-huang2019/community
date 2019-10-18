package com.isoft.community.mapper;

import com.isoft.community.model.User;
import org.apache.ibatis.annotations.*;

//@Mapper
//public interface UserMapper {
//    @Insert("insert into user (name ,account_id ,token ,gmt_create ,gmt_modified) values (#{name} ,#{account_id} ,#{token} ,#{gmt_create} ,#{gmt_modified})")
//    void insert(User user);
//}
@Mapper
public interface UserMapper {

    //将登录信息保存到数据库
    @Insert("insert into user (name ,account_id ,token ,gmt_create ,gmt_modified ,avatar_url) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    //int insert(@Param("name") String name , @Param("account_id") String account_id ,@Param("token")String token ,@Param("gmt_create") String gmt_create , @Param("gmt_modified") String gmt_modified ,@Param("avatar_url")String avatar_url);
    void insert(User user);

    //查询token
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);

    //查询id
    @Select("select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);

    //通过数据库查找是否有存在的登录过的用户
    @Select("select * from user where id = #{account_id}")
    User findByAccountID(@Param("account_id") String account_id);

    //如果登陆时之前已经登录过的直接跟新token和名字就可以了
    @Update("update user set name = #{name} ,token = #{token} ,gmt_create = #{gmt_create} ,gmt_modified = #{gmt_modified} ,avatar_url = #{avatar_url} where id = #{id}")
    void update(User dbuser);
}
