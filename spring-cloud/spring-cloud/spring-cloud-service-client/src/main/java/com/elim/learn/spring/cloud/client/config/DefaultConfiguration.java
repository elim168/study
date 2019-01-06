package com.elim.learn.spring.cloud.client.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.DecodeException;
import feign.codec.Decoder;

@Configuration
public class DefaultConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("username", "password");
    }
    
//    @Bean
    public Decoder decoder() {
        return new DefaultDecoder();
    }
    
    @Bean
    public LoggerDecoder loggerDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new LoggerDecoder(messageConverters);
    }
    
//    @Bean
    public CloseableHttpClient httpClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = builder.setDefaultHeaders(Arrays.asList(new BasicHeader("abc", "1234"))).build();
        return httpClient;
    }
    
    public static class DefaultDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            return "from default decode : " + Util.toString(response.body().asReader());
        }
        
    }
    
}
