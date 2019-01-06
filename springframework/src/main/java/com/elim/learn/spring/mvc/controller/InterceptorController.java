/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证普通HandlerInterceptor的
 * @author Elim
 * 2017年10月28日
 */
@RestController
@RequestMapping("/interceptor")
public class InterceptorController {

    @RequestMapping("/a")
    public String helloA() {
        return "helloA";
    }
    
    @RequestMapping("/b")
    public String helloB() {
        return "helloB";
    }
    
}
