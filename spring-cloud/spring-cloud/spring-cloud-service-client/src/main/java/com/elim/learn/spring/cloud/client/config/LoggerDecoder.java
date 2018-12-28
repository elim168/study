package com.elim.learn.spring.cloud.client.config;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import feign.FeignException;
import feign.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerDecoder extends ResponseEntityDecoder {

    public LoggerDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(new SpringDecoder(messageConverters));
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Object result = super.decode(response, type);
        log.info("请求[{}]的响应内容是：{}", response.request().url(), result);
        return result;
    }
    

}
