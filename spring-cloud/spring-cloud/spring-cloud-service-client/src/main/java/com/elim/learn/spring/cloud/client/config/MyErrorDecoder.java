package com.elim.learn.spring.cloud.client.config;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);
        log.error("请求{}调用方法{}异常，状态码：{}", response.request().url(), methodKey, response.status());
        return exception;
    }

}
