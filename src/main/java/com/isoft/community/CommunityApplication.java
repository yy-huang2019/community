package com.isoft.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//扫描@Controller,@Service,@Mapper注解，如果有为其实例化对象
@ComponentScan(basePackages = "com.isoft.community")
//mybatis框架实现sql语句后为dao层生成类最后实例化对象（接口），mybatis框架实现sql语句：1注解 2写映射文件
@MapperScan("com.isoft.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
