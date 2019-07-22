package com.elim.study.dubbo.service;

import java.util.concurrent.CompletableFuture;

public class HelloServiceStub implements HelloService {

    private final HelloService helloService;

    public HelloServiceStub(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public void sayHello(String name) {
        //类似AOP处理
        System.out.println("远程调用前的处理");
        this.helloService.sayHello(name);
        System.out.println("远程调用后的处理");
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return this.helloService.sayHelloAsync(name);
    }
}
