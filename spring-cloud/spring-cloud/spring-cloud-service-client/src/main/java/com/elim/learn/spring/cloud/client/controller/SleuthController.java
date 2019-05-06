package com.elim.learn.spring.cloud.client.controller;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import com.elim.learn.spring.cloud.client.model.Person;
import com.elim.learn.spring.cloud.client.service.SleuthLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 2019/4/28
 */
@RestController
@RequestMapping("sleuth")
public class SleuthController {

  @Autowired
  private SleuthLocalService sleuthLocalService;

  @Autowired
  private Tracer tracer;

  @Autowired
  private Tracing tracing;

  @GetMapping("span")
  @NewSpan
  public String span() throws Exception {
    Span currentSpan = this.tracer.currentSpan();
    for (int i=0; i<5; i++) {
      String key = "key" + i;
      String value = "value" + i;
      currentSpan.tag(key, value);
    }

    currentSpan.annotate("start");
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } finally {
      currentSpan.annotate("finish");
    }

    return "span";
  }

/*  @GetMapping("span")
  public String span() throws Exception {
    Span rootSpan = this.tracer.newTrace();
    rootSpan.name("自定义span名称");
    rootSpan.start();
    rootSpan.annotate("start");
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (Exception e) {
      rootSpan.error(e);
    } finally {
      rootSpan.annotate("finish");
      rootSpan.finish();
    }

    return "span";
  }*/

  @NewSpan
  @GetMapping
  public String hello() {
    return "hello";
  }

  @NewSpan("custom_span_name")
  @GetMapping("spanName")
  public String setSpanName() {
    return "spanName";
  }

  @NewSpan
  @GetMapping("tag/{tagValue}")
  public String tag(@PathVariable("tagValue") @SpanTag("tagName") String tagValue) {
    return "tag";
  }

  @GetMapping("callMethod")
  @NewSpan
  public String callMethod() {
    this.call();
    this.sleuthLocalService.method1(LocalDateTime.now().toString());
    return "callMethod";
  }

  @NewSpan
  private void call() {
    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
  }

  @NewSpan
  @GetMapping("continueSpan")
  public String continueSpan() {
    this.sleuthLocalService.method2();
    return "continueSpan";
  }

  @GetMapping("tagExpression")
  public String tagExpression() {
    Person person = new Person();
    person.setId(1000L);
    person.setName("张三");
    this.sleuthLocalService.method3(person);
    return "tag expression";
  }

  @GetMapping("threads")
  public String threads() throws Exception {
    this.sleuthLocalService.threads();
    return "threads";
  }

  @GetMapping("async")
  public String async() throws Exception {
    this.sleuthLocalService.asyncTask();
    return "async";
  }

}
