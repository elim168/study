package com.elim.study.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.PullResult;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Elim
 * 19-5-19
 */
@Slf4j
public class BasicTest {

  private String nameServer = "localhost:9876";

  @Test
  public void testSend() throws Exception {
    //指定Producer的Group为group1
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    //指定需要连接的Name Server
    producer.setNamesrvAddr(nameServer);
    producer.setRetryTimesWhenSendFailed(1);
    //发送消息前必须调用start()，其内部会进行一些初始化工作。
    producer.start();
    for (int i = 0; i < 10; i++) {
      //指定消息发送的Topic是topic1。
      Message message = new Message("topic1", ("hello" + i).getBytes());
      //同步发送，发送成功后才会返回
      SendResult sendResult = producer.send(message);
      if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
        System.out.println("消息发送成功：" + sendResult);
      } else {
        System.out.println("消息发送失败：" + sendResult);
      }
    }
    //使用完毕后需要把Producer关闭，以释放相应的资源
    producer.shutdown();
  }

  @Test
  public void sendWithTag() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
    producer.start();
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", "1".getBytes());
      message.setTags("tag8");
      SendResult sendResult = producer.send(message);
      if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
        System.out.println("消息发送成功：" + sendResult);
      } else {
        System.out.println("消息发送失败：" + sendResult);
      }
    }
    TimeUnit.SECONDS.sleep(10);
    producer.shutdown();
  }

  @Test
  public void sendAsync() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(nameServer);
