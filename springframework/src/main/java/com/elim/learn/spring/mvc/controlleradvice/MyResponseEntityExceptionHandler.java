/**
 * 
 */
package com.elim.learn.spring.mvc.controlleradvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ResponseEntityExceptionHandler提供对SpringMVC自带异常的处理，自己也可以通过重载对应的方法对自带异常进行一些扩展，
 * 其中的异常处理都将响应为ResponseEntity对象，如果ResponseEntity中包含的是复杂类型会被以JSON形式返回，基本类型则以
 * 文本形式返回。
 * @author Elim
 * 2017年11月2日
 */
@ControllerAdvice
public class MyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String method = ex.getMethod();
        String[] supportedMethods = ex.getSupportedMethods();
        String body = "不支持的请求类型：" + method + "，支持的请求类型：" + Arrays.toString(supportedMethods);
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
    
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT, reason="test reason")
    public void handleNumberFormat() {
        //直接通过@ResponseStatus指定返回的Http状态码和对应的提示信息
        //@ResponseStatus也可以直接加到异常类上，这样就可以不用定义ExceptionHandler了。
    }
    
}
