/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传。必须配置一个MultipartResolver，可以采用CommonsMultipartResolver或StandardServletMultipartResolver。
 * 前者应用于基于commons-fileupload，后者基于Servlet3的文件上传。应用前者需要加入commons-fileupload依赖包；应用后者需要启用
 * DispatcherServlet对文件上传的支持。
 * @author Elim
 * 2017年10月30日
 */
@RequestMapping("/fileupload")
@Controller
public class FileUploadController {

    @RequestMapping(method=RequestMethod.GET)
    public String index() {
        return "file_upload_index";
    }

    @RequestMapping(value="doupload", method=RequestMethod.POST)
    @ResponseBody
    public Object doUpload(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") Part file2,
            MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        //保存文件
        Map<String, Object> result = new HashMap<>();
        result.put("file1.contentType", file1.getContentType());
        result.put("file1.size", file1.getSize());
        result.put("file2.contentType", file2.getContentType());
        result.put("file2.size", file2.getSize());
        //其它信息可以参考各自的API
        
        
        //也可以通过MultipartHttpServletRequest的API获取文件
        file1 = multiRequest.getFile("file1");//获取file1
        file1 = multiRequest.getFile("file2");//获取file2
        MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();//所有的文件
        
        
        //如果是基于Servlet3的文件上传，也可以通过HttpServletRequest获取文件
        file2 = request.getPart("file1");
        file2 = request.getPart("file2");
        Collection<Part> parts = request.getParts();//获取所有的文件
        if (multiFileMap != null && parts != null) {
            
        }
        return result;
    }
    
}
