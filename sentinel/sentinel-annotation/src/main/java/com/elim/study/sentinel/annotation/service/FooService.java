package com.elim.study.sentinel.annotation.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.elim.study.sentinel.annotation.ResourceConstant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

  @SentinelResource(value = ResourceConstant.RESOURCE_3,
          blockHandler = "resource3BlockHandler",
          fallback = "resource3Fallback")
  public void resource3(long id) {
    this.sleepMillis(80);
    if (id % 6 == 0) {
      throw new IllegalStateException("Fallback Test !");
    }
    System.out.println("resource3 =------------------ id=" + id);
  }

  /**
   * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
   */
  public void resource3BlockHandler(long id, BlockException e) {
    System.out.println(",resource3 blocked-------" + id + "-------" + e);
  }

  /**
   * 可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
   * fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。
   * fallback 函数签名和位置要求：
   *
   *     返回值类型必须与原函数返回值类型一致；
   *     方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
   *     fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，
   *     则可以指定 fallbackClass 为对应的类的 Class 对象，
   *     注意对应的函数必需为 static 函数，否则无法解析。
   */
  public void resource3Fallback(long id, Throwable e) {
    System.out.println("resource3 fallbacked-----------" + id + "--------------" + LocalDateTime.now());
  }

}
