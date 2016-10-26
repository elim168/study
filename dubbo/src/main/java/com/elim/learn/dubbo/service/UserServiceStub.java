/**
 * 
 */
package com.elim.learn.dubbo.service;

/**
 * 
 * 	<dubbo:service interface="com.elim.learn.dubbo.service.UserService"
		stub="com.elim.learn.dubbo.service.UserServiceStub" ref="userService"/>
	
	dubbo存根（Stub）是用于在客户端进行一些缓存操作而设计的。其会定义在服务端，在客户端也应包括它。
	当我们对服务进行调用时访问的就是对应的服务存根的代理对象。定义服务存根类的时候我们会把对应的存根类实现我们的服务对应的
	接口，如我们发布的UserService对应的存根类UserServiceStub则实现了UserService接口；然后我们需要给存根类定义一个
	构造方法其包含一个我们发布的服务类型的参数，用于接收真正的服务引用对象。我们需要在构造方法中把它保存起来，然后在实现的UserService
	所有接口方法中通过它来进行调用，在调用的过程中可以加一些我们自己的逻辑。如UserServiceStub中我们的sayHello方法中的实现。
	在进行调用的时候我们会发现UserServiceStub中的那两句输出会输出在客户端，而调用真正的服务的sayHello的方法里面的输出则是在服务端进行的。
 * 
 * 
 * 
 * 
 * @author Elim
 * 2016年10月23日
 */
public class UserServiceStub implements UserService {

	private final UserService userService;
	
	public UserServiceStub(UserService userService) {
		this.userService = userService;
	}
	
	/* (non-Javadoc)
	 * @see com.elim.learn.dubbo.service.UserService#sayHello(java.lang.String)
	 */
	@Override
	public void sayHello(String name) {
		System.out.println("==========================");
		System.out.println(name);
		this.userService.sayHello(name);
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.dubbo.service.UserService#cacheTest(int)
	 */
	@Override
	public String cacheTest(int id) {
		return this.userService.cacheTest(id);
	}

}
