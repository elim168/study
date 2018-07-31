package com.elim.springboot.spring.core;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationTestService {

    
    public void notNull(@NotNull Integer num) {
        
    }
    
    public void notBlank(@NotBlank String str) {
        
    }
    
    @Positive
    public int returnPositive(int num) {
        return num;
    }
    
    
}
