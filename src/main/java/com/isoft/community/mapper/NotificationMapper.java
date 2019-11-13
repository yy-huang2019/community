package com.isoft.community.mapper;


import com.isoft.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    //创建通知
    @Insert("insert into notification (notifier,receiver,outer_id,type,status,gmt_create,notifier_name,outer_title) values (#{notifier},#{receiver},#{outer_id},#{type},#{status},#{gmt_create},#{notifier_name},#{outer_title})")
    void insert(Notification notification);

    //分页展示个人主页通知(按照时间倒序排列)
    @Select("select * from notification where receiver = #{id} order by gmt_create desc,id asc limit ${offset} , ${size}")
    List<Notification> ListByUserId(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    //个人主页通过id找到所得到的通知
    @Select("select count(1) from notification where receiver = #{id}")
    Integer countByUserID(@Param("id") Integer id);

    //查询通知的所有信息
    @Select("select * from notification where id = #{id}")
    Notification selectById(@Param("id") Integer id);

    //跟新通知状态
    @Update("update notification set status = #{status} where id = #{id}")
    void updateById(Notification notification);

    //个人主页通过id和是否阅读的状态找到所得到的通知
    @Select("select count(1) from notification where receiver = #{id} and status = #{status}")
    Integer countUserID(@Param("id") Integer id , @Param("status")int status);
}
