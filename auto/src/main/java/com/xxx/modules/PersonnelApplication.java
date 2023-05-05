package com.xxx.modules;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xxx.modules.mapper")
public class PersonnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelApplication.class, args);
    }

}
