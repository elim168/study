/**
 * 
 */
package com.yeelim.learn.spring.security.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yeelim
 * @date 2014-8-31
 * @time 下午5:37:21 
 *
 */
@Controller
@RequestMapping("/cas/test")
public class CasTestController {

	@RequestMapping("/getData")
	public void getDataFromApp(PrintWriter writer) throws Exception {
		String target = "http://yeelim:8083/app2/cas/test/sysTime.do";
		//1、从SecurityContextHolder获取到当前的Authentication对象，其是一个CasAuthenticationToken
		CasAuthenticationToken cat = (CasAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		//2、获取到AttributePrincipal对象
		AttributePrincipal principal = cat.getAssertion().getPrincipal();
		//3、获取对应的proxy ticket
		String proxyTicket = principal.getProxyTicketFor(target);
		System.out.println(proxyTicket);
		//4、请求被代理应用时将获取到的proxy ticket以参数ticket进行传递
		URL url = new URL(target + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8"));
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer content = new StringBuffer();
		String line = null;
		while ((line=br.readLine()) != null) {
			content.append(line).append("<br/>");
		}
		writer.write(content.toString());
	}
	
	@RequestMapping("/sysTime")
	public void getSysTime(PrintWriter writer) {
		writer.write("System Time is: " + new Date().toString());
	}
	
}
