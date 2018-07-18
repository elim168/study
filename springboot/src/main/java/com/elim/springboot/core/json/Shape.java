package com.elim.springboot.core.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Shape {

    @JsonIgnore
    String getName();
    
}
