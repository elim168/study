/**
 * 
 */
package com.yeelim.learn.spring.security.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yeelim.learn.spring.security.entity.User;
import com.yeelim.learn.spring.security.service.UserService;

/**
 * @author Yeelim
 * @date 2014-8-6
 * @time 下午7:29:10
 * 
 */
@Controller
@RequestMapping("/acl")
public class AclController {

	@Autowired
	private UserService userService;

	@RequestMapping("/create/{id}")
	public void create(@PathVariable("id") Integer id, PrintWriter ps) {
		User user = new User(id);
		userService.addUser(user);
		ps.write("create Success!");
	}

}
