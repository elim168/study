package com.elim.learn.spring.cloud.client;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.context.annotation.Bean;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloFeignConfiguration {

    @Bean
    public Decoder decoder() {
        return new HelloDecoder();
    }
    
    public static class HelloDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            log.info("receive message, type is {}", type);
            return "from hello decoder : " + Util.toString(response.body().asReader());
        }
        
    }
    
}
