package com.elim.springboot.core.json;

import java.util.List;

import lombok.Data;

@Data
public class RootObject {

    private int id;
    private String code;
    private List<Shape> shapes;
    
}
