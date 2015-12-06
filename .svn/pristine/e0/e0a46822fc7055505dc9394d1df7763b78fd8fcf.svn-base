/**
 * 
 */
package com.yeelim.learn.spring.security.test;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * 用于决定用户是否有访问对应受保护对象的权限
 *
 */
public interface AccessDecisionManager {

    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication 当前正在请求受包含对象的Authentication
     * @param object 受保护对象，其可以是一个MethodInvocation、JoinPoint或FilterInvocation。
     * @param configAttributes 与正在请求的受保护对象相关联的配置属性
     *
     */
    void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException;

    /**
     * 表示当前AccessDecisionManager是否支持对应的ConfigAttribute
     */
    boolean supports(ConfigAttribute attribute);

    /**
     * 表示当前AccessDecisionManager是否支持对应的受保护对象类型
     */
    boolean supports(Class<?> clazz);
}
