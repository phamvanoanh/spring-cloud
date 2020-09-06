package com.springboot.completablefuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

@RestController
public class DemoController {

    @Autowired
    DemoService  demoService;


    @RequestMapping(value = "/async", method = RequestMethod.GET)
    @ResponseBody
    public String callAsyncFunction() {

        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture.supplyAsync(() -> {
            try {
                CompletableFuture<String> demo = demoService.getDemo();
                demo.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }, executor);
        return "pham van oanh";
    }
}
