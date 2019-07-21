package com.elim.study.dubbo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;

/**
 * @author Elim
 * 19-7-21
 */
@Activate(group = "provider")
public class ParamReceiveFilter implements Filter {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    String fooValue = invocation.getAttachment("foo");
    String foo1Value = invocation.getAttachment("foo1", "defaultValue");
    Map<String, String> attachments = invocation.getAttachments();
    String sendTime = attachments.get("sendTime");
    System.out.println(fooValue);
    System.out.println(foo1Value);
    System.out.println(sendTime);
    System.out.println(attachments);
    return invoker.invoke(invocation);
  }

}
