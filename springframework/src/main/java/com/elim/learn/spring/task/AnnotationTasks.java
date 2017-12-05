/**
 * 
 */
package com.elim.learn.spring.task;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Elim
 * 2017年12月3日
 */
@Component
public class AnnotationTasks {

    /**
     * 异步调用的功能由Spring AOP实现，由AsyncAnnotationBeanPostProcessor定义。所以@Async标注的方法必须是public方法，
     * 且必须是外部调用。
     */
    @Async
    public void testAsync() {
        long t1 = System.currentTimeMillis();
        this.async();
        long t2 = System.currentTimeMillis();
        System.out.println(Thread.currentThread() + " 耗时：" + (t2 - t1));
    }
    
    @Async
    private void async() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Scheduled(fixedRate = 5000)
    public void schdule() {
        System.out.println("当前时间是：" + LocalDateTime.now());
    }
    
}
