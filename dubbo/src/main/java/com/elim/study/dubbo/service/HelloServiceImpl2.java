package com.elim.study.dubbo.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloServiceImpl2 implements HelloService {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void sayHello(String name) {
        System.out.println("Hello 2 " + name + "-----" + counter.incrementAndGet());
        try {
            TimeUnit.MILLISECONDS.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Invoke completed");
    }
}
