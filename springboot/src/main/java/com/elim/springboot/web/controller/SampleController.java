/**
 * 
 */
package com.elim.springboot.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elim.springboot.core.json.RootObject;
import com.elim.springboot.core.json.Shape;
import com.elim.springboot.core.json.Square;
import com.elim.springboot.core.json.Triangle;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Elim 2017年5月16日
 */
@Controller
public class SampleController {

    @Value("${test.appId}")
    private String appId;
    @Autowired
    private ApplicationArguments arguments;
    
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @RequestMapping("sample/helloworld")
    public void sample(Writer writer) throws IOException {
        writer.append("hello world!");
        writer.append(this.appId);
        writer.append("<br/>HHHHHHHHHHHHHH" + this.environment.getProperty("test.prop.a"));
        writer.flush();
    }

    /**
     * 传递的命令行参数是--debug --foo=bar abc
     * 
     * @param writer
     * @throws Exception
     */
    @GetMapping("sample/args")
    public void arguments(PrintWriter writer, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        writer.println("包含debug参数：" + arguments.containsOption("debug"));// true
        writer.println("参数foo的值是：" + arguments.getOptionValues("foo"));// [bar]
        writer.println("其它非选项性参数：" + arguments.getNonOptionArgs());// [abc]
        writer.println("原始参数是：" + Arrays.toString(arguments.getSourceArgs()));// --debug,
                                                                              // --foo=bar,
                                                                              // abc
        writer.println(arguments.getOptionNames());
        writer.println(System.getProperty("sysprop1"));

        writer.println(this.environment.getProperty("foo"));
    }

    @GetMapping("/sample/json")
    @ResponseBody
    public Object json() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("KEY_" + (i + 1), "特殊值-" + (i + 1));
        }
        return map;
    }

    @GetMapping("/sample/json/string")
    @ResponseBody
    public String stringResult() {
        return "Hello,中国";
    }

    @GetMapping("/sample/json/serializer")
    @ResponseBody
    public RootObject jsonObject() throws Exception {
        RootObject rootObject = new RootObject();
        rootObject.setId(1);
        rootObject.setCode("A00110101");
        List<Shape> shapes = new ArrayList<>();
        Triangle triangle = new Triangle();
        triangle.setSide1(2);
        triangle.setSide2(5);
        triangle.setSide3(4);
        shapes.add(triangle);
        
        Square square = new Square();
        square.setWidth(10);
        shapes.add(square);
        
        rootObject.setShapes(shapes);
        
        ObjectMapper objectMapper = this.applicationContext.getBean(ObjectMapper.class);
        /**
         * 直接通过ObjectMapper转换是有效果的，通过ResponseBody自动转换时又没效果。
         */
        System.out.println(objectMapper.writeValueAsString(rootObject));
        
        
        
        JsonGenerator gen = objectMapper.getFactory().createGenerator(System.out);
        objectMapper.writer().writeValue(gen, rootObject);
        
        return rootObject;
    }
    
}
