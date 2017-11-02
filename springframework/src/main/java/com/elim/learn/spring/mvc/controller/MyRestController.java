/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用RestController标注的控制器相当于在类上标注了@Controller和@ResponseBody，而实际上
 * RestController注解上也确实使用了@Controller和@ResponseBody注解。
 * 
 * @author Elim 2017年10月10日
 */
@RestController
@RequestMapping("/rest/my")
public class MyRestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object test() {
        return Arrays.asList(1, 2, 3, 4, 5);
    }
    
    @RequestMapping("/string")
    public Object string() {
        return "this is a string. 这是一个字符串。";
    }
    
}
