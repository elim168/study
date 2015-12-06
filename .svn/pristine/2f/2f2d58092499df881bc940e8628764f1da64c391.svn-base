/**
 * 
 */
package com.yeelim.learn.spring.security.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yeelim.learn.spring.security.entity.User;
import com.yeelim.learn.spring.security.service.UserService;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 下午4:41:26 
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@RequestMapping("/authorize/{sidType}/{name}/{id}/{permission}")
	public void authorize(@PathVariable("id") int id, @PathVariable("name") String principalOrAuthority, @PathVariable("sidType") int sidType, @PathVariable("permission") int permission, PrintWriter pw) {
		this.userService.authorize(User.class, id, principalOrAuthority, sidType, permission);
		pw.write("Success.");
	}
	
	@RequestMapping("/find/{id}")
	public String find(@PathVariable("id") int id) {
		this.userService.find(id);
		return "user/find";
	}
	
	@RequestMapping("/find/all")
	public void findAll(PrintWriter pw) {
		List<User> userList = this.userService.findAll();
		for (User user : userList) {
			pw.write(user.toString());
		}
//		return "user/find";
	}
	
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable("id") int id, PrintWriter pw) {
		this.userService.delete(id);
		pw.write("Success");
	}
	
	@RequestMapping("/deletes")
	public void delete(PrintWriter pw) {
		List<Integer> ids = new ArrayList<Integer>(10);
		for (int i=0; i<10; ) {
			ids.add(++i);
		}
		this.userService.delete(ids, null);
		pw.write("OK");
	}
	
	@RequestMapping("/add/{id}")
	public void add(@PathVariable("id") int id, PrintWriter pw) {
		this.userService.addUser(new User(id));
		pw.write("Success");
	}

	@RequestMapping("/add")
	public String add() {
		this.userService.addUser(null);
		return "forward:/user/find/all.do";
	}
	
	/**
	 * 返回属性userService
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 给属性userService赋值
	 * @param userService the userService to set
	 */
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
