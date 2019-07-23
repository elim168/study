package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;

public interface HelloService {

    String sayHello(String name);

    CompletableFuture<String> sayHelloAsync(String name);
}
