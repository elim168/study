/**
 * 
 */
package com.elim.learn.spring.mvc.controlleradvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Elim
 * 2018年1月18日
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 该方法将处理SpringMVC处理过程中抛出的所有的异常，
     * 将使用该方法的返回值来替换正常的Controller方法的返回值
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleException(Exception e) {
        return new ModelAndView("viewName");
    }
    
    /**
     * 该方法将处理SpringMVC过程中抛出的所有的java.lang.IllegalStateException，
     * 而其它异常的处理还由上面定义的handleException()处理。当抛出了一个异常可以同时被
     * 多个@ExceptionHandler标注的方法处理时，对应的异常将交由更精确的异常处理方法处理。
     * 
     * 且抛出该异常时将把处理结果以@ResponseBody的形式返回，此时将被当作JSON返回。
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public Object handleIllegalStateException(IllegalStateException e) {
        Map<String, Object> jsonObj = new HashMap<>();
        jsonObj.put("errorMessage", e.getMessage());
        return jsonObj;
    }
    
}
