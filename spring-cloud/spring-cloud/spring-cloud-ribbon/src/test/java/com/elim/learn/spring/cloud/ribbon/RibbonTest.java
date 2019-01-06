package com.elim.learn.spring.cloud.ribbon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class RibbonTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @Test
    public void test() {
        String serviceId = "hello";
        for (int i=0; i<5; i++) {
            ServiceInstance instance = this.loadBalancerClient.choose(serviceId);
            System.out.println(i + ". " + instance.getUri());
        }
    }
    
}
