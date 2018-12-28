package com.elim.learn.spring.cloud.client.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elim.learn.spring.cloud.client.config.RequestIdHolder;
import com.elim.learn.spring.cloud.client.config.MyHttpMessageConverter.MyObj;
import com.elim.learn.spring.cloud.client.service.HelloService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    private HelloService helloService;
    
    @Autowired
    private EurekaClient eurekaClient;
    
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    @Autowired
    private LoadBalancerFeignClient client;
    
    @GetMapping
    public String helloWorld() {
        System.out.println(client);
        return client + this.helloService.helloWorld();
    }
    
    @GetMapping("world")
    public String helloWorld2() throws Exception {
        String instanceUrl = this.instanceUrl();
        String serviceUrl = instanceUrl + "hello";
        String result = this.restTemplateBuilder.build().getForObject(new URI(serviceUrl), String.class);
        return result;
    }
    
    @GetMapping("instance")
    public String instanceUrl() {
        String virtualHostname = "spring-cloud-service-provider";
        InstanceInfo instanceInfo = this.eurekaClient.getNextServerFromEureka(virtualHostname, false);
        return instanceInfo.getHomePageUrl();
    }
    
    @GetMapping("instance2")
    public String instanceUrl2() {
        String serviceId = "spring-cloud-service-provider";
        List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
        if (CollectionUtils.isNotEmpty(instances)) {
            return instances.get(0).getUri().toString();
        }
        return null;
    }
    
    @GetMapping("path_variable/{pathVariable}")
    public String pathVariable(@PathVariable("pathVariable") String pathVariable) {
        return this.helloService.pathVariable(pathVariable);
    }
    
    @GetMapping("request_body")
    public String requestBody() {
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int size = random.nextInt(10) + 2;
        for (int i=0; i<size; i++) {
            map.put(UUID.randomUUID().toString(), random.nextInt(1000));
        }
        return this.helloService.requestBody(map);
    }
    
    @GetMapping("headers")
    public String headers() {
        RequestIdHolder.set(UUID.randomUUID().toString());
        return this.helloService.headers();
    }
    
    @GetMapping("timeout/{timeout}")
    public String timeout(@PathVariable("timeout") int timeout) {
        return this.helloService.timeout(timeout);
    }
    
    @GetMapping("converter")
    public Object converter() {
        String now = LocalDateTime.now().toString();
        return this.helloService.customHttpMessageConverter(new MyObj("hello---" + now));
    }
    
}
