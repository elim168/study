/**
 * 
 */
package com.elim.learn.spring.mvc.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elim.learn.spring.mvc.controller1.FooController;

/**
 * @author Elim
 * 2018年1月19日
 */
@ControllerAdvice(annotations=RestController.class)
public class MyExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public void handleIllegalStateException(IllegalStateException e) {
        System.out.println(MyExceptionHandler.class);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value=HttpStatus.BAD_GATEWAY, reason="Hello Error")
    public void handleIllegalArgumentException() {
        
    }
    
}
