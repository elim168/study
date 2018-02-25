/**
 * 
 */
package com.elim.learn.spring.mvc.controller1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elim
 * 2018年1月19日
 */
@RestController
@RequestMapping("/controller1/foo")
public class FooController {

    @RequestMapping("/illegal")
    public void illegalStateException() {
        throw new IllegalArgumentException();
    }
    
}
