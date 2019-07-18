package com.elim.study.dubbo.anno.service.impl;

import com.elim.study.dubbo.anno.service.AnnotationService;
import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-7-16
 */
@Service(timeout = 2000, methods = @Method(name = "sayHello", timeout = 1000, retries = 0))
public class AnnotationServiceImpl implements AnnotationService {

  @Override
  public String sayHello(String name) {
    System.out.println("Hello " + name);
    if (name.length() > 5) {
      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return "Hello " + name;
  }

}
