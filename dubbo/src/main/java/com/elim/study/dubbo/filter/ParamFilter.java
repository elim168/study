package com.elim.study.dubbo.filter;

import com.elim.study.dubbo.UserContext;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = {"provider", "consumer"})
public class ParamFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (RpcContext.getContext().isConsumerSide()) {
            invocation.setAttachment("userId", UserContext.getUserId().toString());
        } else {//服务端
            String userId = invocation.getAttachment("userId");
            System.out.println("*****************userId:*********" + userId);
            assert "123456".equals(userId);
            UserContext.setUserId(Long.valueOf(userId));
        }
        return invoker.invoke(invocation);
    }
}
