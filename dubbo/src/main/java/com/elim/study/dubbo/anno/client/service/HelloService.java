package com.elim.study.dubbo.anno.client.service;

import com.elim.study.dubbo.anno.service.AnnotationService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * 正常的Spring bean
 * @author Elim
 * 19-7-16
 */
@Component
public class HelloService {

  @Reference
  private AnnotationService annotationService;

  public void sayHello(String name) {
    this.annotationService.sayHello(name);
  }

}
