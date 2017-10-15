/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证路径匹配的Controller
 * @author Elim
 * 2017年10月15日
 */
@RestController
@RequestMapping("/path/test")
public class PathTestController {

    /**
     * 路径参数中通过正则表达式定义更精确的匹配。语法是{varName:regex}，varName指定路径变量名，regex定义需要匹配的正则表达式。所以
     * 本例中的regex1需要匹配一位的纯小写字母，regex2需要匹配3位的数字
     * @param regex1
     * @param regex2
     * @return
     */
    @RequestMapping("/pathvariable/{regex1:[a-z]}/{regex2:\\d{3}}")
    public Object testPathVariableRegex(@PathVariable String regex1, @PathVariable String regex2) {
        Map<String, Object> result = new HashMap<>();
        result.put("regex1", regex1);
        result.put("regex2", regex2);
        return result;
    }
    
    /**
     * 可以通过*匹配任意字符，但是只包含一级，即本示例可以匹配/antstyle/a，但是不能匹配/antstyle/a/b，如需
     * 映射多级，需要使用多个*。
     * @return
     */
    @RequestMapping("/antstyle/*")
    public Object testAntStyle() {
        return "antStyle";
    }
    
    /**
     * 应用两个*可以匹配多级映射，本示例可以匹配/antstyle/twostar/a，
     * 也可以匹配/antstyle/twostar/a/b等，拥有更多层级也是可以匹配的。
     */
    @RequestMapping("/antstyle/twostar/**")
    public Object testAntStyle2() {
        return "ant style with two star";
    }
    
    /**
     * *还可以跟路径变量一起使用，本示例将匹配/antstylewithpathvariable/a/b/abc等类似层级的路径，
     * 其中倒数第二层将作为变量path的内容，顺数第二层将由*匹配，可以是任意内容。
     * @param path
     * @return
     */
    @RequestMapping("/antstylewithpathvariable/*/{path}/abc")
    public Object testAntStyleWithPathVariable(@PathVariable String path) {
        return "ant style with path variable, path is " + path;
    }
    
}