//  producer.setRetryTimesWhenSendAsyncFailed(1);
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
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", "tag2", ("message send with oneway, no." + i).getBytes());
      producer.sendOneway(message);
    }
    producer.shutdown();
  }

  @Test
  public void testConsumer() throws Exception {
    //创建Consumer并指定消费者组。
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group1");
    //指定需要连接的Name Server
    consumer.setNamesrvAddr(nameServer);
    //订阅topic1上的所有Tag。
    consumer.subscribe("topic1", "*");
    //注册一个消息监听器
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + "收到了消息，数量是：" + msgs.size());
        AtomicInteger counter = new AtomicInteger();
        msgs.forEach(msg -> System.out.println(counter.incrementAndGet() + ".消息内容是：" + new String(msg.getBody())));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    //启动消费者
    consumer.start();
    //为了确保Junit线程不立即死掉。
    TimeUnit.SECONDS.sleep(12000);
  }

  @Test
  public void testConsumeByTag() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group2");
    consumer.setNamesrvAddr(nameServer);
    consumer.subscribe("topic1", "tag0||tag1||tag2");
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
public void testSendWithKeys() throws Exception {
  DefaultMQProducer producer = new DefaultMQProducer("group1");
  producer.setNamesrvAddr(nameServer);
  producer.start();
  for (int i = 0; i < 10; i++) {
    Message message = new Message("topic1", ("message-" + i).getBytes());
    message.setTags("tag0");
//    message.setKeys(String.valueOf(i));
    message.setKeys(Arrays.asList(i, i+1, i+2).stream().map(String::valueOf).collect(Collectors.toList()));
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
  public void testOrderSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    for (int i = 0; i < 5; i++) {
      Message message = new Message("topic1", "tag8", (System.currentTimeMillis() + "---" + System.nanoTime() + "hello ordered message " + i).getBytes());
      SendResult sendResult = producer.send(message, new MessageQueueSelector() {
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
          int index = (int) arg;
          //奇数放一个队列，偶数放一个队列
//        return mqs.get(index % mqs.size() % 2);
          return mqs.get(0);
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
    //有序消费时同一个队列里面的消息会按照顺序进行消费，它们可能被不同的线程消费，每一个线程在消费消息时都将锁定当前队列。
    //如消息的顺序是1/2/3/4/5/6，则按照顺序消费可以保证消息的消费顺序一定是1/2/3/4/5/6，但是消费它们的线程有可能是线程6/5/4/3/2/1。
    //如果要保证有序的消费是在同一个线程完成的，则消费者线程只能有一个。
//    consumer.setConsumeThreadMax(1);
//    consumer.setConsumeThreadMin(1);
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
    for (int i = 0; i < 10; i++) {
      Message message = new Message(topic, tag, ("hello-" + i).getBytes());
      producer.send(message);
    }
    producer.shutdown();
  }

  /**
   * 群集消费时，同一消费组的消费者共享队列里面的消息，同一条消息只会由其中的一个消费者消费。
   *
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
    for (int i = 0; i < 10; i++) {
      Message message = new Message("topic1", "tag5", String.valueOf(i + 1).getBytes());
      message.setDelayTimeLevel(i + 1);
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
        System.out.println(String.format("收到消息%s，延时%dms，内容：%s", msg.getMsgId(), System.currentTimeMillis() - msg.getBornTimestamp(), new String(msg.getBody())));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(1200);
    consumer.shutdown();
  }

  @Test
  public void testSendBatch() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.setMaxMessageSize(10 * 1024 * 1024);
    producer.start();
    String topic = "topic1";
    String tag = "tag6";
    List<Message> messages = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      messages.add(new Message(topic, tag, new byte[1024 * 1024]));
    }
    //批量发送消息，一次发送的消息总量不能超过4MB。
    producer.send(messages);
    producer.shutdown();
  }

  @Test
  public void testConsumeBatch() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr(this.nameServer);
    //指定批量消费的最大值，默认是1
    consumer.setConsumeMessageBatchMaxSize(5);
    //批量拉取消息的数量，默认是32,当consumeMessageBatchMaxSize的值超过了pullBatchSize的值时
    //批量消费的最大值不超过pullBatchSize的值。pullBatchSize的值指定超过32以后，实际拉取回来的也是                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        32条消息。
    consumer.setPullBatchSize(30);
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

  @Test
  public void testFilterSend() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.start();
    String topic = "topic1";
    String tag = "tag6";
    for (int i = 0; i < 1000; i++) {
      Message message = new Message(topic, tag, String.valueOf(i).getBytes());
      message.putUserProperty("abc", String.valueOf(i));
      producer.send(message);
    }
    producer.shutdown();
  }

  @Test
  public void testFilterConsume() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr(this.nameServer);
    //默认是不支持通过sql92过滤的，需要在broker.conf中指定enablePropertyFilter=true。
    consumer.subscribe("topic1", MessageSelector.bySql("abc > 300 and abc between 250 and 381 "));
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println("消费消息：" + msgs.get(0).getUserProperty("abc"));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(30);
    consumer.shutdown();
  }

  /**
   * 可以通过logback把日志都作为消息发送到RocketMQ。
   *
   * @throws Exception
   */
  @Test
  public void logAppenderSend() throws Exception {
    for (int i = 0; i < 10; i++) {
      log.info("日志输出信息------" + i);
    }
    TimeUnit.SECONDS.sleep(10);
  }

  @Test
  public void logAppenderConsume() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr(this.nameServer);
    consumer.subscribe("topic1", "logback");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println("消费消息：" + new String(msgs.get(0).getBody()));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(1200);
    consumer.shutdown();
  }

  /**
   * commit后的消息消费者才能进行消费
   *
   * @throws Exception
   */
  @Test
  public void transactionalSend() throws Exception {
    TransactionMQProducer producer = new TransactionMQProducer("group1");
    producer.setNamesrvAddr(this.nameServer);
    producer.setTransactionListener(new TransactionListener() {
      @Override
      public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Integer attach = (Integer) arg;
        if (attach % 6 == 0) {
          return LocalTransactionState.COMMIT_MESSAGE;
        } else if (attach % 4 == 0) {
          return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
      }

      @Override
      public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        //executeLocalTransaction返回UNKNOW时将转而调用checkLocalTransaction。
        int i = Integer.parseInt(new String(msg.getBody()));
        System.out.println("checkLocalTransaction ----" + i);
        if (i % 10 == 0) {
          return LocalTransactionState.COMMIT_MESSAGE;
        } else if (i % 7 == 0) {
          return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
      }
    });
    producer.start();
    for (int i = 0; i < 100; i++) {
      Message message = new Message("topic1", "transactional", String.valueOf(i).getBytes());
      producer.sendMessageInTransaction(message, i);
    }
    TimeUnit.SECONDS.sleep(60);
    producer.shutdown();
  }

  @Test
  public void transactionalConsume() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.setNamesrvAddr(this.nameServer);
    consumer.subscribe("topic1", "transactional");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println("消费了一条消息——" + new String(msgs.get(0).getBody()));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.SECONDS.sleep(120);
    consumer.shutdown();
  }

  @Test
  public void testConsumeFailure() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    consumer.subscribe("topic1", "tag7");
    AtomicInteger counter = new AtomicInteger();
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(counter.incrementAndGet() + "." + LocalDateTime.now() + "收到一条消息——" + msgs.get(0).getMsgId());
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
      }
    });
    consumer.start();
    TimeUnit.HOURS.sleep(5);
  }

  @Test
  public void testPullConsumeRetry() throws Exception {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("group1_pull");
    consumer.setNamesrvAddr(this.nameServer);
    String topic = "%RETRY%group1_pull";
    consumer.start();

    //获取Topic对应的消息队列
    Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(topic);
    System.out.println(messageQueues);
    int maxNums = 10;//每次拉取消息的最大数量
    while (true) {
      boolean found = false;
      for (MessageQueue messageQueue : messageQueues) {
        long offset = consumer.fetchConsumeOffset(messageQueue, false);
        PullResult pullResult = consumer.pull(messageQueue, "*", offset, maxNums);
        switch (pullResult.getPullStatus()) {
          case FOUND:
            found = true;
            List<MessageExt> msgs = pullResult.getMsgFoundList();
            System.out.println(messageQueue.getQueueId() + "收到了消息，数量----" + msgs.size());
            for (MessageExt msg : msgs) {
              System.out.println(messageQueue.getQueueId() + "处理消息——" + msg.getMsgId());
            }
            long nextOffset = pullResult.getNextBeginOffset();
            consumer.updateConsumeOffset(messageQueue, nextOffset);
            break;
          case NO_NEW_MSG:
            System.out.println("没有新消息");
            break;
          case NO_MATCHED_MSG:
            System.out.println("没有匹配的消息");
            break;
          case OFFSET_ILLEGAL:
            System.err.println("offset错误");
            break;
        }
      }
      if (!found) {//没有一个队列中有新消息，则暂停一会。
        TimeUnit.MILLISECONDS.sleep(5000);
      }
    }
  }

  @Test
  public void testPullConsumer() throws Exception {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("group1_pull");
    consumer.setNamesrvAddr(this.nameServer);
    String topic = "topic1";
    consumer.start();

    //获取Topic对应的消息队列
    Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(topic);
    System.out.println(messageQueues);
    int maxNums = 10;//每次拉取消息的最大数量
    while (true) {
      boolean found = false;
      for (MessageQueue messageQueue : messageQueues) {
        long offset = consumer.fetchConsumeOffset(messageQueue, false);
        PullResult pullResult = consumer.pull(messageQueue, "tag8", offset, maxNums);
//      PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, "tag8", offset, maxNums);
        switch (pullResult.getPullStatus()) {
          case FOUND:
            found = true;
            List<MessageExt> msgs = pullResult.getMsgFoundList();
            System.out.println(messageQueue.getQueueId() + "收到了消息，数量----" + msgs.size());
            for (MessageExt msg : msgs) {
              System.out.println(messageQueue.getQueueId() + "处理消息——" + msg.getMsgId());
              if (new Random().nextInt(10) % 3 == 0) {
                consumer.sendMessageBack(msg, 3);
                System.out.println("消息消费失败----" + msg.getMsgId());
              }
            }
            long nextOffset = pullResult.getNextBeginOffset();
            consumer.updateConsumeOffset(messageQueue, nextOffset);
            break;
          case NO_NEW_MSG:
            System.out.println("没有新消息");
            break;
          case NO_MATCHED_MSG:
            System.out.println("没有匹配的消息");
            break;
          case OFFSET_ILLEGAL:
            System.err.println("offset错误");
            break;
        }
      }
      if (!found) {//没有一个队列中有新消息，则暂停一会。
        TimeUnit.MILLISECONDS.sleep(5000);
      }
    }
  }

  @Test
  public void testPushConsumer() throws Exception {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_push");
    consumer.setNamesrvAddr(this.nameServer);
    consumer.subscribe("topic1", "tag8");
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    AtomicInteger counter = new AtomicInteger();
    Set<String> threads = new HashSet<>();
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
          TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        threads.add(Thread.currentThread().getName());
        System.out.println(counter.incrementAndGet() + ".收到消息——" + msgs.get(0).getMsgId() + "---" + threads.size());
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    TimeUnit.MINUTES.sleep(10);
    consumer.shutdown();
  }

  @Test
  public void testProducer() throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    producer.setMaxMessageSize(1024 * 1024);
    producer.setRetryTimesWhenSendFailed(3);
    producer.setRetryTimesWhenSendAsyncFailed(3);
    producer.setCompressMsgBodyOverHowmuch(1024 * 100);
    producer.setSendMsgTimeout(1000);
    producer.setNamesrvAddr(nameServer);
    producer.setAsyncSenderExecutor(Executors.newFixedThreadPool(10));
    producer.start();
    //指定消息发送的Topic是topic1。
    Message message = new Message("topic1", "tag9", "hello producer".getBytes());
    //同步发送，发送成功后才会返回
    SendResult sendResult = producer.send(message);
//同步发送指定超时时间
    producer.send(message, 5000);
//异步发送指定超时时间
    producer.send(message, new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {

      }

      @Override
      public void onException(Throwable e) {

      }
    }, 5000);
    if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
      System.out.println("消息发送成功：" + sendResult);
    } else {
      System.out.println("消息发送失败：" + sendResult);
    }
    //使用完毕后需要把Producer关闭，以释放相应的资源
    producer.shutdown();
  }

}
