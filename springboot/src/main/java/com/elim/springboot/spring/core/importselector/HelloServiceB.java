package com.elim.springboot.spring.core.importselector;

public class HelloServiceB implements HelloService {

    @Override
    public void doSomething() {
        System.out.println("Hello B");
    }

}
