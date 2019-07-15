package com.elim.study.dubbo.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloServiceImpl implements HelloService {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name + "-----" + counter.incrementAndGet());
        if (counter.get() < 3) {
            throw new IllegalStateException("AAAAAAAAAAA");
        }
        System.out.println("Invoke completed");
    }
}
