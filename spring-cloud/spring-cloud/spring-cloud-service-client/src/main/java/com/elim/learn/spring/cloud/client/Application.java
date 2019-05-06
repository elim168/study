package com.elim.learn.spring.cloud.client;

import brave.sampler.RateLimitingSampler;
import brave.sampler.Sampler;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "123456");
    }

    @Bean
    public Sampler sampler() {
        int tracePerSecond = 1;
        return RateLimitingSampler.create(tracePerSecond);
//        return new MySampler();
    }

    public class MySampler extends Sampler {

        private Sampler sampler = RateLimitingSampler.create(1);

        @Override
        public boolean isSampled(long l) {
            boolean sampled = this.sampler.isSampled(l);
            System.out.println(l + "-----------" + sampled);
            return sampled;
        }
    }

}
