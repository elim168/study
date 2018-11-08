package com.elim.learn.spring.cloud.provider.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("hello")
public class HelloController {
    
    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @GetMapping
    public String sayHelloWorld() {
        return this.instanceId + " hello world.  -- " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private int count;

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "15000") })
    @GetMapping("error")
    public String error() {
        String result = ++count + " Error. " + LocalDateTime.now();
        System.out.println(result);
        if (count % 35 != 0) {
            throw new IllegalStateException();
        }
        return result;
    }

    public String fallback() {
        return count++ + "result from fallback."
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"));
    }

    @HystrixCommand(fallbackMethod = "fallbackWithParam")
    @GetMapping("error/{param}")
    public String error(@PathVariable("param") String param) {
        throw new IllegalStateException();
    }

    public String fallbackWithParam(String param) {
        return "fallback with param : " + param;
    }

    @GetMapping("error/default")
    @HystrixCommand(defaultFallback = "defaultFallback")
    public String errorWithDefaultFallback() {
        throw new IllegalStateException();
    }

    public String defaultFallback() {
        return "default";
    }

    @GetMapping("timeout")
    @HystrixCommand(defaultFallback = "defaultFallback", commandProperties = @HystrixProperty(name = "execution.timeout.enabled", value = "false"))
    public String timeout() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout";
    }

    @GetMapping("concurrent")
    @HystrixCommand(defaultFallback = "defaultFallback", commandProperties = @HystrixProperty(name = "execution.timeout.enabled", value = "false"), threadPoolProperties = {
            @HystrixProperty(name = "maximumSize", value = "15"),
            @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
            @HystrixProperty(name = "maxQueueSize", value = "15"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "16") })
    public String concurrent() {
        System.out.println(LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "concurrent";
    }

    @GetMapping("concurrent/semaphore")
    @HystrixCommand(defaultFallback = "defaultFallback", commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "18") })
    public String concurrentSemaphore() {
        System.out.println(LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "concurrent";
    }

    @GetMapping("timeout/{timeout}")
    @HystrixCommand(commandKey="springcloud", threadPoolKey="springcloud")
    public String timeoutConfigureWithSpringCloud(@PathVariable("timeout") long timeout) {
        System.out.println("Hellooooooooooooo");
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeoutConfigureWithSpringCloud";
    }
    
    @GetMapping("config/instance/{timeout}")
    @HystrixCommand
    public String configureInstanceInfoWithSpringCloud(@PathVariable("timeout") long timeout) {
        System.out.println("Hellooooooooooooo");
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "configureInstanceInfoWithSpringCloud";
    }

}
