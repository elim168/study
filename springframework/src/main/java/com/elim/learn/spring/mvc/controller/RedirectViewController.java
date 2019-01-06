/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 验证RedirectView的。
 * redirect时会自动把当前Model中包含的原始类型的属性及原始类型的Collection/Array作为查询参数传递下去。
 * 比如本示例中会自动附加查询参数：?a=1&b=2&c=1&c=2&c=3
 * @author Elim
 * 2017年10月28日
 */
@Controller
@RequestMapping("/redirect")
public class RedirectViewController {

    @RequestMapping("/{target}")
    public String redirectTo(@PathVariable("target") String redirectTo, Map<String, Object> model) {
        model.put("a", "1");
        model.put("b", 2);
        model.put("c", Arrays.asList(1, 2, 3));
        return "redirect:/" + redirectTo;
    }
    
    @RequestMapping("/pathvar")
    public String redirectToPathVariable(Map<String, Object> model) {
        /**
         * redirect时redirect中包含的路径变量会自动的被Model中的对应变量替换
         */
        model.put("pathVar", "hellopath");
        return "redirect:/{pathVar}";
    }
    
}
