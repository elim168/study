/**
 *
 */
package com.elim.study.dubbo.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 2016年10月11日
 *
 */
public class UserServiceImpl implements UserService {

  private AtomicInteger counter = new AtomicInteger(0);

  /* (non-Javadoc)
   * @see UserService#sayHello(java.lang.String)
   */
  @Override
  public void sayHello(String name) {
    System.out.println(counter.getAndIncrement() + "Hello " + name + "--" + Thread.currentThread().getName());
    try {
      TimeUnit.MILLISECONDS.sleep(200);
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see UserService#cacheTest(int)
   */
  @Override
  public String cacheTest(int id) {
    return "id: " + id + ", counter: " + counter.incrementAndGet();
  }

}
