package com.elim.learn.spring.cloud.zuul.config;

import java.util.UUID;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AddRequestIdZuulFilter extends ZuulFilter {

    private static final String REQUEST_ID_HEADER = "X-REQUEST-ID";
    
    @Override
    public boolean shouldFilter() {
        return !RequestContext.getCurrentContext().getZuulRequestHeaders().containsKey(REQUEST_ID_HEADER);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader(REQUEST_ID_HEADER, UUID.randomUUID().toString());
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
