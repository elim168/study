package com.elim.learn.spring.cloud.client.config;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.cloud.client.config.MyHttpMessageConverter.MyObj;

import lombok.Data;

@Component
public class MyHttpMessageConverter extends AbstractHttpMessageConverter<MyObj> {

    private final StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    
    public MyHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MyObj.class);
    }

    @Override
    protected MyObj readInternal(Class<? extends MyObj> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        String text = this.stringHttpMessageConverter.read(String.class, inputMessage);
        return new MyObj(text);
    }

    @Override
    protected void writeInternal(MyObj t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        this.stringHttpMessageConverter.write(t.getText(), MediaType.TEXT_PLAIN, outputMessage);
    }
    
    @Data
    public static class MyObj {
        private final String text;
    }
    
}
