/**
 * 
 */
package com.elim.learn.spring.test.integrate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.task.AnnotationTasks;
import com.elim.learn.spring.task.AsyncClass;

/**
 * @author Elim
 * 2017年12月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-task.xml")
public class TaskExecutorTest {

    @Autowired
    private AnnotationTasks annotationTasks;
    @Autowired
    private AsyncClass asyncClass;
    
    @Autowired
    @Qualifier("myExecutor")
    private TaskExecutor taskExecutor;
    
    @Autowired
    private TaskScheduler taskScheduler;
    
    @Test
    public void testTaskExecutor() throws Exception {
        for (int i=0; i<20; i++) {
            this.taskExecutor.execute(() -> {
                System.out.println("Task.......Running in " + Thread.currentThread());
            });
        }
        TimeUnit.SECONDS.sleep(5);
    }
    
    @Test
    public void testTaskScheduler() throws Exception {
        this.taskScheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task1----" + LocalDateTime.now());
        }, 1000);
        this.taskScheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task2----" + LocalDateTime.now());
        }, 1000);
        TimeUnit.SECONDS.sleep(10);
    }
    
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
                System.out.println(Thread.currentThread() + "--------" + LocalDateTime.now());
            });
        }
        TimeUnit.SECONDS.sleep(20);
    }
    
    @Test
    public void testScheduleConfig() throws Exception {
        TimeUnit.SECONDS.sleep(60);
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
        this.annotationTasks.testAsync2();
        TimeUnit.SECONDS.sleep(15);
    }
    
    @Test
    public void testAsyncClass() throws Exception {
        this.asyncClass.print();
        TimeUnit.SECONDS.sleep(1);
    }
    
}
