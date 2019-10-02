package com.isoft.community.mapper;

import com.isoft.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//@Mapper
//public interface UserMapper {
//    @Insert("insert into user (name ,account_id ,token ,gmt_create ,gmt_modified) values (#{name} ,#{account_id} ,#{token} ,#{gmt_create} ,#{gmt_modified})")
//    void insert(User user);
//}
@Mapper
public interface UserMapper {
    @Insert("insert into user (name ,account_id ,token ,gmt_create ,gmt_modified) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
    int insert(@Param("name") String name , @Param("account_id") String account_id ,@Param("token")String token ,@Param("gmt_create") String gmt_create , @Param("gmt_modified") String gmt_modified);
}
