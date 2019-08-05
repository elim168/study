package com.elim.learn.spring.cloud.stream.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 声明了@Input，一定要有对应的@StreamListener接收对应的Channel的消息
 * @author Elim
 * 2019年1月7日
 */
//@EnableBinding({Inputs.class, Outputs.class})
//@EnableBinding({Source.class, Sink.class, Outputs.class})
@EnableBinding({CustomBinding.class, Sink.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
/*    @Bean
    @StreamMessageConverter
    public UserMessageConverter userMessageConverter() {
        return new UserMessageConverter();
    }*/
    
/*    @Bean
    @StreamMessageConverter
    public MappingFastJsonMessageConverter mappingFastJsonMessageConverter() {
        return new MappingFastJsonMessageConverter();
    }*/
    
/*    @StreamRetryTemplate
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5));
        return retryTemplate;
    }*/
    
}
