package com.elim.springboot.spring.core.importselector;

public class HelloServiceA implements HelloService {

    @Override
    public void doSomething() {
        System.out.println("Hello A");
    }

}
