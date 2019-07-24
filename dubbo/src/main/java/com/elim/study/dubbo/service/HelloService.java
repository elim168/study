package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface HelloService {

    String sayHello(String name);

    CompletableFuture<String> sayHelloAsync(String name);

    void update(String name, Consumer<String> callback);

}
