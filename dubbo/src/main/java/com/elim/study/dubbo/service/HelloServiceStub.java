package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HelloServiceStub implements HelloService {

    private final HelloService helloService;

    public HelloServiceStub(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        //类似AOP处理
        System.out.println("远程调用前的处理");
        String result = this.helloService.sayHello(name);
        System.out.println("远程调用后的处理");
        return result;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return this.helloService.sayHelloAsync(name);
    }

    @Override
    public void update(String name, Consumer<String> callback) {
        this.helloService.update(name, callback);
    }
}
