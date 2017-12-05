/**
 * 
 */
package com.elim.learn.spring.test.integrate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.task.AnnotationTasks;

/**
 * @author Elim
 * 2017年12月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-task.xml")
public class TaskExecutorTest {

    @Autowired
    private AnnotationTasks annotationTasks;
    
    @Test
    public void test() throws Exception {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.initialize();
        for (int i=0; i<100; i++) {
            TimeUnit.MILLISECONDS.sleep(500);
            taskExecutor.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "--------" + LocalDateTime.now());
            });
        }
        TimeUnit.SECONDS.sleep(20);
    }
    
    @Test
    public void testSchedule() throws Exception {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.initialize();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(LocalDateTime.now());
        }, 1000);
        TimeUnit.SECONDS.sleep(30);
    }
    
    @Test
    public void testAnnotationTasks() throws Exception {
        System.out.println(Thread.currentThread());
        this.annotationTasks.testAsync();
        TimeUnit.SECONDS.sleep(15);
    }
    
}
