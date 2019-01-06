package com.elim.springboot.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class MvcConfiguration {

	@Bean
	public HttpMessageConverters httpMessageConverters() {
		HttpMessageConverter<?> stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<HttpMessageConverter<?>> additional = new ArrayList<>();
		additional.add(stringHttpMessageConverter);
		HttpMessageConverters converters = new HttpMessageConverters(true, additional);
		return converters;
	}
	
	@Component
	@Slf4j
	public static class CustomHttpMessageConverter implements HttpMessageConverter<String> {

		@Override
		public boolean canRead(Class<?> clazz, MediaType mediaType) {
			log.info("Test...............");
			return false;
		}

		@Override
		public boolean canWrite(Class<?> clazz, MediaType mediaType) {
			log.info("Test...............");
			return false;
		}

		@Override
		public List<MediaType> getSupportedMediaTypes() {
			log.info("Test...............");
			return Arrays.asList(MediaType.ALL);
		}

		@Override
		public String read(Class<? extends String> clazz, HttpInputMessage inputMessage)
				throws IOException, HttpMessageNotReadableException {
			log.info("Test...............");
			return null;
		}

		@Override
		public void write(String t, MediaType contentType, HttpOutputMessage outputMessage)
				throws IOException, HttpMessageNotWritableException {
			log.info("Test...............");
		}
		
	}
	
}
