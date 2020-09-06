package com.springboot.completablefuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootCompletableFutureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCompletableFutureApplication.class, args);
	}

}
