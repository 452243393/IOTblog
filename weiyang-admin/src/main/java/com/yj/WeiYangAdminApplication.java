package com.yj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yj.mapper")
public class WeiYangAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeiYangAdminApplication.class,args);
    }
}
