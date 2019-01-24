package com.elim.learn.spring.cloud.client.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.elim.learn.spring.cloud.client.config.MyHttpMessageConverter.MyObj;

//@FeignClient(name="${feign.client.hello}", configuration=HelloFeignConfiguration.class)
//@FeignClient(name="${feign.client.hello}", fallback=HelloServiceFallback.class)
@FeignClient(name="${feign.client.hello}", fallback=HelloServiceFallback.class, fallbackFactory=HelloServiceFallbackFactory.class)
//@FeignClient(name="${feign.client.hello}", url = "localhost:8900")//指定了url的将访问指定的Url，而不是从Eureka获取服务地址
public interface HelloService {

    @GetMapping("hello")
    String helloWorld();
    
    @GetMapping("hello/path_variable/{pathVariable}")
    String pathVariable(@PathVariable("pathVariable") String pathVariable);
    
    @PostMapping("hello/request_body")
    String requestBody(@RequestBody Map<String, Object> body);
    
    @GetMapping("api/hello/abc")
    String headers();
    
    @GetMapping("hello/timeout/{timeout}")
    String timeout(@PathVariable("timeout") int timeout);
    
    @PostMapping("hello/converter")
    MyObj customHttpMessageConverter(@RequestBody MyObj obj);
    
}
