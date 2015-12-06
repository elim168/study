/**
 * 
 */
package com.yeelim.learn.springmvc.test;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * @author elim
 * @date 2015-3-24
 * @time 下午9:26:27 
 *
 */
public class MyWebBindingInitializer implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.setAutoGrowCollectionLimit(300);
	}

}
