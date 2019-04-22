package com.elim.learn.spring.cloud.function;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Elim
 * 2019/3/9
 */
public interface CustomBinding {

  @Output
  MessageChannel output1();

}
