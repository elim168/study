package com.elim.study.dubbo.service;

import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloServiceImpl implements HelloService {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name + "-----" + counter.incrementAndGet());
        if (counter.get() < 3) {
//            throw new IllegalStateException("AAAAAAAAAAA");
        }
        /*try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("Invoke completed" + RpcContext.getContext().getAttachment("ABC"));
    }
}
