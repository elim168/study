package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HelloServiceMock implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Local Hello " + name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.supplyAsync(() -> "Local Hello " + name);
    }

    @Override
    public void update(String name, Consumer<String> callback) {

    }
}
