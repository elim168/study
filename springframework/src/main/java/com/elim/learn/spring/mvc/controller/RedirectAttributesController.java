/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 验证RedirectAttributes。RedirectAttributes只在使用RedirectView时才生效，即返回的
 * 视图名以redirect:打头时才生效。
 * @author Elim
 * 2017年10月19日
 */
@Controller
@RequestMapping("/redirectattributes")
public class RedirectAttributesController {

    @RequestMapping("/src")
    public String index(RedirectAttributes redirectAttributes) {
        
        /**
         * addAttribute中的内容会作为redirect的URL的查询参数传递，即会以
         * /redirectattributes/target?key1=value1&key2=value2的形式传递，
         * 其中的value是以String的形式传递的，添加进去时会把它转换为String，如果内部没有对应的转换器支持则将
         * 抛出异常。具体可以参考RedirectAttributesModelMap中的对应实现
         */
        redirectAttributes.addAttribute("key1", "value1")
            .addAttribute("key2", "value2");
        
        /**
         * addFlashAttribute中的内容会存放到Session中，且在一次页面跳转后失效。
         */
        redirectAttributes.addFlashAttribute("modelAttr1", "modelAttr1Value1")
            .addFlashAttribute("modelAttr2", "modelAttr1Value2")
            .addFlashAttribute("listAttr", Arrays.asList(1, 2, 3));
        return "redirect:/redirectattributes/target";
    }
    
    @RequestMapping("/src2")
    public String src2(Map<String, Object> model) {
        /**
         * 当redirect时，Model中包含的基本类型的属性或基本类型对应的Collection/Array会自动作为redirect后的URL的查询参数
         * 一起传递过去。而非基本类型的属性就传递不过去，如果需要传递则可以用RedirectAttributes。
         */
        model.put("key1", "value1");
        model.put("key2", "value2");
        model.put("modelAttr1", "modelAttr1Value1");
        model.put("listAttr", Arrays.asList(Arrays.asList(1, 2, 3)));
        return "redirect:/redirectattributes/target";
    }
    
    /**
     * 这是由src重定向过来的目标页面，其中的modelAttr1将从Model中获取，而key1将从request的查询参数中获取
     * @param modelAttr1
     * @param key1
     * @return
     */
    @RequestMapping("/target")
    public String target(@ModelAttribute("modelAttr1") String modelAttr1, @RequestParam("key1") String key1) {
        return "redirect_attributes_target";
    }
    
}
