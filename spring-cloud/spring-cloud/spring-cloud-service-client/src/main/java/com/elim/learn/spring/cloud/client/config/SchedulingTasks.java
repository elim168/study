package com.elim.learn.spring.cloud.client.config;

import com.sun.xml.internal.ws.spi.db.DatabindingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Elim
 * 2019/5/6
 */
@Component
public class SchedulingTasks {

  @Scheduled(fixedDelay = 1000 * 60)
  public void task1() {
    System.out.println(new Date() + "--------");
  }

}
