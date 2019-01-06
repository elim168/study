/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.MatrixVariable;
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
    
    /**
     * 使用占位符时占位符将被指定的配置文件、系统参数或Environment中的变量所替换。
     * 对应的PlaceHolder需要是定义在SpringMVC对应的WebApplicationContext中的。
     * 此处的占位符hello对应的值是world。
     * @return
     */
    @RequestMapping("/placeholder/${hello}")
    public Object testPlaceHolder() {
        return "path with placeholder";
    }
    
    /**
     * matrix变量即在路径中包含的路径变量后，可以通过分号的形式分隔指定matrix变量，多个变量之间还是用分号分隔，变量名和值通过
     * 等号分隔，一个变量的多个值可以通过逗号分隔，或者通过多次指定值。如：<br/>
     * "/abc/ddd;a=1;b=1,2,3;c=1;c=2"即定义了a、b、c三个martix变量，其中a的值为1,b的值为1,2,3，c的值为1,2。
     * 
     * 需要指定<pre><mvc:annotation-driven enable-matrix-variables="true"/></pre>请求中的matrix变量才会被SpringMVC解析。
     * 
     * URI中包含的路径变量不一定要作为Controller方法的一个参数
     * @param matrix1
     * @param matrix2
     * @param matrixes
     * @return
     */
    @RequestMapping("/pathvar_with_matrix/{pathvar}")
    public Object testPathVarWithMatrix(@MatrixVariable("matrix1") String matrix1, 
            @MatrixVariable("matrix2") String matrix2, 
            @MatrixVariable Map<String, Object> matrixes) {
        Map<String, Object> result = new HashMap<>();
        result.put("matrix1", matrix1);
        result.put("matrix2", matrix2);
        result.put("matrixes", matrixes);
        return result;
    }
    
    /**
     * 本示例通过consumes指定请求的头信息的Content-Type必须是text/plain，也可以通过!表示必须
     * 不是某种Content-Type，比如"!text/plain"表示请求头信息的Content-Type必须不是text/plain
     * @return
     */
    @RequestMapping(value="/consumes/text/plain", consumes="text/plain")
    public Object testConsumes() {
        return "consumes用来匹配请求头信息的Content-Type";
    }
    
    /**
     * 本示例通过produces指定请求的头信息中的Accept必须支持text/plain，也可以通过!表示必须不支持某种格式。
     * @return
     */
    @RequestMapping(value="/produces/text/plain", produces="text/plain")
    public Object testProduces() {
        return "produces用来匹配请求头信息的Accept";
    }
    
}
