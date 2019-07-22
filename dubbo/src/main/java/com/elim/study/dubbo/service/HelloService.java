package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;

public interface HelloService {

    void sayHello(String name);

    CompletableFuture<String> sayHelloAsync(String name);
}
