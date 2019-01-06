/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * SpringMVC对Servlet3异步请求的支持有两种方式，分别是返回Callable和返回DeferredResult。
 * You can also register a CallableProcessingInterceptor or a DeferredResultProcessingInterceptor 
 * globally through the MVC Java config or the MVC namespace. Those interceptors provide a full set 
 * of callbacks and apply every time a Callable or a DeferredResult is used.
 * 需要开启DispatcherServlet和在DispatcherServlet之前的Filter对异步请求的支持。
 * @author Elim
 * 2017年10月19日
 */
@Controller
@RequestMapping("/async_request")
public class AsyncRequestController {

    /**
     * 返回了一个Callable后SpringMVC会发起一次HttpServletRequest的异步调用，在异步调用中将调用Callable的call
     * 方法，call方法的返回结果将按照SpringMVC的正常逻辑进行。即交给视图解析器进行解析，找到目标视图后进行结果的渲染。这个过程中可以
     * 返回一个正常的页面，也可以是JSON等。祥见CallableMethodReturnValueHandler的handleReturnValue()
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/callable")
    public Callable<String> forCallable(Model model) throws Exception {
        return () -> {
            TimeUnit.SECONDS.sleep(1);//睡眠1秒，模仿某些业务操作
            model.addAttribute("a", "aaaaaaa");
            return "async_request_callable";
        };
    }
    
    /**
     * 对于callable响应是可以指定超时处理和调用完成后的回调处理的，这些只需要把Callable用WebAsyncTask包起来，并返回WebAsyncTask。
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/callable/timeout")
    public WebAsyncTask<String> forCallableWithTimeout(Model model) throws Exception {
        long timeout = 5 * 1000L;
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(timeout, () -> {
            TimeUnit.MILLISECONDS.sleep(timeout + 10);
            model.addAttribute("a", "aaaaaaa");
            return "async_request_callable";
        });
        asyncTask.onTimeout(() -> {
            System.out.println("响应超时回调");
            return "async_request_callable_timeout";
        });
        asyncTask.onCompletion(() -> {
            System.out.println("响应callable调用完成的回调");
        });
        return asyncTask;
    }
    
    /**
     * 使用DeferredResult的返回结果的编程通常是中处理器方法中创建一个DeferredResult实例，把它保存起来后再进行返回，比如保存到一个队列中，
     * 然后在另外的一个线程中会从这个队列中拿到相应的DeferredResult对象进行相应的业务处理后会往DeferredResult中设置对应的返回值。
     * 返回了DeferredResult后SpringMVC将创建一个DeferredResultHandler用于监听DeferredResult，一旦DeferredResult中设置了返回值后，
     * DeferredResultHandler就将对返回值进行处理。DeferredResult的处理过程见DeferredResultMethodReturnValueHandler的handleReturnValue()
     * @return
     * @throws Exception
     */
@RequestMapping("/deferredresult")
public DeferredResult<String> forDeferredResult() throws Exception {
    DeferredResult<String> result = new DeferredResult<>();
    new Thread(() -> {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.setResult("async_request_deferredresult");
    }).start();
    return result;
}
    
@RequestMapping("/deferredresult/timeout")
public DeferredResult<String> forDeferredResultWithTimeout() throws Exception {
    DeferredResult<String> result = new DeferredResult<>(10 * 1000);
    new Thread(() -> {
        try {
            TimeUnit.SECONDS.sleep(31);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.setResult("async_request_deferredresult");
    }).start();
    
    result.onTimeout(() -> {
        System.out.println("响应超时回调函数");
    });
    
    result.onCompletion(() -> {
        System.out.println("响应完成的回调函数");
    });
    
    return result;
}
    
}
