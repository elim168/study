package com.elim.study.basic.cpucost;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 写一个CPU耗时高的线程，然后检测它。<br/>
 *
 * 1. 通过top查看CPU占用率比较高的进程。
 *
 * <pre>
 *   任务: 246 total,   1 running, 187 sleeping,   0 stopped,   0 zombie
 * %Cpu(s): 67.5 us, 15.4 sy,  0.0 ni, 16.4 id,  0.0 wa,  0.0 hi,  0.7 si,  0.0 st
 * KiB Mem :  8060080 total,  1316624 free,  3031332 used,  3712124 buff/cache
 * KiB Swap:  2097148 total,  2097148 free,        0 used.  4473492 avail Mem
 *
 * 进�� USER      PR  NI    VIRT    RES    SHR �  %CPU %MEM     TIME+ COMMAND
 *  4657 elim      20   0 5047660 1.382g  57628 S 241.7 18.0  14:15.61 java
 * 14992 elim      20   0 5317392 165360  17468 S  85.3  2.1   1:18.22 java
 *  2061 elim      20   0  513588  70364  55844 S   1.7  0.9   0:57.67 Xorg
 * 12848 elim      20   0  819180  44072  32648 S   1.3  0.5   0:00.50 gnome-terminal-
 *  2527 elim      20   0 3253460 211752  92952 S   1.0  2.6   0:42.49 gnome-shell
 * 12867 elim      20   0 2835020 125976  17416 S   0.7  1.6   0:05.33 java
 *  1408 mongodb   20   0 1017912  71060  34312 S   0.3  0.9   0:06.99 mongod
 * 15626 elim      20   0   43700   3800   3148 R   0.3  0.0   0:00.17 top
 * 15791 elim      20   0  853156 186236 108788 S   0.3  2.3   0:15.95 chrome
 * 32274 root      20   0       0      0      0 I   0.3  0.0   0:00.12 kworker/1:0
 *     1 root      20   0  225768   9408   6660 S   0.0  0.1   0:07.80 systemd
 *     2 root      20   0       0      0      0 S   0.0  0.0   0:00.00 kthreadd
 *     4 root       0 -20       0      0      0 I   0.0  0.0   0:00.00 kworker/0:0H
 *     6 root       0 -20       0      0      0 I   0.0  0.0   0:00.00 mm_percpu_wq
 *     7 root      20   0       0      0      0 S   0.0  0.0   0:00.19 ksoftirqd/0
 *     8 root      20   0       0      0      0 I   0.0  0.0   0:01.24 rcu_sched
 *     9 root      20   0       0      0      0 I   0.0  0.0   0:00.00 rcu_bh
 *    10 root      rt   0       0      0      0 S   0.0  0.0   0:00.02 migration/0
 *    11 root      rt   0       0      0      0 S   0.0  0.0   0:00.00 watchdog/0
 *    12 root      20   0       0      0      0 S   0.0  0.0   0:00.00 cpuhp/0
 *    13 root      20   0       0      0      0 S   0.0  0.0   0:00.00 cpuhp/1
 * </pre>
 *
 * 2. 从上面查看需要检测的程序的进程号，上面第一个是IDEA的，第二个才是我们手写的程序，所以这里选择第二个进程。
 * 运行top -Hp <pid>，即运行top -Hp 14992。可以看到该进程下每一个线程的CPU耗时情况。
 *
 * <pre>
 *   elim@elim-pc:~$ top -Hp 14992
 *
 * top - 08:23:04 up 42 min,  1 user,  load average: 3.79, 3.35, 1.93
 * Threads:  28 total,   1 running,  27 sleeping,   0 stopped,   0 zombie
 * %Cpu(s): 66.0 us, 16.3 sy,  0.0 ni, 16.9 id,  0.0 wa,  0.0 hi,  0.8 si,  0.0 st
 * KiB Mem :  8060080 total,  1267992 free,  3090432 used,  3701656 buff/cache
 * KiB Swap:  2097148 total,  2097148 free,        0 used.  4428704 avail Mem
 *
 * 进�� USER      PR  NI    VIRT    RES    SHR � %CPU %MEM     TIME+ COMMAND
 * 15027 elim      20   0 5317392 165360  17468 R 82.3  2.1   5:06.02 java
 * 14997 elim      20   0 5317392 165360  17468 S  0.3  2.1   0:00.56 java
 * 15001 elim      20   0 5317392 165360  17468 S  0.3  2.1   0:00.55 java
 * 14992 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 14996 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.10 java
 * 14998 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.56 java
 * 15000 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.55 java
 * 15007 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.45 java
 * 15008 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 15009 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 15010 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 15011 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 15012 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.08 java
 * 15013 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.07 java
 * 15014 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15015 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.00 java
 * 15016 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.13 java
 * 15017 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.06 java
 * 15018 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.06 java
 * 15019 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.06 java
 * 15020 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15021 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15022 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15023 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.06 java
 * 15024 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.06 java
 * 15025 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15026 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.05 java
 * 15034 elim      20   0 5317392 165360  17468 S  0.0  2.1   0:00.01 java
 * </pre>
 *
 * 3. 选择一个CPU耗时比较高的线程号把它替换为16进制表示（JVM内部是16进制表示的），运行printf '%x\n' <tid>，即printf '%x\n' 15027。
 *
 * <pre>
 *   elim@elim-pc:~$ printf '%x\n' 15027
 *   3ab3
 * </pre>
 * 转换后的耗时比较高的线程号的16进制表示为3ab3。<br/>
 *
 * 4. 然后通过jstack拿到该Java进程的堆栈信息，可以把它输出为一个文件，也可以直接通过管道查找高CPU占用的线程当前正在执行的代码。
 *
 * <pre>
 *   elim@elim-pc:~$ jstack 14992 | grep -A30 3ab3
 * "Busy-Thread" #20 prio=5 os_prio=0 tid=0x00007feb8c2a5000 nid=0x3ab3 runnable [0x00007feb6613b000]
 *    java.lang.Thread.State: RUNNABLE
 * 	at java.io.FileOutputStream.writeBytes(Native Method)
 * 	at java.io.FileOutputStream.write(FileOutputStream.java:326)
 * 	at java.io.BufferedOutputStream.flushBuffer(BufferedOutputStream.java:82)
 * 	at java.io.BufferedOutputStream.flush(BufferedOutputStream.java:140)
 * 	- locked <0x0000000085064cb8> (a java.io.BufferedOutputStream)
 * 	at java.io.PrintStream.write(PrintStream.java:482)
 * 	- locked <0x0000000085004908> (a java.io.PrintStream)
 * 	at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
 * 	at sun.nio.cs.StreamEncoder.implFlushBuffer(StreamEncoder.java:291)
 * 	at sun.nio.cs.StreamEncoder.flushBuffer(StreamEncoder.java:104)
 * 	- locked <0x0000000085064d88> (a java.io.OutputStreamWriter)
 * 	at java.io.OutputStreamWriter.flushBuffer(OutputStreamWriter.java:185)
 * 	at java.io.PrintStream.write(PrintStream.java:527)
 * 	- eliminated <0x0000000085004908> (a java.io.PrintStream)
 * 	at java.io.PrintStream.print(PrintStream.java:669)
 * 	at java.io.PrintStream.println(PrintStream.java:806)
 * 	- locked <0x0000000085004908> (a java.io.PrintStream)
 * 	at com.elim.study.basic.cpucost.HighCpuCost.lambda$main$1(HighCpuCost.java:38)
 * 	at com.elim.study.basic.cpucost.HighCpuCost$$Lambda$2/381259350.run(Unknown Source)
 * 	at java.lang.Thread.run(Thread.java:748)
 * </pre>
 *
 * 从上可以看到高CPU占用的线程正在执行HighCpuCost.java的第38行代码。找到了代码位置后可以结合代码做进一步的分析。
 *
 * @author Elim
 * 21-5-18
 */
public class HighCpuCost {

  public static void main(String[] args) {


    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (int i=0; i<100; i++) {
      final int no = i;
      executorService.submit(() -> {
        while (true) {
          System.out.println("Hello,Normal Thread is Running! No." + no);
          try {
            TimeUnit.MILLISECONDS.sleep(500 + no * 5);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }

    // Busy Thread
    Thread busyThread = new Thread(() -> {
      AtomicInteger increment = new AtomicInteger();
      while(true) {
        int value = increment.incrementAndGet();
        System.out.println("Current Value is " + value);
        if (value == Integer.MAX_VALUE) {
          increment.set(0);
        }
      }
    }, "Busy-Thread");

    busyThread.start();

  }

}
