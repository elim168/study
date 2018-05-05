package com.elim.learn.springboot.spring.core;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


/**
 * 验证Nullable的
 * @author Elim
 * 2018年5月3日
 */
@Service
public class NullableService {

	@NonNull
	public String nonNull(@NonNull String nonNullStr) {
		return null;
	}
	
}
