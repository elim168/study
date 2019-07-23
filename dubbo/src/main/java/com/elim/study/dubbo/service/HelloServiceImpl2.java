package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloServiceImpl2 implements HelloService {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public String sayHello(String name) {
        String result = "Hello 2 " + name + "-----" + counter.incrementAndGet();
        System.out.println(result);
        try {
            TimeUnit.MILLISECONDS.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Invoke completed");
        return result;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }
}
