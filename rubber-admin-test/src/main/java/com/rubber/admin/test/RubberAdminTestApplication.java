package com.rubber.admin.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rubber.admin.**")
@MapperScan("com.rubber.admin.**")
public class RubberAdminTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RubberAdminTestApplication.class, args);
    }

}
