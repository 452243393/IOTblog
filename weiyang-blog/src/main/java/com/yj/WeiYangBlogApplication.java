package com.yj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.yj.mapper")
@EnableScheduling
public class WeiYangBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeiYangBlogApplication.class,args);
    }
}
