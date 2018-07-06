/**
 * 
 */
package com.elim.springboot.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param writer
     * @throws Exception
     */
    @GetMapping("sample/args")
    public void arguments(PrintWriter writer) throws Exception {
        writer.println("包含debug参数：" + arguments.containsOption("debug"));//true
        writer.println("参数foo的值是：" + arguments.getOptionValues("foo"));//[bar]
        writer.println("其它非选项性参数：" + arguments.getNonOptionArgs());//[abc]
        writer.println("原始参数是：" + Arrays.toString(arguments.getSourceArgs()));//--debug, --foo=bar, abc
        writer.println(arguments.getOptionNames());
        writer.println(System.getProperty("sysprop1"));
        
        
        writer.println(this.environment.getProperty("foo"));
    }

}
