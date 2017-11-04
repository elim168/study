/**
 * 
 */
package com.elim.learn.spring.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 验证@ResponseStatus
 * @author Elim
 * 2017年11月3日
 */
@ResponseStatus(value=HttpStatus.BAD_GATEWAY, reason="验证@ResponseStatus的作用")
public class ResponseStatusException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 6644585920167208469L;

}
