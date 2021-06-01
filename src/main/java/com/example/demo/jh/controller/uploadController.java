package com.example.demo.jh.controller;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.demo.jh.storage.StorageProperties;
import com.example.demo.jh.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class uploadController {

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            System.out.println("start");
            //storageService.deleteAll();//전부 삭제
            storageService.init();//파일 업로드 세팅
            System.out.println("start end");
        };
    }
}
