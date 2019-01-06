<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shiro标签的简单用法</title>
</head>
<body>
	shiro的标签有如下几种：<br/>
	<ul>
		<li>guest：guest标签里面的内容只在当前用户没有登录的情况下显示。如：<shiro:guest>你好，请<a href="login.jsp">登录</a>。</shiro:guest></li>
		<li>user：与guest标签相对应的就是user标签，user标签的内容只在当前用户已经登录（通过表单登录或者是使用了记住我自动登录的情况）的情况下才显示。如：<shiro:user><a href="logout">退出</a></shiro:user></li>
		<li>
			principal：principal用于展示与principal相关的信息，默认什么参数都不带的principal标签将直接取principal的toString()，如：<shiro:principal/>，
			当我们的principal是一个复杂的对象的时候我们还可以通过property参数来获取它的指定参数，如：<shiro:principal property="bytes"/>，当我们想获取的信息不是主principal，
			而是其它类型的principal时，我们还可以通过指定type属性，来获取我们想要的那个类型的principal对象。
		</li>
		<li>authenticated：<shiro:authenticated>这里面的内容只有在用户经过的认证以后才会显示。这里的认证是指要经过表单登录，如果使用了“记住我”这种自动登录的也不算</shiro:authenticated></li>
		<li>notAuthenticated：<shiro:notAuthenticated>这里面的内容只有在用户没有经过认证的时候才会显示，包括匿名用户和使用了“记住我”的用户</shiro:notAuthenticated></li>
		<li>hasRole：<shiro:hasRole name="admin">这里面的内容只有在当前用户拥有指定的角色时才会显示</shiro:hasRole></li>
		<li>hasAnyRoles：<shiro:hasAnyRoles name="admin,user">这里面的内容只有在当前用户拥有指定的角色中的任何一个时才会显示</shiro:hasAnyRoles></li>
		<li>lacksRole：<shiro:lacksRole name="admin">这里面的内容只有在当前用户没有指定的角色时才会显示</shiro:lacksRole></li>
		<li>hasPermission：<shiro:hasPermission name="user:create">这里面的内容只有在当前的用户拥有指定的权限时才会显示</shiro:hasPermission></li>
		<li>lacksPermission：<shiro:lacksPermission name="user:create">这里面的内容只有在当前的用户没有指定的权限时才会显示</shiro:lacksPermission></li>
	</ul>
	
	
	
</body>
</html>