/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elim.learn.spring.support.ResponseStatusException;

/**
 * 验证异常处理
 * @author Elim
 * 2017年11月2日
 */
@Controller
@RequestMapping("/exceptionhandler")
public class ExceptionHandlerController {

    /**
     * 用get请求该地址
     */
    @RequestMapping(value="/methodunsupported", method={RequestMethod.PUT, RequestMethod.POST})
    public void methodUnsupported() {
        
    }
    
    @RequestMapping("/numberformat")
    public Object numberFormat() {
        return Integer.parseInt("abc");
    }
    
    @RequestMapping("/illegalstate")
    public void illegalState() {
        throw new IllegalStateException("State is not Correct!");
    }
    
    @RequestMapping("/responsestatus")
    public void responseStatusException() {
        throw new ResponseStatusException();
    }
    
}
