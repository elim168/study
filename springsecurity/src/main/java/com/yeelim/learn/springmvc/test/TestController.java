/**
 * 
 */
package com.yeelim.learn.springmvc.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author elim
 * @date 2015-3-24
 * @time 下午7:52:17 
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value="/receive/array", produces="application/json")
	@ResponseBody
	public String receiveArray(User user, Model model, HttpServletRequest request) {
		model.addAttribute("user", user);
		System.out.println(user);
		return "Hello World.";
	}
	
}
