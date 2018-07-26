package com.elim.springboot.web.resttemplate;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public class MyHttpMessageConverter extends AbstractHttpMessageConverter<String> {

    @Override
    protected boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(String t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        
    }

}
