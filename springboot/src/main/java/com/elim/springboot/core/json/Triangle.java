package com.elim.springboot.core.json;

import lombok.Data;

@Data
public class Triangle implements Shape {

    private double side1;
    private double side2;
    private double side3;
    
    
    @Override
    public String getName() {
        return "triangle";
    }

}
