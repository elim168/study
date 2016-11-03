/**
 * 
 */
package com.elim.learn.elastic.job.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Elim
 * 2016年10月29日
 */
public class ElasticJobTest {

	@Test
	public void test() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		System.out.println(context);
		System.in.read();
	}
	
	@Test
	public void testSwitch() {
		int i=1;
		switch (i) {
		case 0:
			System.out.println(0);
		case 1:
			System.out.println(1);
		case 2:
			System.out.println(2);
			break;
		case 3:
			System.out.println(3);
		case 4:
			System.out.println(4);
		case 5:
			System.out.println(5);
		}
	}
	
	//testSwitch代码的说明
	/*
	 0  iconst_1
     1  istore_1 [i]
     2  iload_1 [i]
     3  tableswitch default: 85
          case 0: 40
          case 1: 47
          case 2: 54
          case 3: 64
          case 4: 71
          case 5: 78
    40  getstatic java.lang.System.out : java.io.PrintStream [27]
    43  iconst_0
    44  invokevirtual java.io.PrintStream.println(int) : void [52]
    47  getstatic java.lang.System.out : java.io.PrintStream [27]
    50  iconst_1
    51  invokevirtual java.io.PrintStream.println(int) : void [52]
    54  getstatic java.lang.System.out : java.io.PrintStream [27]
    57  iconst_2
    58  invokevirtual java.io.PrintStream.println(int) : void [52]
    61  goto 85
    64  getstatic java.lang.System.out : java.io.PrintStream [27]
    67  iconst_3
    68  invokevirtual java.io.PrintStream.println(int) : void [52]
    71  getstatic java.lang.System.out : java.io.PrintStream [27]
    74  iconst_4
    75  invokevirtual java.io.PrintStream.println(int) : void [52]
    78  getstatic java.lang.System.out : java.io.PrintStream [27]
    81  iconst_5
    82  invokevirtual java.io.PrintStream.println(int) : void [52]
    85  return
	   
	   
	   
	testSwitch方法内部的内容会生成如上的字节码内容，我们可以看到从编号为3的那一行开始执行的是switch的逻辑，当我们的i为0时就会跳转到第40行开始执行，然后会依次执行40、43、44、47、50、51、54、57、58、61行，
	然后再61行就跳出到85行了，也就是跳出switch了，这里的61行对应的就是case 2下面的那个break。所以如果我们在使用switch的时候，如果不在case里面加break，那么当其条件满足时会自动执行下面其它case的代码，直到
	switch语句结束或者遇到了break语句。   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	 */
	
}
