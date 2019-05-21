package com.elim.study.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 19-5-19
 */
public class BasicTest {

  private String nameServer = "localhost:9876";

  @Test
  public void testSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
    producer.start();
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", ("hello" + i).getBytes());
      SendResult sendResult = producer.send(message);
      if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
        System.out.println("消息发送成功：" + sendResult);
      } else {
        System.out.println("消息发送失败：" + sendResult);
      }
    }
    producer.shutdown();
  }

  @Test
  public void sendWithTag() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
    producer.start();
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", "tag0", ("hello with tag---" + i).getBytes());
      SendResult sendResult = producer.send(message);
      if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
        System.out.println("消息发送成功：" + sendResult);
      } else {
        System.out.println("消息发送失败：" + sendResult);
      }
    }
    producer.shutdown();
  }

  @Test
  public void sendAsync() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
    producer.start();
    CountDownLatch latch = new CountDownLatch(10);
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", "tag1", ("send by async, no." + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
      producer.send(message, new SendCallback() {
        @Override
        public void onSuccess(SendResult sendResult) {
          System.out.println("发送成功：" + message);
          latch.countDown();
        }

        @Override
        public void onException(Throwable throwable) {
          System.out.println("发送失败");
          latch.countDown();
        }
      });
    }
    latch.await();
    producer.shutdown();
  }

  @Test
  public void sendOneway() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
    producer.start();
    for (int i=0; i<10; i++) {
      Message message = new Message("topic1", "tag2", ("message send with oneway, no."+i).getBytes());
      producer.sendOneway(message);
    }
    producer.shutdown();
  }

  @Test
  public void testConsumer() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group1");
    consumer.setNamesrvAddr(nameServer);
    consumer.subscribe("topic1", "*");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + "收到了消息，数量是：" + msgs.size());
        AtomicInteger counter = new AtomicInteger();
        msgs.forEach(msg -> System.out.println(counter.incrementAndGet() + ".消息内容是：" + new String(msg.getBody())));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
  }

  @Test
  public void testConsumeByTag() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group2");
    consumer.setNamesrvAddr(nameServer);
    consumer.subscribe("topic1", "tag1||tag2");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + "收到了消息，数量是：" + msgs.size());
        AtomicInteger counter = new AtomicInteger();
        msgs.forEach(msg -> System.out.println(counter.incrementAndGet() + ".消息内容是：" + new String(msg.getBody())));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
  }

  @Test
  public void testOrderSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    for (int i=0; i<10; i++) {
      Message message = new Message("topic1", "tag3", (System.currentTimeMillis() + "---" + System.nanoTime() + "hello ordered message " + i).getBytes());
      SendResult sendResult = producer.send(message, new MessageQueueSelector() {
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
          int index = (int) arg;
          //奇数放一个队列，偶数放一个队列
          return mqs.get(index % mqs.size() % 2);
        }
      }, i);
      Assert.assertTrue(sendResult.getSendStatus() == SendStatus.SEND_OK);
    }
    producer.shutdown();
  }

  @Test
  public void testOrderConsume() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group2");
    consumer.setNamesrvAddr(this.nameServer);
    consumer.subscribe("topic1", "tag3");
//    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);//订阅以前的消息也可以接收。
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);//默认值，订阅以前的消息将被忽略
    consumer.registerMessageListener(new MessageListenerOrderly() {
      @Override
      public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        System.out.println(Thread.currentThread().getName() + "消费消息：" + new String(msgs.get(0).getBody()));
        return ConsumeOrderlyStatus.SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
    consumer.shutdown();
  }

  @Test
  public void testBroadcastSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    String topic = "topic1";
    String tag = "tag4";
    for (int i=0; i<10; i++) {
      Message message = new Message(topic, tag, ("hello-" + i).getBytes());
      producer.send(message);
    }
    producer.shutdown();
  }

  /**
   * 群集消费时，同一消费组的消费者共享队列里面的消息，同一条消息只会由其中的一个消费者消费。
   * @throws Exception
   */
  @Test
  public void testBroadcastConsume() throws Exception {
    String topic = "topic1";
    String tag = "tag4";
    String consumerGroup = "group1";
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
    consumer.setNamesrvAddr(this.nameServer);
//    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    //广播方式，同一消息可以被所有的消费者消费。
    consumer.setMessageModel(MessageModel.BROADCASTING);
    try {
      consumer.subscribe(topic, tag);
      consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
        System.out.println("consumer-3消费了消息——" + msgs.get(0).getMsgId());
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      });
      consumer.start();
    } catch (MQClientException e) {
      e.printStackTrace();
    }
    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
  }

  @Test
  public void testScheduledMessageSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    for (int i=0; i<10; i++) {
      Message message = new Message("topic1", "tag5", String.valueOf(i).getBytes());
      message.setDelayTimeLevel(4);
      producer.send(message);
    }
    producer.shutdown();
  }

  @Test
  public void testScheduledMessageConsume() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group2");
    consumer.setNamesrvAddr(this.nameServer);
    consumer.subscribe("topic1", "tag5");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        MessageExt msg = msgs.get(0);
        System.out.println(String.format("收到消息%s，延时%dms", msg.getMsgId(), System.currentTimeMillis()-msg.getStoreTimestamp()));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
    consumer.shutdown();
  }

  @Test
  public void testSendBatch() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    String topic = "topic1";
    String tag = "tag6";
    List<Message> messages = new ArrayList<>();
    for (int i=0; i<1000; i++) {
      messages.add(new Message(topic, tag, String.valueOf(i).getBytes()));
    }
    //批量发送消息，一次发送的消息总量不能超过1MB。
    producer.send(messages);
    producer.shutdown();
  }

  @Test
  public void testConsumeBatch() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr(this.nameServer);
    //指定批量消费的最大值，默认是1
    consumer.setConsumeMessageBatchMaxSize(50);
    //批量拉取消息的数量，默认是32,当consumeMessageBatchMaxSize的值超过了pullBatchSize的值时
    //批量消费的最大值不超过pullBatchSize的值。pullBatchSize的值指定超过32以后，实际拉取回来的也是32条消息。
    consumer.setPullBatchSize(15);
    consumer.subscribe("topic1", "tag6");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + "一次收到" + msgs.size() + "消息");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
    consumer.shutdown();
  }

}
