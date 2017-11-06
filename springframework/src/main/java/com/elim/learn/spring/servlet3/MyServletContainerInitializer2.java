/**
 * 
 */
package com.elim.learn.spring.servlet3;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

/**
 * Servlet3通过程序代码来注册Servlet/Listener/Filter的机制。需要通过SPI的机制在META-INF/services目录下
 * 创建javax.servlet.ServletContainerInitializer文件，并在其中填写ServletContainerInitializer的实现类，
 * 每个类写一行。
 * 如果你需要包装一下，开放一个公用的接口供注册Http对象，那么你也可以在ServletContainerInitializer实现类上使用@HandlesTypes
 * 来指定需要自动发现的类型，它们会在调用onStartup方法时作为第一个参数进行传递，在对应的方法体中我们可以再调用对应接口实现类的特定
 * 方法进行Http对象的注册。
 * @author Elim
 * 2017年11月6日
 */
@HandlesTypes(WebInitializer.class)
public class MyServletContainerInitializer2 implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext ctx) throws ServletException {
        //classes就是自动检测到的类路径下的该初始化类上的@HandlesTypes指定的类，在本示例中就是WebInitializer接口的实现类
        if (classes != null && !classes.isEmpty()) {
            for (Class<?> initializerClass : classes) {
                WebInitializer initializer;
                try {
                    initializer = (WebInitializer)initializerClass.newInstance();
                    initializer.onStartup(ctx);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
