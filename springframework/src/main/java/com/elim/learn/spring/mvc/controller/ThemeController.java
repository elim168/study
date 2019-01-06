/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 验证theme的
 * @author Elim
 * 2017年10月29日
 */
@Controller
@RequestMapping("/theme")
public class ThemeController {

    @RequestMapping(method=RequestMethod.GET)
    public String index() {
        return "theme/index";
    }
    
}
