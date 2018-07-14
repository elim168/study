package com.elim.springboot.core.json;

import lombok.Data;

@Data
public class Square implements Shape {

    private double width;
    
    @Override
    public String getName() {
        return "square";
    }

}
