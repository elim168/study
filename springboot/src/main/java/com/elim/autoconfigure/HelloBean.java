package com.elim.autoconfigure;

import lombok.Data;

@Data
public class HelloBean {

    private String name = "world";
    
    public void sayHello() {
        System.out.println("Hello " + name);
    }
    
}
