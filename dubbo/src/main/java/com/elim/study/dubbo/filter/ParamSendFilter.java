package com.elim.study.dubbo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Elim
 * 19-7-21
 */
@Activate(group = "consumer")
public class ParamSendFilter implements Filter {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    invocation.setAttachment("sendTime", LocalDateTime.now().toString());
    Map<String, String> attachments = invocation.getAttachments();
    System.out.println(attachments);
    attachments.put("foo", "bar");
    return invoker.invoke(invocation);
  }

}
