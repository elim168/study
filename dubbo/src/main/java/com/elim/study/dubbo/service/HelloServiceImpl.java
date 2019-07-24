package com.elim.study.dubbo.service;

import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class HelloServiceImpl implements HelloService {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public String sayHello(String name) {
        System.out.println("Hello " + name + "-----" + counter.incrementAndGet());
        if (counter.get() < 3) {
//            throw new IllegalStateException("AAAAAAAAAAA");
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Invoke completed" + RpcContext.getContext().getAttachment("ABC"));
        AsyncContext asyncContext = RpcContext.startAsync();
        new Thread(() -> {
            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            asyncContext.write("Hello " + name);
        }).start();
        return null;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.supplyAsync(() -> "Hello " + name);
    }

    @Override
    public void update(String name, Consumer<String> callback) {
        System.out.println("update --- " + name);
        callback.accept("callback " + name);
    }
}
