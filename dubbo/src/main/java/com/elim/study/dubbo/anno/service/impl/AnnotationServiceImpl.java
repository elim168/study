package com.elim.study.dubbo.anno.service.impl;

import com.elim.study.dubbo.anno.service.AnnotationService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Elim
 * 19-7-16
 */
@Service(timeout = 2000)
public class AnnotationServiceImpl implements AnnotationService {

  @Override
  public void sayHello(String name) {
    System.out.println("Hello " + name);
  }

}
