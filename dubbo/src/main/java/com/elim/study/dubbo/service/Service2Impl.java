package com.elim.study.dubbo.service;

import java.io.IOException;

public class Service2Impl implements Service2 {

    public int plus1(int a) {
        System.out.println(Thread.currentThread().getName() + "----" + a);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a+1;
    }

}
