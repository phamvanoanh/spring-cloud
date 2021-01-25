package com.edu.springbootreactive;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

@Slf4j
public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("==========start completable future==========");
        ask();


        final CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @SneakyThrows
            @Override
            public String get() {
                sleep(5000);
                log.info("execute===========");
                return "42";
            }
        }, Executors.newFixedThreadPool(3));
        log.info("==========end completable future========== {} ", future.get());

        log.info("==========Then apply function==========");
        final CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "42";
        });
        final CompletableFuture<Integer> f2 = f1.thenApply(Integer::parseInt);
        final CompletableFuture<Integer> f3 = f2.thenApply(p -> p * 1000);

        //final Integer integer = f3.get();
        //log.info("result then apply==== {}", integer);

        log.info("==========Then accept function==========");
        f3.thenAcceptAsync((t) -> log.info("result accept {}", t), Executors.newFixedThreadPool(3));
        log.info("Continuing");



    }

    public static CompletableFuture<String> ask() {
        CompletableFuture<String> cfs = new CompletableFuture<>();

        cfs.complete("oanhpv");

        return cfs;
    }
}
