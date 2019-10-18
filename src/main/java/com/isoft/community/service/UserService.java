package com.isoft.community.service;

import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountID(user.getAccount_id());         //通过数据库查找是否有存在的登录过的用户
        if(dbuser == null){
            //插入
            user.setGmt_create(System.currentTimeMillis());                     //创建时间
            String gmt_create = String.valueOf(user.getGmt_create());
            user.setGmt_modified(user.getGmt_create());                         //修改时间
            String gmt_modified = String.valueOf(user.getGmt_create());
            userMapper.insert(user);     //调用insert方法
        }else{
            //跟新
            dbuser.setGmt_create(System.currentTimeMillis());                     //创建时间
            dbuser.setGmt_modified(user.getGmt_create());                         //修改时间
            dbuser.setToken(user.getToken());
            dbuser.setName(user.getName());
            userMapper.update(dbuser);          //跟新token
        }
    }
}
