/**
 * 
 */
package com.elim.learn.spring.bean.registry;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Elim
 * 2017年9月21日
 */
@HelloAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HasHelloAnnotation {

}
