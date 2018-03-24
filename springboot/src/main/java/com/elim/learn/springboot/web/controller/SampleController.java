/**
 * 
 */
package com.elim.learn.springboot.web.controller;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Elim 2017年5月16日
 */
@Controller
public class SampleController {

    @RequestMapping("sample/helloworld")
    public void sample(Writer writer) throws IOException {
        writer.append("hello world!");
        writer.flush();
    }

}
