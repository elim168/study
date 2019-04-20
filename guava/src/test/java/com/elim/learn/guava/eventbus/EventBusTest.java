package com.elim.learn.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 * @author Elim
 * 19-4-20
 */
public class EventBusTest {

  @Test
  public void test() {
    EventBus eventBus = new EventBus();
// Class is typically registered by the container.
    class EventBusChangeRecorder {
      @Subscribe
      public void recordStringEvent(String event) {
        System.out.println("收到String事件：" + event);
      }
      @Subscribe
      public void recordLongEvent(Long event) {
        System.out.println("收到Long事件：" + event);
      }

      @Subscribe
      public void recordNumberEvent(Number event) {
        //收到Long类型的事件时该监听也会触发
        System.out.println("收到数字类型的事件：" + event);
      }

      /**
       * 所有不存在订阅者的事件都将被封装为DeadEvent进行发布，所以如果想知道有哪些事件是没有订阅者的，
       * 可以订阅DeadEvent事件。
       * @param missedEvent
       */
      @Subscribe
      public void recordDeadEvent(DeadEvent missedEvent) {
        System.out.println("收到一个没有人监听的事件：" + missedEvent.getEvent() + ",toString=" + missedEvent);
      }

    }
// somewhere during initialization
    eventBus.register(new EventBusChangeRecorder());
// much later
    for (int i=0; i<5; i++) {
      eventBus.post("Event-" + i);
      eventBus.post(Long.valueOf(i));
    }
    eventBus.post(Boolean.TRUE);


  }

}
