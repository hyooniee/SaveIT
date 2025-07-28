package com.saveit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.saveit.mapper") 
public class SaveITprojectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaveITprojectApplication.class, args);
    }
}



