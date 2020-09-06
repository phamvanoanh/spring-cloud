package com.springboot.completablefuture;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DemoService {

    @Autowired
    private ConfigurationApp configurationApp;

    public CompletableFuture<String> getDemo () throws InterruptedException {
        System.out.println("Start thread : " + Thread.currentThread());
        Thread.sleep(5000L);
        System.out.println("End thread: " + Thread.currentThread());
        return CompletableFuture.completedFuture("oanh");
    }

}
