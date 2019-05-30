package com.rubber.admin.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.rubber.admin.**.mapper")
@ComponentScan("com.rubber.admin.**")
@ServletComponentScan
public class AdminTestSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminTestSysApplication.class, args);
    }

}
