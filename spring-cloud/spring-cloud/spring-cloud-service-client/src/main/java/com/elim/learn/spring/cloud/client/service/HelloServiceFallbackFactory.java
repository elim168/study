package com.elim.learn.spring.cloud.client.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.client.config.MyHttpMessageConverter.MyObj;

import feign.hystrix.FallbackFactory;

@Component
public class HelloServiceFallbackFactory implements FallbackFactory<HelloService> {

    @Override
    public HelloService create(Throwable cause) {
        return new HelloService() {

            @Override
            public String helloWorld() {
                return "fallback for helloWorld，reason is：" + cause.getMessage();
            }

            @Override
            public String pathVariable(String pathVariable) {
                return "fallback for pathVariable";
            }

            @Override
            public String requestBody(Map<String, Object> body) {
                return "fallback for requestBody";
            }

            @Override
            public String headers() {
                return "fallback for headers";
            }

            @Override
            public String timeout(int timeout) {
                return "fallback for timeout, reason is :" + cause.getMessage();
            }

            @Override
            public MyObj customHttpMessageConverter(MyObj obj) {
                return new MyObj("fallback for customHttpMessageConverter, reason is :" + cause.getMessage());
            }
            
        };
    }

}
