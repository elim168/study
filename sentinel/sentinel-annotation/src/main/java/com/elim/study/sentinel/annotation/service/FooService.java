package com.elim.study.sentinel.annotation.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.elim.study.sentinel.annotation.ResourceConstant;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-11-23
 */
@Service
public class FooService {

  @SentinelResource(value = ResourceConstant.RESOURCE_1)
  public void resource1() {
    this.sleepMillis(1000);
  }

  @SentinelResource(ResourceConstant.RESOURCE_2)
  public void sleepMillis(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
