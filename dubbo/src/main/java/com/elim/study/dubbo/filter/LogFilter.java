package com.elim.study.dubbo.filter;

import org.apache.dubbo.rpc.*;

/**
 * @author Elim
 * 19-7-18
 */
//@Activate(group = {"provider", "consumer"})
public class LogFilter implements Filter {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    System.out.println("调用前");
    long t1 = System.currentTimeMillis();
    Result result = invoker.invoke(invocation);
    long t2 = System.currentTimeMillis();
    System.out.println(invoker.getUrl() + "调用后，耗时：" + (t2 - t1));
    return result;
  }

}
