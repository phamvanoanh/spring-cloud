package com.springboot.demo;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

@SpringBootApplication
public class SpringbootbasicApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootbasicApplication.class, args);
    }

}
