package com.elim.learn.spring.cloud.client.controller;

import java.net.URI;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @GetMapping
    public String helloWorld() {
        return this.helloService.helloWorld();
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
    
}
