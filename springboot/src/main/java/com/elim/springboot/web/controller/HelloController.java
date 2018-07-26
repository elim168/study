package com.elim.springboot.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elim.springboot.mongo.User;

@RestController
@RequestMapping("hello")
public class HelloController {

	@GetMapping("json")
	public Object jsonResult() {
		Map<String, Object> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return map;
	}
	
	@PostMapping("string")
	public String string(@RequestBody String body) {
	    return "响应内容：" + body;
	}
	
	@RequestMapping("header")
	public String header(@RequestHeader Map<String, Object> headers) {
	    List<String> headerValues = new ArrayList<>();
	    headers.forEach((name, value) -> {
	        headerValues.add(name + "=" + value);
	    });
	    String result = headerValues.stream().collect(Collectors.joining(","));
	    return result;
	}
	
	@GetMapping("user/{id}")
	public User getUser(@PathVariable Long id) {
	    User user = new User();
	    user.setUserId(id);
	    user.setUsername("zhangsan");
	    user.setName("张三");
	    return user;
	}
	
}
