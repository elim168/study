/**
 * 
 */
package com.elim.springboot.web.controller;

import java.io.IOException;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * @author Elim 2017年5月16日
 */
@Controller
public class SampleController {

    @Value("${test.appId}")
    private String appId;
    
    @RequestMapping("sample/helloworld")
    public void sample(Writer writer) throws IOException {
        writer.append("hello world!");
        writer.append(this.appId);
        writer.flush();
    }

}
